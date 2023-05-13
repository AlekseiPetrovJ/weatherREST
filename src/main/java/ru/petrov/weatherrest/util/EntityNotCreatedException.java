package ru.petrov.weatherrest.util;

public class EntityNotCreatedException extends RuntimeException {
    public EntityNotCreatedException(String message) {
        super(message);
    }
}
