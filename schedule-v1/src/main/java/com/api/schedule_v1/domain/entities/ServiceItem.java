package com.api.schedule_v1.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "tb_service")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ServiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,unique = true,updatable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name",nullable = false,unique = true)
    private String name;

    @Column(name = "price",nullable = false)
    private BigDecimal price;

    @Column(name = "duration_in_minutes",nullable = false)
    private Integer durationInMinutes;

}
