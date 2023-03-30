package io.github.mrvictor42.controller

import io.github.mrvictor42.data.vo.v1.PersonVO
import io.github.mrvictor42.data.vo.v2.PersonVO as PersonVOV2
import io.github.mrvictor42.services.PersonServices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/person")
class PersonController {

    @Autowired
    private lateinit var personServices: PersonServices // var service : PersonService = null

    @GetMapping("/{id}")
    fun getPerson(@PathVariable("id") id : Long) : PersonVO {
        return personServices.findById(id)
    }

    @GetMapping("/people")
    fun getPeopleList() : List<PersonVO> {
        return personServices.findAll()
    }

    //Outra forma de usar o @PostMapping
    @PostMapping(
        "v1",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody person : PersonVO) : PersonVO {
        return personServices.create(person)
    }

    @PutMapping("/update", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun update(@RequestBody person : PersonVO) : PersonVO {
        return personServices.update(person)
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable("id") id : Long) : Any {

        personServices.delete(id)
        return ResponseEntity.noContent().build<Any>()
    }

    @PostMapping(
        "/v2",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createV2(@RequestBody person : PersonVOV2) : PersonVOV2 {
        return personServices.createV2(person)
    }
}