package com.medicalcentre.ui;
import com.medicalcentre.model.Patient;
import com.medicalcentre.service.DoctorService;
import com.medicalcentre.service.PatientService;
import com.medicalcentre.service.PrescriptionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route("patients")
public class PatientView extends VerticalLayout {
    private PatientService patientService;
    private PrescriptionService prescriptionService;
    private DoctorService doctorService;
    private PatientForm form;
    private PatientDetails details;
    private Dialog dialog;
    private Button add;
    private Grid<Patient> grid = new Grid<>(Patient.class);
    private HorizontalLayout buttonsBar = new HorizontalLayout();

    public PatientView(@Autowired PatientService patientService,
                       @Autowired PrescriptionService prescriptionService,
                       @Autowired DoctorService doctorService) {

        this.patientService = patientService;
        this.prescriptionService = prescriptionService;
        this.doctorService = doctorService;

        buildGrid();

        buttonsBar.add(buildAddButton());
        add(buttonsBar, grid);
        setSizeFull();
        refresh();
    }

    private void buildGrid() {
        grid.setColumns("id", "firstname", "lastname", "dateOfBirth",
                "email", "mobile", "address", "nhsnumber");
        grid.addComponentColumn(this::buildShowDetailsButton).setFlexGrow(0);
        grid.addComponentColumn(this::buildEditButton).setFlexGrow(0);
        grid.addComponentColumn(this::buildDeleteButton).setFlexGrow(0);
        grid.getColumnByKey("id")
                .setFlexGrow(0)
                .setWidth("100px");
    }

    private Button buildAddButton() {
        return new Button(new Icon(VaadinIcon.PLUS),
                event -> {
                    form = new PatientForm(patientService);
                    dialog = new Dialog(form);
                    dialog.setWidth("500px");
                    dialog.open();
                    form.save.addClickListener(e -> {
                        dialog.close();
                        refresh();
                    });
                    form.setPatient(new Patient());
                });
    }

    private Button buildDeleteButton(Patient patient) {
        Icon icon = new Icon(VaadinIcon.TRASH);
        Button delete = new Button();
        delete.setIcon(icon);
        delete.addClickListener(a -> {
            patientService.delete(patient);
            refresh();
        });
        return delete;
    }

    private Button buildEditButton(Patient patient) {
        Button edit = new Button(new Icon(VaadinIcon.EDIT));
        edit.addClickListener(a -> {
            form = new PatientForm(patientService);
            dialog = new Dialog(form);
            dialog.open();
            dialog.setWidth("500px");
            form.save.addClickListener(e -> {
                dialog.close();
                refresh();
            });
            form.setPatient(patient);
        });
        return edit;
    }

    private Button buildShowDetailsButton(Patient patient) {
        Button showDetails = new Button(new Icon(VaadinIcon.INFO));
        showDetails.addClickListener(p -> {
            details = new PatientDetails(patient, prescriptionService, doctorService);
            dialog = new Dialog(details);
            dialog.open();
            dialog.setWidth("500px");
            details.setPatient(patient);
        });
        return showDetails;
    }

    private void refresh() {
        grid.setItems(patientService.getPatients());
    }
}
