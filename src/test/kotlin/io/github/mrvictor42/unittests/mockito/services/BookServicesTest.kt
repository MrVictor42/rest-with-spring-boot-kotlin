package io.github.mrvictor42.unittests.mockito.services

import io.github.mrvictor42.exception.RequiredObjectsIsNullExceptions
import io.github.mrvictor42.repository.BookRepository
import io.github.mrvictor42.repository.PersonRepository
import io.github.mrvictor42.services.BookServices
import io.github.mrvictor42.unittests.mocks.MockPerson
import io.github.mrvictor42.services.PersonServices
import io.github.mrvictor42.unittests.mocks.MockBook
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
class BookServicesTest {

    private lateinit var inputObject : MockBook
    @InjectMocks
    private lateinit var service : BookServices
    @Mock
    private lateinit var bookRepository: BookRepository
    @BeforeEach
    fun setUp() {
        inputObject = MockBook()
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findById() {
        val book = inputObject.mockEntity(1)

        book.id = 1
        `when`(bookRepository.findById(1)).thenReturn(Optional.of(book))

        val result = service.findById(1)

        assertNotNull(result)
        assertNotNull(result.id)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/book/1>;rel=\"self\""))
        assertEquals("Some Title1", result.title)
        assertEquals("Some Author1", result.author)
        assertEquals(25.0, result.price)
    }

    @Test
    fun findAll() {
        val list = inputObject.mockEntityList()

        `when`(bookRepository.findAll()).thenReturn(list)

        val bookList = service.findAll()

        assertNotNull(bookList)
        assertEquals(14, bookList.size)

        val bookOne = bookList[1]

        assertNotNull(bookOne)
        assertNotNull(bookOne.id)
        assertNotNull(bookOne.links)
        assertTrue(bookOne.links.toString().contains("</api/book/1>;rel=\"self\""))
        assertEquals("Some Title1", bookOne.title)
        assertEquals("Some Author1", bookOne.author)
        assertEquals(25.0, bookOne.price)
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

        `when`(bookRepository.save(entity)).thenReturn(persisted)

        val vo = inputObject.mockVO(1)
        val result = service.create(vo)

        assertNotNull(result)
        assertNotNull(result.id)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/book/1>;rel=\"self\""))
        assertEquals("Some Title1", result.title)
        assertEquals("Some Author1", result.author)
        assertEquals(25.0, result.price)
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

        `when`(bookRepository.findById(1)).thenReturn(Optional.of(entity))
        `when`(bookRepository.save(entity)).thenReturn(persisted)

        val vo = inputObject.mockVO(1)
        val result = service.update(vo)

        assertNotNull(result)
        assertNotNull(result.id)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("</api/book/1>;rel=\"self\""))
        assertEquals("Some Title1", result.title)
        assertEquals("Some Author1", result.author)
        assertEquals(25.0, result.price)
    }

    @Test
    fun delete() {
        val entity = inputObject.mockEntity(1)

        `when`(bookRepository.findById(1)).thenReturn(Optional.of(entity))
        service.delete(1)
    }
}