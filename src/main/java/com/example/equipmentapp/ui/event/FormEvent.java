package com.example.equipmentapp.ui.event;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.formlayout.FormLayout;

public abstract class FormEvent extends ComponentEvent<FormLayout> {

    private final Object object;

    public FormEvent(FormLayout source, Object object) {
        super(source, false);
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public static class SaveEvent extends FormEvent {
        public SaveEvent(FormLayout source, Object object) {
            super(source, object);
        }
    }

    public static class DeleteEvent extends FormEvent {
        public DeleteEvent(FormLayout source, Object object) {
            super(source, object);
        }
    }

    public static class CloseEvent extends FormEvent {
        public CloseEvent(FormLayout source) {
            super(source, null);
        }
    }
}
