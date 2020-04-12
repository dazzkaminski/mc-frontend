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
public class Prescription {
    private Integer id;
    private String prescription;
    private LocalDate date;
    private LocalTime time;
    private Integer patientId;
    private Integer doctorId;
    private Patient patient;
    private Doctor doctor;

    public Prescription(LocalDate date, LocalTime time, Integer doctorId, String prescription) {
        this.prescription = prescription;
        this.date = date;
        this.time = time;
        this.doctorId = doctorId;
    }
}
