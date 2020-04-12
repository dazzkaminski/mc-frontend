package com.medicalcentre.ui;
import com.medicalcentre.model.Doctor;
import com.medicalcentre.model.Patient;
import com.medicalcentre.model.Prescription;
import com.medicalcentre.service.DoctorService;
import com.medicalcentre.service.PrescriptionService;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

public class PatientDetails extends FormLayout {
    private PrescriptionService prescriptionService;
    private DoctorService doctorService;
    private Patient patient;
    private Binder<Patient> binder = new Binder<>(Patient.class);
    private H3 title = new H3("Patient's details");
    private Details prescriptions = new Details();
    private TextField NHSNumber = new TextField();
    private TextField firstName = new TextField();
    private TextField lastName = new TextField();
    private TextField dob = new TextField();
    private TextField email = new TextField();
    private TextField mobile = new TextField();
    private TextField address = new TextField();

    public PatientDetails(Patient patient,
                          @Autowired PrescriptionService prescriptionService,
                          @Autowired DoctorService doctorService) {

        binder.bindInstanceFields(this);
        this.patient = patient;
        this.prescriptionService = prescriptionService;
        this.doctorService = doctorService;

        buildDetailsForm();

        add(title, NHSNumber, firstName, lastName, dob, email, mobile, address, prescriptions);
    }

    private void buildDetailsForm() {
        prescriptions.setSummaryText("Prescriptions");

        List<Prescription> list = prescriptionService.getPrescriptions().stream()
                .filter(p -> p.getPatientId() == patient.getId())
                .map(p -> new Prescription(p.getDate(), p.getTime(), p.getDoctorId(), p.getPrescription()))
                .collect(Collectors.toList());

        for (Prescription p : list) {
            TextArea result = new TextArea();
            result.setWidth("90%");
            Doctor doctor = doctorService.getDoctor(p.getDoctorId());
            result.setLabel("Dr. " + doctor.getFirstname() + " "
                    + doctor.getLastname() + " "
                    + p.getDate() + " "
                    + p.getTime()
            );
            result.setValue(p.getPrescription());
            prescriptions.addContent(result);
        }
    }

    public void setPatient(Patient patient) {
        binder.setBean(patient);
    }
}
