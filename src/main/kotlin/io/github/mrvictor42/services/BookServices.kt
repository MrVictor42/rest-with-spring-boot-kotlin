package io.github.mrvictor42.services

import io.github.mrvictor42.controller.BookController
import io.github.mrvictor42.data.vo.v1.BookVO
import io.github.mrvictor42.exception.RequiredObjectsIsNullExceptions
import io.github.mrvictor42.exception.ResourceNotFoundException
import io.github.mrvictor42.mapper.DozerMapper
import io.github.mrvictor42.model.Book
import io.github.mrvictor42.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class BookServices {

    @Autowired
    private lateinit var bookRepository: BookRepository
    private val logger = Logger.getLogger(BookServices::class.java.name)

    fun findById(id : Long) : BookVO {
        logger.info("Finding a Book!")
        val book = bookRepository.findById(id).orElseThrow {
            ResourceNotFoundException("No records found for this ID!")
        }
        val bookVO : BookVO = DozerMapper.parserObject(book, BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.id).withSelfRel()

        bookVO.add(withSelfRel)

        return bookVO
    }

    fun findAll() : List<BookVO> {
        logger.info("Finding all people!")
        val books = bookRepository.findAll()

        val vos = DozerMapper.parserListObjects(books, BookVO::class.java)

        vos.forEach { book ->
            val withSelfRel = linkTo(BookController::class.java).slash(book.id).withSelfRel()
            book.add(withSelfRel)
        }

        return vos
    }

    fun create(book : BookVO?) : BookVO {
        if(book == null) throw RequiredObjectsIsNullExceptions()
        logger.info("Creating Book with title ${ book.title }!")
        val entity : Book = DozerMapper.parserObject(book, Book::class.java)

        val bookVO : BookVO = DozerMapper.parserObject(bookRepository.save(entity), BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.id).withSelfRel()

        bookVO.add(withSelfRel)

        return bookVO
    }

    fun update(book: BookVO?) : BookVO {
        if(book == null) throw RequiredObjectsIsNullExceptions()
        logger.info("Updating Book with ID ${ book.id }!")
        val entity = bookRepository.findById(book.id).orElseThrow {
            ResourceNotFoundException("No records found for this ID!")
        }

        entity.author = book.author
        entity.title = book.title
        entity.price = book.price
        entity.launchDate = book.launchDate

        val BookVO : BookVO = DozerMapper.parserObject(bookRepository.save(entity), BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(BookVO.id).withSelfRel()

        BookVO.add(withSelfRel)

        return BookVO
    }

    fun delete(id: Long) {
        logger.info("Deleting Book with ID ${ id }!")
        val entity = bookRepository.findById(id).orElseThrow {
            ResourceNotFoundException("No records found for this ID!")
        }
        bookRepository.delete(entity)
    }
}