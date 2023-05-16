package ru.petrov.weatherrest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petrov.weatherrest.models.Sensor;
import ru.petrov.weatherrest.repositories.SensorsRepository;
import ru.petrov.weatherrest.util.SensorNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorsService {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsService(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    public List<Sensor> findAll() {
        return sensorsRepository.findAll();
    }

    public Sensor findOne(int id) {
        Optional<Sensor> foundSensor = sensorsRepository.findById(id);
        return foundSensor.orElseThrow(SensorNotFoundException::new);
    }

    public Optional<Sensor> findOne(String name) {
        return sensorsRepository.findByName(name);
    }

@Transactional
public void save(Sensor sensor) {
    sensorsRepository.save(sensor);
}
}
