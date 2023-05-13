package ru.petrov.weatherRest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petrov.weatherRest.models.Measurement;
import ru.petrov.weatherRest.repositories.MeasurementRepository;

import java.util.List;

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


    @Transactional
    public void save(Measurement measurement) {
        measurementRepository.save(measurement);
    }

    public int getRainyDaysCount(boolean raining) {
        return measurementRepository.findByRaining(raining).size();
    }
}
