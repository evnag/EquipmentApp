package com.example.equipmentapp.ui.views.list;

import com.example.equipmentapp.backend.configuration.SecurityService;
import com.example.equipmentapp.backend.entity.Authority;
import com.example.equipmentapp.backend.entity.Employee;
import com.example.equipmentapp.backend.entity.User;
import com.example.equipmentapp.backend.event.FormEvent;
import com.example.equipmentapp.backend.repository.AuthorityRepository;
import com.example.equipmentapp.backend.service.EmployeeService;
import com.example.equipmentapp.backend.service.UserService;
import com.example.equipmentapp.ui.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "user-list", layout = MainLayout.class)
@PageTitle("Users | EquipmentApp CRM")
@RolesAllowed("ADMIN")
public class UserListView extends VerticalLayout {

    private final UserForm userForm;
    Grid<User> grid = new Grid<>(User.class);
    TextField filter = new TextField();
    private final UserService userService;
    private final AuthorityRepository authorityRepository;
    private final SecurityService securityService;

    public UserListView(UserService userService,
                        EmployeeService employeeService,
                        AuthorityRepository authorityRepository,
                        SecurityService securityService) {
        this.userService = userService;
        this.authorityRepository = authorityRepository;
        this.securityService = securityService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        userForm = new UserForm(employeeService.findAll(), authorityRepository);

        userForm.addSaveListener(this::saveUser);
        userForm.addDeleteListener(this::deleteUser);
        userForm.addCloseListener(e -> closeEditor());

        Div content = new Div(grid, userForm);
        content.addClassName("content");
        content.setSizeFull();

        add(
                new H1("Пользователи"),
                getToolBar(),
                content
        );
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        userForm.setUser(null);
        userForm.setVisible(false);
        removeClassName("editing");
    }

    private HorizontalLayout getToolBar() {
        filter.setPlaceholder("поиск");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> updateList());

        Button addUserButton = new Button("Добавить", click -> addUser());

        HorizontalLayout toolbar = new HorizontalLayout(filter, addUserButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void configureGrid() {
        grid.addClassName("grid");
        grid.setSizeFull();
        grid.setColumns(
                "id",
                "username",
                "password",
                "enabled"
        );
        grid.getColumnByKey("username").setHeader("Пользователь");
        grid.getColumnByKey("password").setHeader("Пароль");
        grid.getColumnByKey("enabled").setHeader("Активен");
        grid.addColumn(user -> {
            Employee employee = user.getEmployeeId();
            return employee == null ? "-" : employee.getLastName();
        }).setHeader("Сотрудник").setSortable(true);
        grid.addColumn(user -> {
            Authority authority = authorityRepository.getByUserName(user.getUsername());
            return authority == null ? "-" : authority.getAuthority();
        }).setHeader("Роль").setSortable(true);

        grid.getColumns().forEach((c -> c.setAutoWidth(true)));

        grid.asSingleSelect().addValueChangeListener(event -> editUser(event.getValue()));
    }

    private void editUser(User user) {
        if (user == null) {
            closeEditor();
        } else {
            userForm.setUser(user);
            userForm.authority.setValue(setUserAuthority(user));
            userForm.setVisible(true);
            addClassName("editing");
        }
    }

    private String setUserAuthority(User user) {
        if (authorityRepository.getByUserName(user.getUsername()) == null) {
            return "ROLE_USER";
        }
        return authorityRepository.getByUserName(user.getUsername()).getAuthority();
    }

    private void addUser() {
        grid.asSingleSelect().clear();
        editUser(new User());
        userForm.delete.setEnabled(false);
    }

    private void saveUser(FormEvent.SaveEvent saveEvent) {
        User user = (User) saveEvent.getObject();
        user.setPassword(securityService.encodePassword(user.getPassword()));
        userService.save((User) saveEvent.getObject());
        setAuthority(saveEvent);
        updateList();
        closeEditor();
    }

    private void setAuthority(FormEvent.SaveEvent saveEvent) {
        User user = (User) saveEvent.getObject();
        Authority auth;
        if (authorityRepository.getByUserName(user.getUsername()) == null) {
            auth = new Authority(userForm.username.getValue(), userForm.authority.getValue());
        } else {
            auth = authorityRepository.getByUserName(user.getUsername());
            auth.setAuthority(userForm.authority.getValue());
        }
        authorityRepository.save(auth);
    }

    private void deleteUser(FormEvent.DeleteEvent deleteEvent) {
        User user = (User) deleteEvent.getObject();
        authorityRepository.delete(authorityRepository.getByUserName(user.getUsername()));
        userService.delete(user);
        updateList();
        closeEditor();
    }

    private void showNotification(String msg) {
        Notification.show(msg);
    }

    private void updateList() {
        grid.setItems(userService.findAll(filter.getValue()));
    }
}
