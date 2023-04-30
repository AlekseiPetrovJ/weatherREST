package ru.petrov.weatherREST.util;

public class EntityNotCreatedException extends RuntimeException {
    public EntityNotCreatedException(String message) {
        super(message);
    }
}
