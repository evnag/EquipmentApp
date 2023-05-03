package com.example.equipmentapp.ui.views.list;

import com.example.equipmentapp.backend.entity.Category;
import com.example.equipmentapp.ui.event.FormEvent;
import com.example.equipmentapp.backend.service.CategoryService;
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

@Route(value = "categories", layout = MainLayout.class)
@PageTitle("Category | EquipmentApp CRM")
@RolesAllowed("ADMIN")
public class CategoryListView extends VerticalLayout {

    private final CategoryForm categoryForm;
    private final CategoryService categoryService;
    Grid<Category> grid = new Grid<>(Category.class);
    TextField filter = new TextField();

    public CategoryListView(CategoryService categoryService) {
        this.categoryService = categoryService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        categoryForm = new CategoryForm();

        categoryForm.addSaveListener(this::saveCategory);
        categoryForm.addDeleteListener(this::deleteCategory);
        categoryForm.addCloseListener(e -> closeEditor());

        Div content = new Div(grid, categoryForm);
        content.addClassName("content");
        content.setSizeFull();

        add(
                new H1("Категории оборудования"),
                getToolBar(),
                content
        );
        updateList();
        closeEditor();
    }

    private void deleteCategory(FormEvent.DeleteEvent deleteEvent) {
        Category category = (Category) deleteEvent.getObject();
        categoryService.delete(category);
        this.showNotification(category.getName() + " deleted");
        updateList();
        closeEditor();
    }

    private void saveCategory(FormEvent.SaveEvent saveEvent) {
        Category category = (Category) saveEvent.getObject();
        categoryService.save(category);
        this.showNotification(category.getName() + " saved");
        updateList();
        closeEditor();
    }

    public void showNotification(String msg) {
        Notification.show(msg);
    }

    private void configureGrid() {
        grid.addClassName("grid");
        grid.setSizeFull();
        grid.setColumns(
                "id",
                "name"
        );
        grid.getColumnByKey("name").setHeader("Категория");

        grid.getColumns().forEach((c -> c.setAutoWidth(true)));

        grid.asSingleSelect().addValueChangeListener(event -> editCategory(event.getValue()));

    }

    private void updateList() {
        grid.setItems(categoryService.findAll(filter.getValue()));
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
        editCategory(new Category());
    }

    private void editCategory(Category category) {
        if (category == null) {
            closeEditor();
        } else {
            categoryForm.setCategory(category);
            categoryForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        categoryForm.setCategory(null);
        categoryForm.setVisible(false);
    }
}
