package com.medicalcentre.service;
import com.medicalcentre.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private RestTemplate restTemplate;

    public PatientService(@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Patient> getPatients() {
        try {

            Patient[] patients = restTemplate.getForObject("http://mc-patient-service/patients", Patient[].class);

            return Arrays.asList(Optional.ofNullable(patients).orElse(new Patient[0]));

        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public Patient getPatient(int id) {
        return restTemplate.getForObject("http://mc-patient-service/patients/" + id, Patient.class);
    }

    public void save(Patient patient) {
        restTemplate.postForObject("http://mc-patient-service/patients", patient, Patient.class);
    }

    public void delete(Patient patient) {
        restTemplate.delete("http://mc-patient-service/patients/" + patient.getId());
    }


}
