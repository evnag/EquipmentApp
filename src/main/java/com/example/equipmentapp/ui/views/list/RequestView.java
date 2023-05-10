package com.example.equipmentapp.ui.views.list;

import com.example.equipmentapp.backend.configuration.SecurityService;
import com.example.equipmentapp.backend.entity.Request;
import com.example.equipmentapp.backend.repository.UserRepository;
import com.example.equipmentapp.backend.service.RequestService;
import com.example.equipmentapp.ui.MainLayout;
import com.example.equipmentapp.ui.event.FormEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.userdetails.UserDetails;

@Route(value = "requests", layout = MainLayout.class)
@PageTitle("Requests | EquipmentApp CRM")
@RolesAllowed({"ADMIN", "USER"})
public class RequestView extends VerticalLayout {

    private final RequestForm form;
    private final SecurityService securityService;
    private final RequestService requestService;

    public RequestView(SecurityService securityService,
                       RequestService requestService,
                       UserRepository userRepository) {
        this.securityService = securityService;
        this.requestService = requestService;
        form = new RequestForm();
        form.addSaveListener(this::submitRequest);
        form.addCloseListener(e -> closeEditor());

        Request request = new Request();
        request.setDescription(form.description.getValue());
        request.setDate(form.date.getValue());
        request.setUserId(userRepository.findByUsername(getCurrentUsername()));

        form.setRequest(request);
        add(
                displayComponentByAuthority()
        );
    }

    private String getCurrentUsername() {
        return securityService.getAuthenticatedUser().getUsername();
    }

    public Component displayComponentByAuthority() {
        UserDetails details = securityService.getAuthenticatedUser();
        H1 header;
        Div content;
        if (details != null && details.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            header = new H1("Составить заявку");
            content = new Div(header, form);
            content.addClassName("content");
            content.setSizeFull();
            return content;
        } else if (details != null && details.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            header = new H1("Заявки");
            content = new Div(header, new RequestTabs(requestService));
            content.addClassName("content");
            content.setSizeFull();
            return content;
        }
        return new Text("Anonymous view");
    }

    private void submitRequest(FormEvent.SaveEvent saveEvent) {
        Request request = (Request) saveEvent.getObject();
        requestService.save(request);
        Notification.show("Заявка отправлена");
        closeEditor();
    }

    private void closeEditor() {
        form.description.clear();
        form.date.clear();
    }
}
