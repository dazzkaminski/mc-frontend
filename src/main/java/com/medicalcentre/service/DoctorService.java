package com.medicalcentre.service;
import com.medicalcentre.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private RestTemplate restTemplate;

    @Value("${mc.doctor.endpoint}")
    private String url;

    public DoctorService(@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Doctor> getDoctors() {
        try {

            Doctor[] doctors = restTemplate.getForObject(url, Doctor[].class);

            return Arrays.asList(Optional.ofNullable(doctors).orElse(new Doctor[0]));

        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public Doctor getDoctor(int id) {
        return restTemplate.getForObject(url + "/" + id, Doctor.class);
    }

    public void save(Doctor doctor) {
        restTemplate.postForObject(url, doctor, Doctor.class);
    }

    public void delete(Doctor doctor) {
        restTemplate.delete(url + "/" + doctor.getId());
    }
}

