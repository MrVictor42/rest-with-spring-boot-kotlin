package io.github.mrvictor42.controller

import io.github.mrvictor42.model.Person
import io.github.mrvictor42.services.PersonServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/person")
class PersonController {

    @Autowired
    private lateinit var personServices: PersonServices // var service : PersonService = null

    @GetMapping("/{id}")
    fun getPerson(@PathVariable("id") id : Long) : Person {
        return personServices.findById(id)
    }

    @GetMapping("/people")
    fun getPeopleList() : List<Person> {
        return personServices.findAll()
    }

    //Outra forma de usar o @PostMapping
    @RequestMapping(
        method = [RequestMethod.POST],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody person : Person) : Person {
        return personServices.create(person)
    }

    @PutMapping("/update", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun update(@RequestBody person : Person) : Person {
        return personServices.update(person)
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") id : Long) {
        return personServices.delete(id)
    }
}