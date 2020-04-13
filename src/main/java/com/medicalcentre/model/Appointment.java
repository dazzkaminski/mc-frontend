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

    public Appointment(Integer id, LocalDate date, LocalTime time,
                       String status, Integer patientId, Integer doctorId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.status = status;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }
}
