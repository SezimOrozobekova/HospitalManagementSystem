package com.example.hospitalmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "work_schedule")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
//
//    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<WorkSchedule> workSchedules;


}
