package ru.petrov.weatherREST.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petrov.weatherREST.models.Sensor;
import ru.petrov.weatherREST.repositories.SensorsRepository;
import ru.petrov.weatherREST.util.SensorNotFoundException;

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void save(Sensor sensor) {
        sensorsRepository.save(sensor);
    }
}
