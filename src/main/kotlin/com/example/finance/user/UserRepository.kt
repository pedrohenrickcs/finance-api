package com.example.finance.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<User, Long> {
    @Query("select u from User u where lower(u.email) = lower(:email)")
    fun findByEmail(@Param("email") email: String): User?

    @Query("select case when count(u) > 0 then true else false end from User u where lower(u.email) = lower(:email)")
    fun existsByEmail(@Param("email") email: String): Boolean
}
