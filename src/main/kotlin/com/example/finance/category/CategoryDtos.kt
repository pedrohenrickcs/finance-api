package com.example.finance.category

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.Instant

data class CategoryRequest(
    @field:NotBlank @field:Size(max = 100)
    val name: String,

    @field:NotNull
    val type: CategoryType,
)

data class CategoryResponse(
    val id: Long,
    val name: String,
    val type: CategoryType,
    val createdAt: Instant,
)

fun Category.toResponse() = CategoryResponse(
    id = requireNotNull(id),
    name = name,
    type = type,
    createdAt = createdAt,
)
