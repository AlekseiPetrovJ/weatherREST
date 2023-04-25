package ru.petrov.weatherREST.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.petrov.weatherREST.models.Sensor;

@Repository
public interface SensorsRepository extends JpaRepository<Sensor, Integer> {

}
