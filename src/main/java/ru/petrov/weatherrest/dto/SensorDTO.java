package ru.petrov.weatherrest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @NotEmpty(message = "Имя датчика не должно быть пустым")
    @Size(min = 3, max = 60, message = "Имя должно содержать от 3 до 30 символов")
    @Schema(example = "Ufa 2")
    private String name;

    @Schema(hidden = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
