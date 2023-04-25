package ru.petrov.weatherREST.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @NotEmpty(message = "Имя датчика не должно быть пустым")
    @Size(min = 3, max = 60, message = "Имя должно содержать от 3 до 30 символов")
    @Column(name = "name")
    //TODO Spring validator
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SensorDTO() {

    }
}
