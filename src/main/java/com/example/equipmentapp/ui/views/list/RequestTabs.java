package com.example.equipmentapp.ui.views.list;

import com.example.equipmentapp.backend.entity.Request;
import com.example.equipmentapp.backend.service.RequestService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

public class RequestTabs extends Div {

    private final Tab open;
    private final Tab completed;
    private final VerticalLayout content;
    private final RequestService requestService;
    Grid<Request> grid = new Grid<>(Request.class);
    private final RequestsConfirmDialog dialog;


    public RequestTabs(RequestService requestService) {
        this.requestService = requestService;
        addClassName("list-view");
        setSizeFull();
        open = new Tab(new Span("Открытые"), createBadge(requestService.findAllOpened().size()));
        completed = new Tab(new Span("Закрытые"), createBadge(requestService.findAllCompleted().size()));
        Tabs tabs = new Tabs(open, completed);
        tabs.addSelectedChangeListener(
                event -> setContent(event.getSelectedTab()));
        configureRequestGrid();

        dialog = new RequestsConfirmDialog(requestService);

        content = new VerticalLayout();
        content.setSpacing(false);
        setContent(tabs.getSelectedTab());
        Div div = new Div(content);
        add(tabs, div);
    }

    private void configureRequestGrid() {
        grid.removeColumnByKey("userId");
        grid.setColumns("id", "date", "description", "completed");
        grid.addColumn(request -> request.getUserId().getId()).setHeader("User");

        grid.getColumns().forEach((c -> c.setAutoWidth(true)));
        grid.asSingleSelect().addValueChangeListener(event -> {
            editRequest(event.getValue());
        });
    }

    private void editRequest(Request request) {
        if (request != null && !request.getCompleted()) {
            dialog.popUpDialog.open();
            dialog.setRequestCompletedTrue(request);
            dialog.popUpDialog.addConfirmListener(event -> {
                grid.setItems(requestService.findAllOpened());
                grid.getDataProvider().refreshAll();
                open.removeAll();
                completed.removeAll();
                open.add(new Span("Открытые"), createBadge(requestService.findAllOpened().size()));
                completed.add(new Span("Закрытые"), createBadge(requestService.findAllCompleted().size()));
            });
        }
    }

    private void setContent(Tab tab) {
        content.removeAll();
        if (tab.equals(open)) {
            grid.setItems(requestService.findAllOpened());
            content.add(grid);
        } else {
            grid.setItems(requestService.findAllCompleted());
            content.add(grid);
        }
    }

    private Span createBadge(int value) {
        Span badge = new Span(String.valueOf(value));
        badge.getElement().getThemeList().add("badge small contrast");
        badge.getStyle().set("margin-inline-start", "var(--lumo-space-xs)");
        return badge;
    }
}
