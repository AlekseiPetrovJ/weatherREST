package ru.petrov.weatherREST.controllers;

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
import ru.petrov.weatherREST.util.SensorErrorResponse;
import ru.petrov.weatherREST.util.SensorNotCreatedException;
import ru.petrov.weatherREST.util.SensorNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorsService sensorsService;
    private final ModelMapper mapper;

    @Autowired
    public SensorsController(SensorsService sensorsService, ModelMapper mapper) {
        this.sensorsService = sensorsService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<SensorDTO> getSensors() {
        return sensorsService.findAll()
                .stream()
                .map(person -> mapper.map(person, SensorDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SensorDTO getPerson(@PathVariable("id") int id) {
        return mapper.map(sensorsService.findOne(id), SensorDTO.class);
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new SensorNotCreatedException(errorMsg.toString());
        }
        sensorsService.save(mapper.map(sensorDTO, Sensor.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                "Sensor with this id not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
