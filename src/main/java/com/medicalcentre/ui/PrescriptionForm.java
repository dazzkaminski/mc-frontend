package com.medicalcentre.ui;
import com.medicalcentre.model.Doctor;
import com.medicalcentre.model.Patient;
import com.medicalcentre.model.Prescription;
import com.medicalcentre.service.DoctorService;
import com.medicalcentre.service.PatientService;
import com.medicalcentre.service.PrescriptionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;

public class PrescriptionForm extends FormLayout {
    private PrescriptionService prescriptionService;
    private PatientService patientService;
    private DoctorService doctorService;
    private Binder<Prescription> binder = new Binder<>(Prescription.class);
    private H3 header = new H3("Add/Edit a prescription");
    public TextArea prescription = new TextArea("Prescription");
    public ComboBox<Patient> patient = new ComboBox<>("Patient");
    public ComboBox<Doctor> doctor = new ComboBox<>("Doctor");
    public DatePicker date = new DatePicker("Date");
    public TimePicker time = new TimePicker("Time");
    public Button save;


    public PrescriptionForm(@Autowired DoctorService doctorService,
                            @Autowired PrescriptionService prescriptionService,
                            @Autowired PatientService patientService) {

        binder.bindInstanceFields(this);
        this.prescriptionService = prescriptionService;
        this.doctorService = doctorService;
        this.patientService = patientService;

        buildPrescriptionForm();

        add(header, date, time, patient, doctor, prescription, save);
    }

    private void buildPrescriptionForm() {
        patient.setItemLabelGenerator(patient -> patient.getFirstname() + " " + patient.getLastname());
        patient.setItems(patientService.getPatients());
        doctor.setItemLabelGenerator(doctor -> doctor.getFirstname() + " " + doctor.getLastname());
        doctor.setItems(doctorService.getDoctors());
        prescription.setWidth("90%");
        save = new Button("Save", event -> save());
    }

    public void save() {
        Prescription prescription = binder.getBean();
        prescription.setPatientId(binder.getBean().getPatient().getId());
        prescription.setDoctorId(binder.getBean().getDoctor().getId());
        prescriptionService.save(prescription);
        setPrescription(null);
    }

    public void setPrescription(Prescription prescription) {
        binder.setBean(prescription);
    }
}
