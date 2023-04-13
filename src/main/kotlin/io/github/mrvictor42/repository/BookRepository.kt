package io.github.mrvictor42.repository

import io.github.mrvictor42.model.Book
import io.github.mrvictor42.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long?> {

}