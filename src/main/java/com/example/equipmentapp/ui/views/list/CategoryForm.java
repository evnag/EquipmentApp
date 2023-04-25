package com.example.equipmentapp.ui.views.list;

import com.example.equipmentapp.backend.entity.Category;
import com.example.equipmentapp.backend.event.FormEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class CategoryForm extends FormLayout {

    TextField name = new TextField("Категория");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Category> binder = new BeanValidationBinder<>(Category.class);

    public CategoryForm() {
        addClassName("form");

        binder.bindInstanceFields(this);

        add(name, createButtonsLayOut());
    }

    public void setCategory(Category category) {
        binder.setBean(category);
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
