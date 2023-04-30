package ru.petrov.weatherREST.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.petrov.weatherREST.models.Measurement;
import ru.petrov.weatherREST.services.SensorsService;

@Component
public class MeasurementValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Measurement.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Measurement measurement = (Measurement) o;

        if (measurement.getSensor() == null) {
            return;
        }
        if (measurement.getSensor().getName() == null || measurement.getSensor().getName().isEmpty()) {
            errors.rejectValue("sensor", "Датчик не должен быть пустым!");
        } else if (sensorsService.findOne(measurement.getSensor().getName()).isEmpty()) {
            errors.rejectValue("sensor", "Нет датчика с таким именем!");
        } else {
            measurement.setSensor(sensorsService.findOne(measurement.getSensor().getName()).orElse(null));
        }
    }
}
