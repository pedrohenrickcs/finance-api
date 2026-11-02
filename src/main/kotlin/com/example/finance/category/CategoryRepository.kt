package com.example.finance.category

import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {
    fun findAllByType(type: CategoryType): List<Category>
}
