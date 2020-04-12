package com.medicalcentre.ui;
import com.medicalcentre.service.AppointmentService;
import com.medicalcentre.service.DoctorService;
import com.medicalcentre.service.PatientService;
import com.medicalcentre.service.PrescriptionService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Route("")
public class MainView extends AppLayout {
    private H4 header = new H4("Medical Centre");
    private Tab tab1 = new Tab("Dashboard");
    private Tab tab2 = new Tab("Appointments");
    private Tab tab3 = new Tab("Doctors");
    private Tab tab4 = new Tab("Patients");
    private Tab tab5 = new Tab("Prescriptions");
    private Div page1 = new Div();
    private Div page2 = new Div();
    private Div page3 = new Div();
    private Div page4 = new Div();
    private Div page5 = new Div();
    private Tabs tabs = new Tabs(tab1, tab2, tab3, tab4, tab5);
    private Div pages = new Div(page1, page2, page3, page4, page5);
    private Map<Tab, Component> tabsToPages = new HashMap<>();

    public MainView(@Autowired DoctorService doctorService,
                    @Autowired AppointmentService appointmentService,
                    @Autowired PatientService patientService,
                    @Autowired PrescriptionService prescriptionService) {

        setPrimarySection(Section.DRAWER);
        addToNavbar(new DrawerToggle(), header);

        page1.add(new DashboardView(doctorService, appointmentService, patientService, prescriptionService));
        page2.add(new AppointmentView(doctorService, appointmentService, patientService));
        page3.add(new DoctorView(doctorService));
        page4.add(new PatientView(patientService, prescriptionService, doctorService));
        page5.add(new PrescriptionView(doctorService, prescriptionService, patientService));

        page2.setVisible(false);
        page3.setVisible(false);
        page4.setVisible(false);
        page5.setVisible(false);

        tabsToPages.put(tab1, page1);
        tabsToPages.put(tab2, page2);
        tabsToPages.put(tab3, page3);
        tabsToPages.put(tab4, page4);
        tabsToPages.put(tab5, page5);

        Set<Component> pageShown = Stream.of(page1)
                .collect(Collectors.toSet());

        tabs.addSelectedChangeListener(event -> {
            pageShown.forEach(page -> page.setVisible(false));
            pageShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            pageShown.add(selectedPage);
        });

        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        setContent(pages);
        addToDrawer(tabs);
    }
}
