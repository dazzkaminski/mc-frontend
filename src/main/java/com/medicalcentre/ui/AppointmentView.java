package com.medicalcentre.ui;
import com.medicalcentre.model.Appointment;
import com.medicalcentre.service.AppointmentService;
import com.medicalcentre.service.DoctorService;
import com.medicalcentre.service.PatientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("appointments")
public class AppointmentView extends VerticalLayout {
    private AppointmentService appointmentService;
    private PatientService patientService;
    private DoctorService doctorService;
    private Grid<Appointment> grid = new Grid<>(Appointment.class);
    private HorizontalLayout buttonsBar = new HorizontalLayout();
    private AppointmentForm form;
    private Dialog dialog;
    private Button add;

    public AppointmentView(@Autowired DoctorService doctorService,
                           @Autowired AppointmentService appointmentService,
                           @Autowired PatientService patientService) {

        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;

        buildGrid();

        buttonsBar.add(buildAddButton());
        add(buttonsBar, grid);
        setSizeFull();
        refresh();
    }

    public void buildGrid() {
        grid.setColumns("id", "date", "time");

        grid.addColumn(p -> patientService.getPatient(p.getPatientId()).getFirstname() +
                " " + patientService.getPatient(p.getPatientId()).getLastname())
                .setHeader("Patient");

        grid.addColumn(d -> doctorService.getDoctor(d.getDoctorId()).getFirstname() +
                " " + doctorService.getDoctor(d.getDoctorId()).getLastname())
                .setHeader("Doctor");

        grid.addColumn("status").setHeader("Status");
        grid.addComponentColumn(this::buildEditButton).setFlexGrow(0);
        grid.addComponentColumn(this::buildDeleteButton).setFlexGrow(0);
        grid.getColumnByKey("id")
                .setFlexGrow(0)
                .setWidth("100px");
    }

    private Button buildAddButton() {
        return new Button(new Icon(VaadinIcon.PLUS),
                event -> {

                    form = new AppointmentForm(
                            doctorService,
                            appointmentService,
                            patientService
                    );

                    dialog = new Dialog(form);
                    dialog.setWidth("500px");
                    dialog.open();
                    form.save.addClickListener(e -> {
                        dialog.close();
                        refresh();
                    });
                    form.setAppointment(new Appointment());
                });
    }

    private Button buildDeleteButton(Appointment appointment) {
        Icon icon = new Icon(VaadinIcon.TRASH);
        Button delete = new Button();
        delete.setIcon(icon);
        delete.addClickListener(a -> {
            appointmentService.delete(appointment);
            refresh();
        });
        return delete;
    }

    private Button buildEditButton(Appointment appointment) {
        Button edit = new Button(new Icon(VaadinIcon.EDIT));
        edit.addClickListener(a -> {

            this.form = new AppointmentForm(
                    doctorService,
                    appointmentService,
                    patientService
            );

            dialog = new Dialog(form);
            dialog.open();
            dialog.setWidth("500px");
            form.save.addClickListener(e -> {
                dialog.close();
                refresh();
            });
            form.setAppointment(appointment);
        });
        return edit;
    }

    private void refresh() {
        grid.setItems(appointmentService.getAppointments());
    }
}
