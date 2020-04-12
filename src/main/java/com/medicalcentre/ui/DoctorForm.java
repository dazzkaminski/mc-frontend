package com.medicalcentre.ui;
import com.medicalcentre.model.Doctor;
import com.medicalcentre.service.DoctorService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;

public class DoctorForm extends FormLayout {
    private DoctorService doctorService;
    public Binder<Doctor> binder = new Binder<>(Doctor.class);
    private H3 header = new H3("Add/Edit a doctor");
    public TextField firstname = new TextField("Firstname");
    public TextField lastname = new TextField("Lastname");
    public Button save;

    public DoctorForm(@Autowired DoctorService doctorService) {

        binder.bindInstanceFields(this);
        this.doctorService = doctorService;

        save = new Button("Save", event -> save());
        add(header, firstname, lastname, save);
    }

    private void save() {
        Doctor doctor = binder.getBean();
        doctorService.save(doctor);
        setDoctor(null);
    }

    public void setDoctor(Doctor doctor) {
        binder.setBean(doctor);
    }
}
