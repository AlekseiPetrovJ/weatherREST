package ru.petrov.weatherREST.util;

public class SensorNotCreatedException extends RuntimeException{
    public SensorNotCreatedException(String message) {
        super(message);
    }
}
