package com.medicalcentre.ui;

import com.medicalcentre.model.Appointment;
import com.medicalcentre.model.Doctor;
import com.medicalcentre.model.Patient;
import com.medicalcentre.service.AppointmentService;
import com.medicalcentre.service.DoctorService;
import com.medicalcentre.service.PatientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

public class AppointmentForm extends FormLayout {
    private AppointmentService appointmentService;
    private PatientService patientService;
    private DoctorService doctorService;
    public Binder<Appointment> binder = new Binder<>(Appointment.class);
    private H3 header = new H3("Add an appointment");
    public ComboBox<Patient> patient = new ComboBox<>("Patient");
    public ComboBox<Doctor> doctor = new ComboBox<>("Doctor");
    public ComboBox<String> status = new ComboBox<>("Status");
    public DatePicker date = new DatePicker("Date");
    public TimePicker time = new TimePicker("Time");
    public Button save;

    public AppointmentForm(@Autowired DoctorService doctorService,
                           @Autowired AppointmentService appointmentService,
                           @Autowired PatientService patientService) {

        binder.bindInstanceFields(this);
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.doctorService = doctorService;

        buildForm();

        add(header, date, time, patient, doctor, status, save);
    }

    private void buildForm() {
        patient.setItems(patientService.getPatients());
        patient.setItemLabelGenerator(p -> p.getFirstname() + " " + p.getLastname());

        doctor.setItems(doctorService.getDoctors());
        doctor.setItemLabelGenerator(d -> d.getFirstname() + " " + d.getLastname());

        List<String> statusList = new ArrayList<>();
        statusList.add("Pending");
        statusList.add("Completed");
        statusList.add("Didn't show up");
        statusList.add("Cancelled");
        status.setItems(statusList);

        save = new Button("Save", event -> save());
    }

    private void save() {
        Appointment appointment = binder.getBean();
        appointment.setPatientId(appointment.getPatient().getId());
        appointment.setDoctorId(appointment.getDoctor().getId());
        appointmentService.save(appointment);
        setAppointment(null);
    }

    public void setAppointment(Appointment appointment) {
        binder.setBean(appointment);
    }
}
