package ru.petrov.weatherrest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petrov.weatherrest.models.Measurement;
import ru.petrov.weatherrest.repositories.MeasurementRepository;
import ru.petrov.weatherrest.util.MeasurementNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementsService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    public Measurement findOne(int id) {
        Optional<Measurement> foundSensor = measurementRepository.findById(id);
        return foundSensor.orElseThrow(MeasurementNotFoundException::new);
    }


    @Transactional
    public void save(Measurement measurement) {
        measurementRepository.save(measurement);
    }

    public int getRainyDaysCount(boolean raining) {
        return measurementRepository.findByRaining(raining).size();
    }
}
