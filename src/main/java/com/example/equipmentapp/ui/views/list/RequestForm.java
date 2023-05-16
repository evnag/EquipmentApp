package com.example.equipmentapp.ui.views.list;

import com.example.equipmentapp.backend.entity.Request;
import com.example.equipmentapp.ui.event.FormEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class RequestForm extends FormLayout {

    TextField description = new TextField("Описание");
    DatePicker date = new DatePicker("Дата заявки");

    Button save = new Button("Submit");
    Button close = new Button("Cancel");

    Binder<Request> binder = new BeanValidationBinder<>(Request.class);

    public RequestForm() {
        addClassName("form");
        binder.bindInstanceFields(this);

        add(description, date, createButtonsLayOut());
    }

    public void setRequest(Request request) {
        binder.setBean(request);
    }

    private Component createButtonsLayOut() {
        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        close.addClickListener(click -> fireEvent(new FormEvent.CloseEvent(this)));
        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new FormEvent.SaveEvent(this, binder.getBean()));
        }
    }

    public Registration addSaveListener(ComponentEventListener<FormEvent.SaveEvent> listener) {
        return addListener(FormEvent.SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<FormEvent.CloseEvent> listener) {
        return addListener(FormEvent.CloseEvent.class, listener);
    }
}
