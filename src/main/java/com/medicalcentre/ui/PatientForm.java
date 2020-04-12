package com.medicalcentre.ui;
import com.medicalcentre.model.Patient;
import com.medicalcentre.service.PatientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;

public class PatientForm extends FormLayout {
    public Binder<Patient> binder = new Binder<>(Patient.class);
    public Button save;
    public TextField firstname = new TextField("First name");
    public TextField lastname = new TextField("Last name");
    public DatePicker dateOfBirth = new DatePicker("Date of birth");
    public TextField email = new TextField("Email");
    public TextField mobile = new TextField("Mobile");
    public TextField address = new TextField("Address");
    public TextField NHSNumber = new TextField("NHS Number");
    private PatientService patientService;
    private H3 header = new H3("Add/Edit a patient");

    public PatientForm(@Autowired PatientService patientService) {

        binder.bindInstanceFields(this);
        this.patientService = patientService;

        save = new Button("Save", event -> save());
        add(header, firstname, lastname, dateOfBirth, email,
                mobile, address, NHSNumber, save);
    }

    public void save() {
        Patient patient = binder.getBean();
        patientService.save(patient);
        setPatient(null);
    }

    public void setPatient(Patient patient) {
        binder.setBean(patient);
    }
}
