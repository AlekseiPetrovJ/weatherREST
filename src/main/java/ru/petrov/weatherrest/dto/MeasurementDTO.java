package ru.petrov.weatherrest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MeasurementDTO {
    @NotNull(message = "Значение температуры должно быть заполнено")
    @Min(value = -100, message = "Температура должна быть больше чем -100")
    @Max(value = 100, message = "Температура должна быть меньше чем 100")
    private Double value;

    @NotNull(message = "Значение дождливости должно быть заполнено.")
    private Boolean raining;

    @NotNull(message = "Датчик не должен быть пустым.")
    private SensorDTO sensor;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
