package ru.petrov.weatherrest.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.petrov.weatherrest.dto.SensorDTO;
import ru.petrov.weatherrest.models.Sensor;
import ru.petrov.weatherrest.services.SensorsService;
import ru.petrov.weatherrest.util.EntityNotCreatedException;
import ru.petrov.weatherrest.util.ErrorResponse;
import ru.petrov.weatherrest.util.SensorNotFoundException;
import ru.petrov.weatherrest.util.SensorValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/sensors", produces = MediaType.APPLICATION_JSON_VALUE)
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
    public SensorDTO getPerson(@PathVariable("id") int id) {
        return mapper.map(sensorsService.findOne(id), SensorDTO.class);
    }

    @PostMapping()
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        Sensor sensor = mapper.map(sensorDTO, Sensor.class);
        sensor.setId(0);
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
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(sensor.getId()).toUri()).build();
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    private ResponseEntity<ErrorResponse> handleException(SensorNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Датчик с таким id не найден",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    private ResponseEntity<ErrorResponse> handleException(EntityNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
