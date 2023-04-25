package com.example.equipmentapp.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "admin")
@RolesAllowed("ADMIN")
public class AdminView extends VerticalLayout {
//    public AdminView(ArrivalService arrivalService, UnitTypeService unitTypeService) {
//
//        GridCrud<Arrival> crud = new GridCrud<>(Arrival.class);
//
//        TextField filter = new TextField();
//        filter.setPlaceholder("поиск по имени");
//        filter.setClearButtonVisible(true);
//        crud.getCrudLayout().addFilterComponent(filter);
//
////        crud.getGrid().setColumns("id", "name", "unitType", "unitSort", "totalArrival", "totalExpense", "totalBalance", "invoice", "date");
//
////        grid.addColumn(arrival -> arrival.getUnitType().getName()).setHeader("Тип учетной единицы");
////        grid.addColumn(arrival -> arrival.getUnitSort().getName()).setHeader("Вид учетной единицы");
//
//        crud.getGrid().addColumn(arrival -> arrival.getUnitType().getName()).setHeader("Тип учетной единицы");
//        crud.getGrid().addColumn(arrival -> arrival.getUnitSort().getName()).setHeader("Вид учетной единицы");
//        crud.getGrid().addColumn(arrival -> arrival.getInvoice().getNumber()).setHeader("Накладная, №");
//        crud.getGrid().getColumnByKey("id").setHeader("№");
//        crud.getGrid().getColumnByKey("name").setHeader("Наименование");
//        crud.getGrid().getColumnByKey("totalArrival").setHeader("Приход");
//        crud.getGrid().getColumnByKey("date").setHeader("Дата прихода");

//        List<String> numbers = new ArrayList<>();
//        numbers.add("One");
//        numbers.add("Ten");
//        numbers.add("Eleven");

//        crud.getCrudFormFactory().setUseBeanValidation(true);
//        crud.getCrudFormFactory().setVisibleProperties("name", "totalArrival", "unitType", "unitSort", "invoice", "date");
//        crud.getCrudFormFactory().setVisibleProperties(
//                CrudOperation.ADD,
//                "name", "totalArrival", "unitType", "unitSort", "invoice", "date"
//                );
//        crud.getCrudFormFactory().setFieldProvider("unitType",
//                new ComboBoxProvider<>(unitTypeService.findAll()));
//        crud.getCrudFormFactory().setFieldProvider("unitType",
//                new ComboBoxProvider<>("Тип уч. единицы", unitTypeService.findAll(), new TextRenderer<>(UnitType::getName), UnitType::getName));

//        setSizeFull();
//        add(
//                new H1("Записи о приходе"),
//                crud
//        );
//
//        filter.addValueChangeListener(e -> crud.refreshGrid());
//    }
}
