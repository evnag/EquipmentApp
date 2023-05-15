package com.example.equipmentapp.ui.views.list;

import com.example.equipmentapp.backend.entity.Request;
import com.example.equipmentapp.backend.service.RequestService;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;

public class RequestsConfirmDialog extends Div {

    ConfirmDialog popUpDialog = new ConfirmDialog();
    Binder<Request> binder = new Binder<>(Request.class);
    private final RequestService requestService;


    public RequestsConfirmDialog(RequestService requestService) {
        this.requestService = requestService;

        HorizontalLayout layout = new HorizontalLayout();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        popUpDialog.setHeader("Обработка заявки");
        popUpDialog.setText("Нажав на кнопку \"Confirm\" заявка перейдет в статус \"Completed\". Закрыть заявку?");

        popUpDialog.setCancelable(true);
        popUpDialog.addConfirmListener(event -> setRequestCompleted());

        // Center the button
        getStyle().set("position", "fixed").set("top", "0").set("right", "0")
                .set("bottom", "0").set("left", "0").set("display", "flex")
                .set("align-items", "center").set("justify-content", "center");
    }

    private void setRequestCompleted() {
        if (binder.isValid()) {
            requestService.save(binder.getBean());
        }
    }

    public void setRequestCompletedTrue(Request request) {
        request.setCompleted(true);
        binder.setBean(request);
    }
}
