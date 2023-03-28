package io.github.mrvictor42.services

import io.github.mrvictor42.exception.ResourceNotFoundException
import io.github.mrvictor42.model.Person
import io.github.mrvictor42.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonServices {

    @Autowired
    private lateinit var personRepository: PersonRepository
    private val logger = Logger.getLogger(PersonServices::class.java.name)

    fun findById(id : Long) : Person {
        logger.info("Finding a person!")
        return personRepository.findById(id).orElseThrow {
            ResourceNotFoundException("No records found for this ID!")
        }
    }

    fun findAll() : List<Person> {
        logger.info("Finding all people!")
        return personRepository.findAll()
    }

    fun create(person : Person) : Person {
        logger.info("Creating person with name ${ person.firstName }!")
        return personRepository.save(person)
    }

    fun update(person: Person) : Person {
        logger.info("Updating person with ID ${ person.id }!")
        val entity = personRepository.findById(person.id).orElseThrow {
            ResourceNotFoundException("No records found for this ID!")
        }

        entity.firstName = person.firstName
        entity.lastName = person.lastName
        entity.address = person.address
        entity.gender = person.gender

        return personRepository.save(entity)
    }

    fun delete(id: Long) {
        logger.info("Deleting person with ID ${ id }!")
        val entity = personRepository.findById(id).orElseThrow {
            ResourceNotFoundException("No records found for this ID!")
        }
        personRepository.delete(entity)
    }
}