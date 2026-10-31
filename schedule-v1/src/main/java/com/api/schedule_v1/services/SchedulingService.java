package com.api.schedule_v1.services;

import com.api.schedule_v1.domain.entities.*;
import com.api.schedule_v1.domain.enums.SchedulingStatus;
import com.api.schedule_v1.dto.scheduling.SchedulingAvailableDTO;
import com.api.schedule_v1.dto.scheduling.SchedulingRequestDTO;
import com.api.schedule_v1.dto.scheduling.SchedulingResponseDTO;
import com.api.schedule_v1.exception.BadRequestException;
import com.api.schedule_v1.repository.SchedulingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final SchedulingRepository schedulingRepository;
    private final ClientService clientService;
    private final ProfessionalService professionalService;
    private final ServiceItemService serviceItemService;
    private final ProfessionalAvailabilityService professionalAvailabilityService;

    public void scheduleAppointment(SchedulingRequestDTO dto){

        LocalDateTime appointmentDateTime = LocalDateTime.of(
                dto.appointmentDate(),
                dto.startTime()
        );

        if (!appointmentDateTime.isAfter(LocalDateTime.now())) {
            throw new BadRequestException(
                    "Não é possível agendar para uma data ou horário no passado"
            );
        }

        ServiceItem serviceItem = serviceItemService.findById(dto.serviceId());
        Professional professional = professionalService.findById(dto.professionalId());
        Client client = clientService.findById(dto.clientId());

        List<ProfessionalAvailability> listAvailability = professionalAvailabilityService
                .findByProfessionalIdAndDayOfWeek(professional.getId(), dto.appointmentDate().getDayOfWeek());
        List<Scheduling> listAppointment = schedulingRepository.findByProfessionalIdAndAppointmentDate(dto.professionalId(), dto.appointmentDate());


        boolean isConflict = isAvailable(listAvailability, dto.startTime(), serviceItem.getDurationInMinutes());

        if(!isConflict){
            throw new BadRequestException("Horário Inválido, fora do turno de atendimento");
        }

        List<SchedulingAvailableDTO> slots = generateSlots(
                listAvailability,
                serviceItem.getDurationInMinutes(),
                listAppointment
        );

        boolean isValidSlot = slots.stream().anyMatch(slot ->
                slot.startTime().equals(dto.startTime())
                        && slot.available()
        );

        if (!isValidSlot) {
            throw new BadRequestException(
                    "Horário inválido ou indisponível"
            );
        }

        Scheduling scheduling = Scheduling.builder()
                .appointmentDate(dto.appointmentDate())
                .client(client)
                .professional(professional)
                .serviceItem(serviceItem)
                .startTime(dto.startTime())
                .endTime(dto.startTime().plusMinutes(serviceItem.getDurationInMinutes()))
                .status(SchedulingStatus.AGENDADO)
                .build();

        schedulingRepository.save(scheduling);
    }

    public List<SchedulingAvailableDTO> getAllForDate(Long professionalId, Long serviceId, LocalDate appointmentDate){

        Professional professional = professionalService.findById(professionalId);
        ServiceItem service = serviceItemService.findById(serviceId);
        List<Scheduling> listAppointment = schedulingRepository.findByProfessionalIdAndAppointmentDate(professionalId, appointmentDate);

        DayOfWeek dayOfWeek = appointmentDate.getDayOfWeek();
        List<ProfessionalAvailability> professionalAvailabilityList =
                professionalAvailabilityService.findByProfessionalIdAndDayOfWeek(professionalId, dayOfWeek);

        if(professionalAvailabilityList.isEmpty()){
            throw new BadRequestException("O Profissional Não atende neste dia");
        }

       return generateSlots(professionalAvailabilityList, service.getDurationInMinutes(), listAppointment);
    }

    private List<SchedulingAvailableDTO> generateSlots(List<ProfessionalAvailability> availabilities,
                                                       Integer durationMinutes,
                                                       List<Scheduling> appointments
                                                       ){
        List<SchedulingAvailableDTO> availableSlots = new ArrayList<>();

        for(ProfessionalAvailability availability : availabilities) {

            Shift shift = availability.getShift();

            LocalTime shiftStartTime = shift.getStartTime();
            LocalTime shiftEndTime = shift.getEndTime();

            LocalTime slotStartTime = shiftStartTime;

            while (slotStartTime.isBefore(shiftEndTime)){
                LocalTime slotEndTime = slotStartTime.plusMinutes(durationMinutes);

                if(slotEndTime.isAfter(shiftEndTime)){
                    break;
                }

                LocalTime currentStart = slotStartTime;
                LocalTime currentEnd = slotEndTime;

                boolean isConflict = appointments.stream().anyMatch(scheduling ->
                        currentStart.isBefore(scheduling.getEndTime()) && currentEnd.isAfter(scheduling.getStartTime())
                );

                availableSlots.add(new SchedulingAvailableDTO(slotStartTime, slotEndTime, !isConflict));

                slotStartTime = slotEndTime;
            }
        }

        return availableSlots;
    }


    private boolean isAvailable(List<ProfessionalAvailability> availabilities, LocalTime startTime, Integer durationService){

        for(ProfessionalAvailability availability : availabilities){

            LocalTime endTime = startTime.plusMinutes(durationService);

            Shift shift = availability.getShift();
            LocalTime shiftStartTime = shift.getStartTime();
            LocalTime shiftEndTime = shift.getEndTime();

            if(!startTime.isBefore(shiftStartTime) && !endTime.isAfter(shiftEndTime)){
                return true;
            }

        }

        return false;
    }
}








