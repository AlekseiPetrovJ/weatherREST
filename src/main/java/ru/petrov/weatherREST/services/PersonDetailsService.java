package ru.petrov.weatherREST.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.petrov.weatherREST.models.Person;
import ru.petrov.weatherREST.repositories.PeopleRepository;
import ru.petrov.weatherREST.security.PersonDetails;

import java.util.Optional;

@Service
//Не обычный сервис. Чтобы Spring Security Знал что мы загружаем пользователя мы должны
//реализовать implements UserDetailsService
//Чтобы spring мог получить пользователя по имени
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;

    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(s);
        //Если пользователя с таким именем не нашли, то выбрасываем исключение UsernameNotFoundException
        //А Spring security покажет сообщение на странице авторизации
        if (person.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }

        //Если человек есть, то возвращаем PersonDetails c человеком
        //Так как PersonDetails реализует UserDetails мы можем его возвращать
        return new PersonDetails(person.get());
    }
}
