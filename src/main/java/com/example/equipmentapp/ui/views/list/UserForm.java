package com.example.equipmentapp.ui.views.list;

import com.example.equipmentapp.backend.entity.Employee;
import com.example.equipmentapp.backend.entity.User;
import com.example.equipmentapp.backend.event.FormEvent;
import com.example.equipmentapp.backend.repository.AuthorityRepository;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class UserForm extends FormLayout {

    TextField username = new TextField("Имя пользователя");
    TextField password = new TextField("Пароль");
    Checkbox enabled = new Checkbox("Активирован");
    ComboBox<Employee> employeeId = new ComboBox<>("Сотрудник");
    TextField authority = new TextField("Роль");
    private final AuthorityRepository authorityRepository;


    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<User> binder = new BeanValidationBinder<>(User.class);

    public UserForm(List<Employee> employees, AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
        addClassName("form");

        binder.bindInstanceFields(this);
        employeeId.setItems(employees);
        employeeId.setItemLabelGenerator(Employee::getLastName);


        add(
                username,
                password,
                enabled,
                employeeId,
                authority,
                createButtonsLayOut()
        );
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
        binder.addStatusChangeListener(event -> delete.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void setUser(User user) {
        binder.setBean(user);
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
