package com.medicalcentre.ui;
import com.medicalcentre.model.Appointment;
import com.medicalcentre.model.Doctor;
import com.medicalcentre.model.Patient;
import com.medicalcentre.service.AppointmentService;
import com.medicalcentre.service.DoctorService;
import com.medicalcentre.service.PatientService;
import com.medicalcentre.service.PrescriptionService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayoutVariant;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Route("dashboard")
public class DashboardView extends VerticalLayout {
    private AppointmentService appointmentService;
    private DoctorService doctorService;
    private PatientService patientService;
    private PrescriptionService preService;
    private SplitLayout widgets = new SplitLayout();
    private VerticalLayout infoWidget = new VerticalLayout();
    private VerticalLayout appointmentsWidget = new VerticalLayout();
    private VerticalLayout topBar = new VerticalLayout();
    private DatePicker datePicker = new DatePicker(LocalDate.now());
    private H4 appointmentsTitle = new H4("Appointments");

    public DashboardView(@Autowired DoctorService doctorService,
                         @Autowired AppointmentService appointmentService,
                         @Autowired PatientService patientService,
                         @Autowired PrescriptionService prescriptionService) {

        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.preService = prescriptionService;

        Long appPerDay = appointmentService.getAppointments().stream()
                .filter(a -> a.getDate().equals(LocalDate.now()) && a.getStatus().contains("Pending"))
                .count();
        H4 infoTitle = new H4("Medical Centre Info");
        Label todaysAppointments = new Label("Appointment's for today: " + appPerDay.toString());
        Label doctorCounter = new Label("Doctors in the surgery: " + doctorService.getDoctors().size());
        Label patientCounter = new Label("Registered patients: " + patientService.getPatients().size());
        Label prescriptionCounter = new Label("Prescriptions given: " + preService.getPrescriptions().size());

        listAppointments();

        datePicker.addValueChangeListener(event -> {
            listAppointments();
        });

        topBar.setWidthFull();
        topBar.add(appointmentsTitle, datePicker);

        infoWidget.setPadding(true);
        infoWidget.add(
                infoTitle,
                todaysAppointments,
                doctorCounter,
                patientCounter,
                prescriptionCounter);

        widgets.addToPrimary(topBar, appointmentsWidget);
        widgets.setPrimaryStyle("maxWidth", "500px");
        widgets.setPrimaryStyle("minWidth", "400px");
        widgets.addToSecondary(infoWidget);

        widgets.setSizeFull();
        widgets.addThemeVariants(SplitLayoutVariant.LUMO_SMALL);
        add(widgets);
    }

    public Component buildAppointment(Appointment a) {
        VerticalLayout content = new VerticalLayout();
        Patient patient = patientService.getPatient(a.getPatientId());
        Doctor doctor = doctorService.getDoctor(a.getDoctorId());

        H4 pName = new H4();
        Label dName = new Label();
        Label pDOB = new Label();
        Label pEmail = new Label();
        Label pNHSNumber = new Label();
        Label status = new Label();

        pName.setText(patient.getFirstname() + " " + patient.getLastname());
        dName.setText("Doctor: " + doctor.getFirstname() + " " + doctor.getLastname());
        pDOB.setText("Date of birth: " + patient.getDateOfBirth().toString());
        pEmail.setText("Email: " + patient.getEmail());
        pNHSNumber.setText("NHS Number: " + patient.getNhsnumber());

        content.addAndExpand(pName, dName, pDOB, pEmail, pNHSNumber, status);
        content.setPadding(true);
        content.getStyle().set("border", "1px solid #ebebeb");
        return content;
    }

    public void listAppointments() {
        List<Appointment> appointments = appointmentService.getAppointments().stream()
                .filter(a -> a.getDate().equals(datePicker.getValue()) && a.getStatus().contains("Pending"))
                .collect(Collectors.toList());

        appointmentsWidget.removeAll();

        for (Appointment a : appointments) {
            appointmentsWidget.add(buildAppointment(a));
        }
    }
}
