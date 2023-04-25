package com.example.equipmentapp.ui.views.list;

import com.example.equipmentapp.backend.entity.Category;
import com.example.equipmentapp.backend.entity.Employee;
import com.example.equipmentapp.backend.entity.Office;
import com.example.equipmentapp.backend.entity.Unit;
import com.example.equipmentapp.backend.event.FormEvent;
import com.example.equipmentapp.backend.service.CategoryService;
import com.example.equipmentapp.backend.service.EmployeeService;
import com.example.equipmentapp.backend.service.OfficeService;
import com.example.equipmentapp.backend.service.UnitService;
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


@Route(value = "", layout = MainLayout.class)
@PageTitle("Units | EquipmentApp CRM")
@RolesAllowed("ADMIN")
public class UnitListView extends VerticalLayout {

    private final UnitForm form;
    Grid<Unit> grid = new Grid<>(Unit.class);
    TextField filter = new TextField();
    private final UnitService unitService;

    public UnitListView(UnitService unitService,
                        CategoryService categoryService,
                        OfficeService officeService,
                        EmployeeService employeeService) {
        this.unitService = unitService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form = new UnitForm(
                categoryService.findALL(),
                officeService.findAll(),
                employeeService.findAll());

        form.addSaveListener(this::saveUnit);
        form.addDeleteListener(this::deleteUnit);
        form.addCloseListener(e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(
                new H1("Оборудование"),
                getToolBar(),
                content
        );
        updateList();
        closeEditor();
    }

    private void saveUnit(FormEvent.SaveEvent event) {
        unitService.save((Unit) event.getObject());
        updateList();
        closeEditor();
    }

    private void deleteUnit(FormEvent.DeleteEvent event) {
        unitService.delete((Unit) event.getObject());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        filter.setPlaceholder("поиск");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> updateList());

        Button addUnitButton = new Button("Добавить", click -> addUnit());

        HorizontalLayout toolbar = new HorizontalLayout(filter, addUnitButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void configureGrid() {
        grid.addClassName("grid");
        grid.setSizeFull();
        grid.removeColumnByKey("categoryId");
        grid.setColumns(
                "id",
                "model",
                "serialNumber",
                "inventoryNumber",
                "incomeDate",
                "outcomeDate",
                "description");
        grid.getColumnByKey("model").setHeader("Модель");
        grid.getColumnByKey("serialNumber").setHeader("Серийный №");
        grid.getColumnByKey("inventoryNumber").setHeader("Инвентарный №");
        grid.getColumnByKey("incomeDate").setHeader("Дата поступления");
        grid.getColumnByKey("outcomeDate").setHeader("Дата списания");
        grid.getColumnByKey("description").setHeader("Описание");
        grid.addColumn(unit -> {
            Category category = unit.getCategoryId();
            return category == null ? "-" : category.getName();
        }).setHeader("Категория").setSortable(true);
        grid.addColumn(unit -> {
            Employee employee = unit.getEmployeeId();
            return employee == null ? "-" : employee.getLastName();
        }).setHeader("Сотрудник").setSortable(true);
        grid.addColumn(unit -> {
            Office office = unit.getOfficeId();
            return office == null ? "-" : office.getOfficeNumber();
        }).setHeader("Кабинет").setSortable(true);

        grid.getColumns().forEach((c -> c.setAutoWidth(true)));

        grid.asSingleSelect().addValueChangeListener(event -> editUnit(event.getValue()));
    }

    private void addUnit() {
        grid.asSingleSelect().clear();
        editUnit(new Unit());
    }

    private void editUnit(Unit unit) {
        if (unit == null) {
            closeEditor();
        } else {
            form.setUnit(unit);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setUnit(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(unitService.findAll(filter.getValue()));
    }
}
