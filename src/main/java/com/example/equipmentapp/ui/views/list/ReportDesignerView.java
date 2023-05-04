package com.example.equipmentapp.ui.views.list;

import com.example.equipmentapp.backend.entity.Unit;
import com.example.equipmentapp.backend.repository.UnitRepository;
import com.example.equipmentapp.backend.service.UnitService;
import com.example.equipmentapp.ui.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.reports.PrintPreviewReport;

import javax.annotation.security.RolesAllowed;

@Route(value = "reports", layout = MainLayout.class)
@PageTitle("Report Designer | EquipmentApp CRM")
@RolesAllowed("ADMIN")
public class ReportDesignerView extends VerticalLayout {

//    TabSheet tabSheet = new TabSheet();
//    Dialog dialog = new Dialog();

    public ReportDesignerView(UnitRepository unitRepository) {
        PrintPreviewReport<Unit> report = new PrintPreviewReport<>(
                Unit.class,
                "model",
                "serialNumber",
                "inventoryNumber",
                "incomeDate",
                "outcomeDate",
                "description");
        report.setItems(unitRepository.findAll());
        report.getReportBuilder().setTitle("Title");
//        tabSheet.add("Print unit by ID",
//                new Div(new Text("This is the fist tab content"), reportToolBar()));
//        tabSheet.add("Print units by officeId ",
//                new Div(new Text("This is the second tab content")));
//        tabSheet.add("Print units by categoryId?name",
//                new Div(new Text("This is the third tab content")));
//        tabSheet.add("Print units by employeeId?name",
//                new Div(new Text("This is the third tab content")));

        add(
               report
        );
    }

//    private HorizontalLayout reportToolBar() {
//        TextField field = new TextField("Type some digits");
//        UI.getCurrent().addShortcutListener(event -> showDialog(field.getValue()), Key.ENTER);
//        return new HorizontalLayout(field);
//    }
//
//    private void showDialog(String id) {
//        if (id == null || id.isEmpty()) {
//            Notification.show("Input unit id must not be null");
//            return;
//        }
//        configuredDialog();
//        dialog.open();
//    }
//
//    private void configuredDialog() {
//        Button cancelButton = new Button("Cancel", e -> dialog.close());
//        dialog.setHeaderTitle("Print this?");
//        dialog.setSizeFull();
//        dialog.getFooter().add(cancelButton);
//
//        VerticalLayout layout = new VerticalLayout();
//
//        dialog.add(layout);
//    }
}
