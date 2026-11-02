package com.example.finance.account

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.Instant

data class AccountRequest(
    @field:NotBlank @field:Size(max = 100)
    val name: String,

    @field:NotNull
    val type: AccountType,

    val initialBalance: BigDecimal = BigDecimal.ZERO,
)

data class AccountResponse(
    val id: Long,
    val name: String,
    val type: AccountType,
    val initialBalance: BigDecimal,
    val createdAt: Instant,
)

fun Account.toResponse() = AccountResponse(
    id = requireNotNull(id),
    name = name,
    type = type,
    initialBalance = initialBalance,
    createdAt = createdAt,
)
