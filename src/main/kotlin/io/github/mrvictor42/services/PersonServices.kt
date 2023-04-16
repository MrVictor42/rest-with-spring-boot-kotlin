package io.github.mrvictor42.services

import io.github.mrvictor42.controller.PersonController
import io.github.mrvictor42.data.vo.v1.PersonVO
import io.github.mrvictor42.exception.RequiredObjectsIsNullExceptions
import io.github.mrvictor42.exception.ResourceNotFoundException
import io.github.mrvictor42.mapper.DozerMapper
import io.github.mrvictor42.mapper.custom.PersonMapper
import io.github.mrvictor42.model.Person
import io.github.mrvictor42.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonServices {

    @Autowired
    private lateinit var personRepository: PersonRepository
    @Autowired
    private lateinit var mapper : PersonMapper
    private val logger = Logger.getLogger(PersonServices::class.java.name)

    fun findById(id : Long) : PersonVO {
        logger.info("Finding a person!")
        val person = personRepository.findById(id).orElseThrow {
            ResourceNotFoundException("No records found for this ID!")
        }
        val personVO : PersonVO = DozerMapper.parserObject(person, PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.id).withSelfRel()

        personVO.add(withSelfRel)

        return personVO
    }

    fun findAll() : List<PersonVO> {
        logger.info("Finding all people!")
        val persons = personRepository.findAll()

        val vos = DozerMapper.parserListObjects(persons, PersonVO::class.java)

        vos.forEach { person ->
            val withSelfRel = linkTo(PersonController::class.java).slash(person.id).withSelfRel()
            person.add(withSelfRel)
        }

        return vos
    }

    fun create(person : PersonVO?) : PersonVO {
        if(person == null) throw RequiredObjectsIsNullExceptions()
        logger.info("Creating person with name ${ person.firstName }!")
        val entity : Person = DozerMapper.parserObject(person, Person::class.java)

        val personVO : PersonVO = DozerMapper.parserObject(personRepository.save(entity), PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.id).withSelfRel()

        personVO.add(withSelfRel)

        return personVO
    }

    fun update(person: PersonVO?) : PersonVO {
        if(person == null) throw RequiredObjectsIsNullExceptions()
        logger.info("Updating person with ID ${ person.id }!")
        val entity = personRepository.findById(person.id).orElseThrow {
            ResourceNotFoundException("No records found for this ID!")
        }

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender

        val personVO : PersonVO = DozerMapper.parserObject(personRepository.save(entity), PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.id).withSelfRel()

        personVO.add(withSelfRel)

        return personVO
    }

    fun delete(id: Long) {
        logger.info("Deleting person with ID ${ id }!")
        val entity = personRepository.findById(id).orElseThrow {
            ResourceNotFoundException("No records found for this ID!")
        }
        personRepository.delete(entity)
    }
}