package com.medicalcentre.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment {
    private Integer id;
    private LocalDate date;
    private LocalTime time;
    private String status;
    private Integer patientId;
    private Integer doctorId;
    private Patient patient;
    private Doctor doctor;
}
