package com.example.equipmentapp.backend.exception;

public class UnitNotFoundException extends RuntimeException{
    public UnitNotFoundException(Long id) {
        super("Unit with id " + id + " not found");
    }
}
