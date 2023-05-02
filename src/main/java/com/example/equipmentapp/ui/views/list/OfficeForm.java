package com.example.equipmentapp.ui.views.list;

import com.example.equipmentapp.backend.entity.Office;
import com.example.equipmentapp.ui.event.FormEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class OfficeForm extends FormLayout {

    TextField officeNumber = new TextField("Кабинет, №");
    TextField buildingNumber = new TextField("Корпус, №");
    TextArea description = new TextArea("Описание");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Office> binder = new BeanValidationBinder<>(Office.class);

    public OfficeForm() {
        addClassName("form");

        binder.bindInstanceFields(this);

        add(
                officeNumber,
                buildingNumber,
                description,
                createButtonsLayOut()
        );
    }

    public void setOffice(Office office) {
        binder.setBean(office);
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
//    public static abstract class OfficeFormEvent extends ComponentEvent<OfficeForm> {
//        private Office office;
//
//        protected OfficeFormEvent(OfficeForm source, Office office) {
//            super(source, false);
//            this.office = office;
//        }
//
//        public Office getOffice() {
//            return office;
//        }
//    }
//
//    public static class SaveEvent extends OfficeForm.OfficeFormEvent {
//        SaveEvent(OfficeForm source, Office office) {
//            super(source, office);
//        }
//    }
//
//    public static class DeleteEvent extends OfficeForm.OfficeFormEvent {
//        DeleteEvent(OfficeForm source, Office office) {
//            super(source, office);
//        }
//    }
//
//    public static class CloseEvent extends OfficeForm.OfficeFormEvent {
//        CloseEvent(OfficeForm source) {
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
