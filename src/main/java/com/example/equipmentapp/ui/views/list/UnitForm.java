package com.example.equipmentapp.ui.views.list;

import com.example.equipmentapp.backend.entity.Category;
import com.example.equipmentapp.backend.entity.Employee;
import com.example.equipmentapp.backend.entity.Office;
import com.example.equipmentapp.backend.entity.Unit;
import com.example.equipmentapp.backend.event.FormEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class UnitForm extends FormLayout {

    TextField model = new TextField("Модель");
    TextField serialNumber = new TextField("Серийный №");
    TextField inventoryNumber = new TextField("Инвентарный №");
    TextField description = new TextField("Описание");
    DatePicker incomeDate = new DatePicker("Дата прихода");
    ComboBox<Category> categoryId = new ComboBox<>("Категория");
    ComboBox<Office> officeId = new ComboBox<>("Кабинет");
    ComboBox<Employee> employeeId = new ComboBox<>("Сотрудник");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Unit> binder = new BeanValidationBinder<>(Unit.class);

    public UnitForm(List<Category> categories,
                    List<Office> offices,
                    List<Employee> employees) {
        addClassName("form");

        binder.bindInstanceFields(this);

        categoryId.setItems(categories);
        categoryId.setItemLabelGenerator(Category::getName);
        officeId.setItems(offices);
        officeId.setItemLabelGenerator(Office::getDescription);
        employeeId.setItems(employees);
        employeeId.setItemLabelGenerator(Employee::getLastName);

        add(
                model,
                serialNumber,
                inventoryNumber,
                description,
                incomeDate,
                categoryId,
                officeId,
                employeeId,
                createButtonsLayOut()
        );
    }

    public void setUnit(Unit unit) {
        binder.setBean(unit);
    }

    private Component createButtonsLayOut() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new FormEvent.DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent(new FormEvent.CloseEvent(this)));

        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new FormEvent.SaveEvent(this, binder.getBean()));
        }
    }

    // Events
//    public static abstract class UnitFormEvent extends ComponentEvent<UnitForm> {
//        private Unit unit;
//
//        protected UnitFormEvent(UnitForm source, Unit unit) {
//            super(source, false);
//            this.unit = unit;
//        }
//
//        public Unit getUnit() {
//            return unit;
//        }
//    }
//
//    public static class SaveEvent extends UnitFormEvent {
//        SaveEvent(UnitForm source, Unit unit) {
//            super(source, unit);
//        }
//    }
//
//    public static class DeleteEvent extends UnitFormEvent {
//        DeleteEvent(UnitForm source, Unit unit) {
//            super(source, unit);
//        }
//    }
//
//    public static class CloseEvent extends UnitFormEvent {
//        CloseEvent(UnitForm source) {
//            super(source, null);
//        }
//    }

    public Registration addDeleteListener(ComponentEventListener<FormEvent.DeleteEvent> listener) {
        return addListener(FormEvent.DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<FormEvent.SaveEvent> listener) {
        return addListener(FormEvent.SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<FormEvent.CloseEvent> listener) {
        return addListener(FormEvent.CloseEvent.class, listener);
    }
}
