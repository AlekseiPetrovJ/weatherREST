package ru.petrov.weatherrest.services;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.petrov.weatherrest.models.Sensor;
import ru.petrov.weatherrest.repositories.SensorsRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class SensorsServiceTest {

    private final SensorsRepository sensorsRepository;

    @Autowired
    public SensorsServiceTest(SensorsRepository sensorsRepository) {
        this.sensorsRepository = sensorsRepository;
    }

    @Test
    void findAll() {
        List<Sensor> expected = List.of(
                new Sensor("New York 1"),
                new Sensor("New York 2"),
                new Sensor("Moscow 1"),
                new Sensor("London 1")

        );
        List<Sensor> actual = sensorsRepository.findAll();
        Assert.assertThat(expected, Matchers.containsInAnyOrder(actual.toArray()));
    }

    @Test
    void findOne() {
        String name = "New York 1";
        Sensor expected =  new Sensor(name);


        Sensor actual = sensorsRepository.findByName(name).orElse(new Sensor());
        Assert.assertEquals(expected, actual);
    }

    @Test
    void findOneNotFoundByName() {
        String name = "New York 33";

        Sensor found = sensorsRepository.findByName(name).orElse(null);
        Assert.assertNull(found);
    }

}