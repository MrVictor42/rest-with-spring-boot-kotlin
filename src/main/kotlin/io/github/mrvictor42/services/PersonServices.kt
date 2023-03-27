package io.github.mrvictor42.services

import io.github.mrvictor42.model.Person
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger

@Service
class PersonServices {

    private val counter : AtomicLong = AtomicLong()
    private val logger = Logger.getLogger(PersonServices::class.java.name)

    fun findById(id : Long) : Person {
        logger.info("Finding a person!")
        val person : Person = Person()

        person.id = counter.incrementAndGet()
        person.firstName = "Victor"
        person.lastName = "Mota"
        person.address = "Brasilia"
        person.gender = "Male"

        return person
    }

    fun findAll() : List<Person> {
        logger.info("Finding all people!")
        val personList : MutableList<Person> = mutableListOf()

        for(aux in 0 .. 7) {
            val person : Person = mockPerson(aux)

            personList.add(person)
        }

        return personList
    }

    fun create(person : Person) : Person {

        return person
    }

    fun update(person: Person) : Person {

        return person
    }

    private fun mockPerson(number : Int) : Person {
        val person : Person = Person()

        person.id = counter.incrementAndGet()
        person.firstName = "Person name $number"
        person.lastName = "Mota"
        person.address = "Some address in Braszil"
        person.gender = "Male"

        return person
    }

    fun delete(id: Long) {

    }
}