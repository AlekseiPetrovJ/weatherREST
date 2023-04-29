package ru.petrov.weatherREST.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.petrov.weatherREST.dto.MeasurementDTO;
import ru.petrov.weatherREST.models.Measurement;
import ru.petrov.weatherREST.services.MeasurementsService;
import ru.petrov.weatherREST.services.SensorsService;
import ru.petrov.weatherREST.util.SensorErrorResponse;
import ru.petrov.weatherREST.util.SensorNotCreatedException;
import ru.petrov.weatherREST.util.SensorNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementsService measurementsService;
    private final SensorsService sensorsService;

    private final ModelMapper mapper;
//    private final SensorValidator sensorValidator;
//    private final MeasurementsValidator measurementsValidator;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, SensorsService sensorsService, ModelMapper mapper) {
        this.measurementsService = measurementsService;
        this.sensorsService = sensorsService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<MeasurementDTO> getMeasurements() {
        return measurementsService.findAll()
                .stream()
                .map(measurement -> mapper.map(measurement, MeasurementDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public int getRainyDaysCount(){
        return measurementsService.getRainyDaysCount(true);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO,
                                             BindingResult bindingResult) {
        Measurement measurement = mapper.map(measurementDTO, Measurement.class);
        String sensorName = measurementDTO.getSensor().getName();
        measurement.setSensor(sensorsService.findOne(sensorName).orElse(null));
//        measurement.setSensor(sensor);

        //TODO Обдумать валидацию
//        sensorValidator.validate(sensor, bindingResult);
//        if (bindingResult.hasErrors()) {
//            StringBuilder errorMsg = new StringBuilder();
//            List<FieldError> errors = bindingResult.getFieldErrors();
//            for (FieldError error : errors) {
//                errorMsg.append(error.getField())
//                        .append(" - ")
//                        .append(error.getDefaultMessage())
//                        .append(";");
//            }
//            throw new SensorNotCreatedException(errorMsg.toString());
//        }
        measurement.setCreatedAt(LocalDateTime.now());

        measurementsService.save(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    //TODO Обработать
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
