package ru.petrov.weatherrest.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.petrov.weatherrest.dto.MeasurementDTO;
import ru.petrov.weatherrest.models.Measurement;
import ru.petrov.weatherrest.services.MeasurementsService;
import ru.petrov.weatherrest.util.EntityNotCreatedException;
import ru.petrov.weatherrest.util.ErrorResponse;
import ru.petrov.weatherrest.util.MeasurementNotFoundException;
import ru.petrov.weatherrest.util.MeasurementValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
@SecurityRequirement(name = "bearerAuth")
public class MeasurementsController {

    private final MeasurementsService measurementsService;
    private final MeasurementValidator measurementValidator;
    private final ModelMapper mapper;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, MeasurementValidator measurementValidator, ModelMapper mapper) {
        this.measurementsService = measurementsService;
        this.measurementValidator = measurementValidator;
        this.mapper = mapper;
    }

    @GetMapping
    public List<MeasurementDTO> getMeasurements() {
        return measurementsService.findAll()
                .stream()
                .map(measurement -> mapper.map(measurement, MeasurementDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public MeasurementDTO getMeasurement(@PathVariable("id") int id) {
        return mapper.map(measurementsService.findOne(id), MeasurementDTO.class);
    }

    @GetMapping("/rainyDaysCount")
    public int getRainyDaysCount() {
        return measurementsService.getRainyDaysCount(true);
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO,
                                             BindingResult bindingResult) {
        Measurement measurement = mapper.map(measurementDTO, Measurement.class);
        measurement.setId(0);
        measurementValidator.validate(measurement, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
                        .append(";");
            }
            throw new EntityNotCreatedException(errorMsg.toString());
        }
        measurement.setCreatedAt(LocalDateTime.now());

        measurementsService.save(measurement);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(measurement.getId()).toUri()).build();

    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(EntityNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(MeasurementNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Измерение с таким id не найдено",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
