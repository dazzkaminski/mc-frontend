package com.medicalcentre.ui;
import com.medicalcentre.model.Doctor;
import com.medicalcentre.service.DoctorService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("doctors")
public class DoctorView extends VerticalLayout {
    private DoctorService doctorService;
    private DoctorForm form;
    private Dialog dialog;
    private Button add;
    private Grid<Doctor> grid = new Grid<>(Doctor.class);
    private HorizontalLayout buttonsBar = new HorizontalLayout();
    private DataProvider dataProvider;

    public DoctorView(@Autowired DoctorService doctorService) {

        this.doctorService = doctorService;

        buildGrid();
        buttonsBar.add(buildAddButton());
        add(buttonsBar, grid);
        refresh();
    }

    private void buildGrid() {
        grid.setColumns("id", "firstname", "lastname");
        grid.addComponentColumn(this::buildEditButton).setFlexGrow(0);
        grid.addComponentColumn(this::buildDeleteButton).setFlexGrow(0);
        grid.getColumnByKey("id")
                .setFlexGrow(0)
                .setWidth("100px");
    }

    private Button buildAddButton() {
        return new Button(new Icon(VaadinIcon.PLUS),
                event -> {
                    form = new DoctorForm(doctorService);
                    dialog = new Dialog(form);
                    dialog.setWidth("500px");
                    dialog.open();
                    form.save.addClickListener(e -> {
                        dialog.close();
                        refresh();
                    });
                    form.setDoctor(new Doctor());
                });
    }

    private Button buildDeleteButton(Doctor doctor) {
        Icon icon = new Icon(VaadinIcon.TRASH);
        Button delete = new Button();
        delete.setIcon(icon);
        delete.addClickListener(a -> {
            doctorService.delete(doctor);
            refresh();
        });
        return delete;
    }

    private Button buildEditButton(Doctor doctor) {
        Button edit = new Button(new Icon(VaadinIcon.EDIT));
        edit.addClickListener(a -> {
            form = new DoctorForm(doctorService);
            dialog = new Dialog(form);
            dialog.open();
            dialog.setWidth("500px");
            form.save.addClickListener(e -> {
                dialog.close();
                refresh();
            });
            form.setDoctor(doctor);
        });
        return edit;
    }

    private void refresh() {
        grid.setItems(doctorService.getDoctors());
    }
}
