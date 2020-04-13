package com.medicalcentre.service;
import com.medicalcentre.model.Prescription;
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
public class PrescriptionService {

    private RestTemplate restTemplate;

    @Value("${mc.prescription.endpoint}")
    private String url;
    
    public PrescriptionService(@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Prescription> getPrescriptions() {
        try {

            Prescription[] prescriptions =
                    restTemplate.getForObject(url, Prescription[].class);

            return Arrays.asList(Optional.ofNullable(prescriptions).orElse(new Prescription[0]));

        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public Prescription getPrescription(int id) {
        return restTemplate.getForObject(url + id, Prescription.class);
    }

    public void save(Prescription prescription) {
        restTemplate.postForObject(url, prescription, Prescription.class);
    }

    public void delete(Prescription prescription) {
        restTemplate.delete(url + prescription.getId());
    }
}
