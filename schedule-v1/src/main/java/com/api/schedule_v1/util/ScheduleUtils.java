package com.api.schedule_v1.util;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public final class ScheduleUtils {

    public static List<LocalTime> generateDailySlots(int durationService){

        LocalTime openingTime = LocalTime.of(8, 0);
        LocalTime closingTime = LocalTime.of(18, 0);
        LocalTime lunchTimeStart = LocalTime.of(12, 0);
        LocalTime lunchTimeEnd = LocalTime.of(13, 0);

        List<LocalTime> slots = new ArrayList<>();
        LocalTime current = openingTime;

        while (current.isBefore(closingTime)){

            if(!current.equals(lunchTimeStart) && !current.isBefore(lunchTimeEnd)){
                slots.add(current);
            }

            current = current.plusMinutes(durationService);
        }

        return slots;
    }
}
