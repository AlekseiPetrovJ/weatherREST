package ru.petrov.weatherREST.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.petrov.weatherREST.dto.SensorDTO;
import ru.petrov.weatherREST.models.Sensor;
import ru.petrov.weatherREST.services.SensorsService;
import ru.petrov.weatherREST.util.EntityErrorResponse;
import ru.petrov.weatherREST.util.EntityNotCreatedException;
import ru.petrov.weatherREST.util.SensorNotFoundException;
import ru.petrov.weatherREST.util.SensorValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
@SecurityRequirement(name = "bearerAuth")
public class SensorsController {

    private final SensorsService sensorsService;
    private final ModelMapper mapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(SensorsService sensorsService, ModelMapper mapper, SensorValidator sensorValidator) {
        this.sensorsService = sensorsService;
        this.mapper = mapper;
        this.sensorValidator = sensorValidator;
    }

    @GetMapping
    public List<SensorDTO> getSensors() {
        return sensorsService.findAll()
                .stream()
                .map(person -> mapper.map(person, SensorDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Hidden
    public SensorDTO getPerson(@PathVariable("id") int id) {
        return mapper.map(sensorsService.findOne(id), SensorDTO.class);
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        Sensor sensor = mapper.map(sensorDTO, Sensor.class);
        sensorValidator.validate(sensor, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new EntityNotCreatedException(errorMsg.toString());
        }
        sensorsService.save(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<EntityErrorResponse> handleException(SensorNotFoundException e) {
        EntityErrorResponse response = new EntityErrorResponse(
                "Датчик с таким id не найден",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<EntityErrorResponse> handleException(EntityNotCreatedException e) {
        EntityErrorResponse response = new EntityErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
