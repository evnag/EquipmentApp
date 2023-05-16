package com.example.equipmentapp.ui.views.list;

import com.example.equipmentapp.backend.entity.*;
import com.example.equipmentapp.ui.event.FormEvent;
import com.example.equipmentapp.backend.exception.UnitNotFoundException;
import com.example.equipmentapp.backend.service.*;
import com.example.equipmentapp.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
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

import java.time.LocalDate;
import java.util.List;


@Route(value = "", layout = MainLayout.class)
@PageTitle("Units | EquipmentApp CRM")
@RolesAllowed({"ADMIN", "USER"})
public class UnitListView extends VerticalLayout {

    private final UnitForm form;
    private final UnitService unitService;
    private final TransactionService transactionService;
    Grid<Unit> grid = new Grid<>(Unit.class);
    Grid<Transaction> gridTransaction = new Grid<>(Transaction.class);
    TextField filter = new TextField();
    Dialog dialog = new Dialog();

    public UnitListView(UnitService unitService,
                        CategoryService categoryService,
                        OfficeService officeService,
                        EmployeeService employeeService,
                        TransactionService transactionService) {
        this.unitService = unitService;
        this.transactionService = transactionService;
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
        Unit unit = (Unit) event.getObject();
        unitService.save(unit);
        transactionService.save(addTransaction(unit));
        updateList();
        updateTransactionList();
        closeEditor();
    }
    public void deleteUnit(FormEvent.DeleteEvent event) {
        Unit unit = (Unit) event.getObject();
        unitService.delete(unit);
        updateList();
        updateTransactionList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        filter.setPlaceholder("поиск");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> updateList());

        Button addUnitButton = new Button("Добавить", click -> addUnit());
        Button dialogButton = new Button("История", e -> dialog.open());
        dialogButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        configuredDialog();

        HorizontalLayout toolbar = new HorizontalLayout(filter, addUnitButton, dialogButton, dialog);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void configuredDialog() {
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.setHeaderTitle("История");
        dialog.setSizeFull();
        dialog.getFooter().add(cancelButton);

        VerticalLayout dialogLayout = createDialogLayout(transactionService.findAll());
        dialog.add(dialogLayout);
    }

    private VerticalLayout createDialogLayout(List<Transaction> transactions) {
        gridTransaction.addClassName("grid");
        gridTransaction.setColumns("id", "action", "date", "asset");
        gridTransaction.getColumns().forEach((c -> c.setAutoWidth(true)));

        gridTransaction.setItems(transactions);
        return new VerticalLayout(gridTransaction);
    }

    private void updateTransactionList() {
        gridTransaction.setItems(transactionService.findAll());
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
            return office == null ? "-" : office.getFullData();
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

    public Transaction addTransaction(Unit unit) {
        Transaction transaction = new Transaction();
        if (unitService.getById(unit.getId()) != null) {
            transaction.setUnitId(unit);
            transaction.setDate(LocalDate.now());
            transaction.setAction("New transaction");
            transaction.setAsset(unit.getDescription());
            return transaction;
        } else {
            throw new UnitNotFoundException(unit.getId());
        }
    }
}
