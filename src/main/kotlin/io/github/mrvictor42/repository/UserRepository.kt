package io.github.mrvictor42.repository

import io.github.mrvictor42.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User?, Long?> {
    @Query("SELECT user FROM User user WHERE user.username = :userName")
    fun findByUsername(@Param("userName") username : String?) : User?
}