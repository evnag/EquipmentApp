package com.example.equipmentapp.ui;

import com.example.equipmentapp.backend.configuration.SecurityService;
import com.example.equipmentapp.ui.views.list.CategoryListView;
import com.example.equipmentapp.ui.views.list.OfficeListView;
import com.example.equipmentapp.ui.views.list.UnitListView;
import com.example.equipmentapp.ui.views.list.UserListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

@CssImport("./styles/custom-styles.css")
public class MainLayout extends AppLayout {

    private final SecurityService securityService;

    public MainLayout(@Autowired SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("EquipmentApp CRM");
        logo.addClassName("logo");
        HorizontalLayout header;

        if (securityService.getAuthenticatedUser() != null) {
            Button logout = new Button("Log out", clickEvent -> securityService.logout());
            header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        } else {
            header = new HorizontalLayout(new DrawerToggle(), logo);
        }

        header.addClassName("header");
        header.expand(logo);
        header.setWidthFull();
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink unitListLink = new RouterLink("Оборудование", UnitListView.class);
        unitListLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink officeListLink = new RouterLink("Кабинеты", OfficeListView.class);
        RouterLink categoryListLink = new RouterLink("Категории", CategoryListView.class);
        RouterLink userListLink = new RouterLink("Пользователи", UserListView.class);

        addToDrawer(new VerticalLayout(
                unitListLink,
                officeListLink,
                categoryListLink,
                userListLink
        ));
    }


}
