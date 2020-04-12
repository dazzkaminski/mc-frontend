package com.medicalcentre.ui;
import com.medicalcentre.model.Prescription;
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

@Route("prescriptions")
public class PrescriptionView extends VerticalLayout {
    private PrescriptionService prescriptionService;
    private PatientService patientService;
    private DoctorService doctorService;
    private PrescriptionForm form;
    private Dialog dialog;
    private Button add;
    private Grid<Prescription> grid = new Grid<>(Prescription.class);
    private HorizontalLayout buttonsBar = new HorizontalLayout();

    public PrescriptionView(@Autowired DoctorService doctorService,
                            @Autowired PrescriptionService prescriptionService,
                            @Autowired PatientService patientService) {

        this.prescriptionService = prescriptionService;
        this.doctorService = doctorService;
        this.patientService = patientService;

        buildGrid();
        buttonsBar.add(buildAddButton());
        add(buttonsBar, grid);
        setSizeFull();
        refresh();
    }

    private void buildGrid() {
        grid.setColumns("id", "prescription", "date", "time");

        grid.addColumn(p -> patientService.getPatient(p.getPatientId()).getFirstname() +
                " " + patientService.getPatient(p.getPatientId()).getLastname())
                .setHeader("Patient");

        grid.addColumn(d -> doctorService.getDoctor(d.getDoctorId()).getFirstname() +
                " " + doctorService.getDoctor(d.getDoctorId()).getLastname())
                .setHeader("Doctor");

        grid.addComponentColumn(this::buildEditButton).setFlexGrow(0);
        grid.addComponentColumn(this::buildDeleteButton).setFlexGrow(0);

        grid.getColumnByKey("id")
                .setFlexGrow(0)
                .setWidth("100px");
    }

    private Button buildAddButton() {
        return new Button(new Icon(VaadinIcon.PLUS),
                event -> {
                    form = new PrescriptionForm(
                            doctorService,
                            prescriptionService,
                            patientService);

                    dialog = new Dialog(form);
                    dialog.setWidth("500px");
                    dialog.open();
                    form.save.addClickListener(e -> {
                        dialog.close();
                        refresh();
                    });
                    form.setPrescription(new Prescription());
                });
    }

    private Button buildDeleteButton(Prescription prescription) {
        Icon icon = new Icon(VaadinIcon.TRASH);
        Button delete = new Button();
        delete.setIcon(icon);
        delete.addClickListener(a -> {
            prescriptionService.delete(prescription);
            refresh();
        });
        return delete;
    }

    private Button buildEditButton(Prescription prescription) {
        Button edit = new Button(new Icon(VaadinIcon.EDIT));
        edit.addClickListener(a -> {
            form = new PrescriptionForm(
                    doctorService,
                    prescriptionService,
                    patientService);
            dialog = new Dialog(form);
            dialog.open();
            dialog.setWidth("500px");
            form.save.addClickListener(e -> {
                dialog.close();
                refresh();
            });
            form.setPrescription(prescription);
        });
        return edit;
    }

    private void refresh() {
        grid.setItems(prescriptionService.getPrescriptions());
    }
}
