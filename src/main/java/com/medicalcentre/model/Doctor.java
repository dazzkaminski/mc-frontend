package com.medicalcentre.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Doctor {
    private int id;
    private String firstname;
    private String lastname;
}
