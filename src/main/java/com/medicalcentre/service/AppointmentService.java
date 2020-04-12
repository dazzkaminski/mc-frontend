package com.medicalcentre.service;
import com.medicalcentre.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private RestTemplate restTemplate;

    public AppointmentService(@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Appointment> getAppointments() {
        try {

            Appointment[] appointments =
                    restTemplate.getForObject("http://mc-appointment-service/appointments", Appointment[].class);

            return Arrays.asList(Optional.ofNullable(appointments).orElse(new Appointment[0]));

        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public Appointment getAppointment(int id) {
        return restTemplate.getForObject("http://mc-appointment-service/appointments/" + id, Appointment.class);
    }

    public void save(Appointment doctor) {
        restTemplate.postForObject("http://mc-appointment-service/appointments", doctor, Appointment.class);
    }

    public void delete(Appointment doctor) {
        restTemplate.delete("http://mc-appointment-service/appointments/" + doctor.getId());
    }
}

