package com.example.equipmentapp.ui.views.list;

import com.example.equipmentapp.backend.entity.Office;
import com.example.equipmentapp.backend.event.FormEvent;
import com.example.equipmentapp.backend.service.OfficeService;
import com.example.equipmentapp.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "offices", layout = MainLayout.class)
@PageTitle("Offices | EquipmentApp CRM")
@RolesAllowed("ADMIN")
public class OfficeListView extends VerticalLayout {

    Grid<Office> grid = new Grid<>(Office.class);
    TextField filter = new TextField();
    private final OfficeService officeService;
    private final OfficeForm officeForm;

    public OfficeListView(OfficeService officeService) {
        this.officeService = officeService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        officeForm = new OfficeForm();
        officeForm.addSaveListener(this::saveOffice);
        officeForm.addDeleteListener(this::deleteOffice);
        officeForm.addCloseListener(event -> closeEditor());

        Div content = new Div(grid, officeForm);
        content.addClassName("content");
        content.setSizeFull();

        add(
                new H1("Кабинеты"),
                getToolBar(),
                content
        );
        updateList();
        closeEditor();
    }

    private void deleteOffice(FormEvent.DeleteEvent deleteEvent) {
        officeService.delete((Office) deleteEvent.getObject());
        updateList();
        closeEditor();
    }

    private void saveOffice(FormEvent.SaveEvent saveEvent) {
        officeService.save((Office) saveEvent.getObject());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        filter.setPlaceholder("поиск");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> updateList());

        Button addOfficeButton = new Button("Добавить", click -> addOffice());

        HorizontalLayout toolbar = new HorizontalLayout(filter, addOfficeButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addOffice() {
        grid.asSingleSelect().clear();
        editOffice(new Office());
    }

    private void editOffice(Office office) {
        if (office == null) {
            closeEditor();
        } else {
            officeForm.setOffice(office);
            officeForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void updateList() {
        grid.setItems(officeService.findAll(filter.getValue()));
    }

    private void closeEditor() {
        officeForm.setOffice(null);
        officeForm.setVisible(false);
        removeClassName("editing");
    }

    private void configureGrid() {
        grid.addClassName("grid");
        grid.setSizeFull();
        grid.setColumns(
                "id",
                "officeNumber",
                "buildingNumber",
                "description"
        );
        grid.getColumnByKey("officeNumber").setHeader("№ Кабинета");
        grid.getColumnByKey("buildingNumber").setHeader("№ Корпуса");
        grid.getColumnByKey("description").setHeader("Описание");

        grid.getColumns().forEach((c -> c.setAutoWidth(true)));

        grid.asSingleSelect().addValueChangeListener(event -> editOffice(event.getValue()));

    }
}
