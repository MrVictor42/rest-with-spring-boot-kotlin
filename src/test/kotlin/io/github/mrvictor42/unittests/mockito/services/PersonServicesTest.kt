package io.github.mrvictor42.unittests.mockito.services

import io.github.mrvictor42.exception.RequiredObjectsIsNullExceptions
import io.github.mrvictor42.repository.PersonRepository
import io.github.mrvictor42.unittests.mocks.MockPerson
import io.github.mrvictor42.services.PersonServices
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class PersonServicesTest {

    private lateinit var inputObject : MockPerson
    @InjectMocks
    private lateinit var service : PersonServices
    @Mock
    private lateinit var personRepository: PersonRepository
    @BeforeEach
    fun setUp() {
        inputObject = MockPerson()
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findById() {
        val person = inputObject.mockEntity(1)

        person.id = 1
        `when`(personRepository.findById(1)).thenReturn(Optional.of(person))

        val result = service.findById(1)

        assertNotNull(result)
        assertNotNull(result.id)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/person/1>;rel=\"self\""))
        assertEquals("Address Test1", result.address)
        assertEquals("First Name Test1", result.firstName)
        assertEquals("Last Name Test1", result.lastName)
        assertEquals("Female", result.gender)
    }

    @Test
    fun findAll() {
        val list = inputObject.mockEntityList()

        `when`(personRepository.findAll()).thenReturn(list)

        val peopleList = service.findAll()

        assertNotNull(peopleList)
        assertEquals(14, peopleList.size)

        val personOne = peopleList[1]

        assertNotNull(personOne)
        assertNotNull(personOne.id)
        assertNotNull(personOne.links)
        assertTrue(personOne.links.toString().contains("</api/person/1>;rel=\"self\""))
        assertEquals("Address Test1", personOne.address)
        assertEquals("First Name Test1", personOne.firstName)
        assertEquals("Last Name Test1", personOne.lastName)
        assertEquals("Female", personOne.gender)
    }

    @Test
    fun createWithNullPerson() {
        val exception : Exception = assertThrows(
            RequiredObjectsIsNullExceptions::class.java
        ) { service.create(null) }

        val expectedMessage = "It's not allowed to persist a null object!!"
        val actualMessage = exception.message

        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun create() {
        val entity = inputObject.mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1

        `when`(personRepository.save(entity)).thenReturn(persisted)

        val vo = inputObject.mockVO(1)
        val result = service.create(vo)

        assertNotNull(result)
        assertNotNull(result.id)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/person/1>;rel=\"self\""))
        assertEquals("Address Test1", result.address)
        assertEquals("First Name Test1", result.firstName)
        assertEquals("Last Name Test1", result.lastName)
        assertEquals("Female", result.gender)
    }

    @Test
    fun updateWithNullPerson() {
        val exception : Exception = assertThrows(
            RequiredObjectsIsNullExceptions::class.java
        ) { service.update(null) }

        val expectedMessage = "It's not allowed to persist a null object!!"
        val actualMessage = exception.message

        assertTrue(actualMessage!!.contains(expectedMessage))
    }

    @Test
    fun update() {
        val entity = inputObject.mockEntity(1)

        val persisted = entity.copy()
        persisted.id = 1

        `when`(personRepository.findById(1)).thenReturn(Optional.of(entity))
        `when`(personRepository.save(entity)).thenReturn(persisted)

        val vo = inputObject.mockVO(1)
        val result = service.update(vo)

        assertNotNull(result)
        assertNotNull(result.id)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/person/1>;rel=\"self\""))
        assertEquals("Address Test1", result.address)
        assertEquals("First Name Test1", result.firstName)
        assertEquals("Last Name Test1", result.lastName)
        assertEquals("Female", result.gender)
    }

    @Test
    fun delete() {
        val entity = inputObject.mockEntity(1)

        `when`(personRepository.findById(1)).thenReturn(Optional.of(entity))
        service.delete(1)
    }
}