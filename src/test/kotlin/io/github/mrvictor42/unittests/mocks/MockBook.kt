package io.github.mrvictor42.unittests.mocks

import io.github.mrvictor42.data.vo.v1.BookVO
import java.util.ArrayList
import io.github.mrvictor42.data.vo.v1.PersonVO
import io.github.mrvictor42.model.Book
import io.github.mrvictor42.model.Person

class MockBook {
    fun mockEntity(): Book {
        return mockEntity(0)
    }

    fun mockVO(): BookVO {
        return mockVO(0)
    }

    fun mockEntityList(): ArrayList<Book> {
        val books: ArrayList<Book> = ArrayList<Book>()
        for (i in 0..13) {
            books.add(mockEntity(i))
        }
        return books
    }

    fun mockVOList(): ArrayList<BookVO> {
        val books: ArrayList<BookVO> = ArrayList()
        for (i in 0..13) {
            books.add(mockVO(i))
        }
        return books
    }

    fun mockEntity(number: Int): Book {
        val book = Book()

        book.id = number.toLong()
        book.author = "Some Author$number"
        book.price = 25.0
        book.title = "Some Title$number"

        return book
    }

    fun mockVO(number: Int): BookVO {
        val book = BookVO()

        book.id = number.toLong()
        book.author = "Some Author$number"
        book.price = 25.0
        book.title = "Some Title$number"

        return book
    }
}