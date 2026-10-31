package com.api.schedule_v1.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_professional")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Professional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,updatable = false, unique = true)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "telephone",nullable = false, unique = true)
    private String telephone;

    @Column(name = "profession",nullable = false)
    private String profession;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_professional_service",
               joinColumns = @JoinColumn(name = "professional_id", nullable = false),
               inverseJoinColumns = @JoinColumn(name = "service_id", nullable = false)
    )
    private Set<ServiceItem> listServiceItems = new HashSet<>();

}
