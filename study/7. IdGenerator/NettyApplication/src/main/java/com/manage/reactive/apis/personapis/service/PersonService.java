package com.manage.reactive.apis.personapis.service;

import com.manage.reactive.apis.id_generator.SnowFlakeIdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manage.reactive.apis.common.response.Response;
import com.manage.reactive.apis.personapis.domain.dto.PersonDto;
import com.manage.reactive.apis.personapis.domain.entity.Person;
import com.manage.reactive.apis.personapis.domain.repository.PersonRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonService {

    @Value("${serverSpec.serverId}")
    private Long serverId;

    @Value("${serverSpec.dataCenterId}")
    private Long dataCenterId;
    
    private final PersonRepository personRepository;

    private final SnowFlakeIdGenerator idGenerator = new SnowFlakeIdGenerator();

    //insert case
    @Transactional
    public Mono<String> insert(PersonDto personDto) throws Exception {
        Person person = new Person();
        BeanUtils.copyProperties(personDto, person);

        String genId = idGenerator.generateNewId(serverId, dataCenterId).toString();

        person.setId(genId)
                .setNew(true).setCretId("cretHost");

        return personRepository.save(person).then(Response.responseOk);

    }

    
    //read All
    @Transactional
    public Flux<PersonDto> getAllPerson(){
        //Entity to Dto (mapping)
        return personRepository.findAll().map(person -> {
            PersonDto personDto = new PersonDto();
            BeanUtils.copyProperties(person, personDto);
            return personDto; //flatMap을 사용하면 하나씩 return되게 됨.
        });
    }


    //getPersonByEmail
    @Transactional
    public Mono<PersonDto> getPersonByEmail(String email){
        return personRepository.findByEmail(email).map(person -> {
            PersonDto personDto = new PersonDto();
            BeanUtils.copyProperties(person, personDto);
            return personDto;
        });
    }


    //update
    @Transactional
    public Mono<String> update(PersonDto personDto){
        Mono<Person> monoPerson = personRepository.findById(personDto.getId()).map(person ->{
            BeanUtils.copyProperties(personDto, person, "id");
            return person;
        });

        //publisher째 저장할수 있다.
        return personRepository.saveAll(monoPerson).then(Response.responseOk);
    }


    //delete
    @Transactional
    public Mono<String> delete(String id){
        return personRepository.deleteById(id).then(Response.responseOk);
    }

}
