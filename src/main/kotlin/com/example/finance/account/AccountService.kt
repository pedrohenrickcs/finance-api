package com.example.finance.account

import com.example.finance.common.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(private val repository: AccountRepository) {

    @Transactional(readOnly = true)
    fun list(): List<AccountResponse> = repository.findAll().map { it.toResponse() }

    @Transactional(readOnly = true)
    fun get(id: Long): AccountResponse = find(id).toResponse()

    @Transactional
    fun create(request: AccountRequest): AccountResponse {
        val account = Account(request.name.trim(), request.type, request.initialBalance)
        return repository.saveAndFlush(account).toResponse()
    }

    @Transactional
    fun update(id: Long, request: AccountRequest): AccountResponse {
        val account = find(id).apply {
            name = request.name.trim()
            type = request.type
            initialBalance = request.initialBalance
        }
        return repository.saveAndFlush(account).toResponse()
    }

    @Transactional
    fun delete(id: Long) {
        repository.delete(find(id))
        repository.flush()
    }

    private fun find(id: Long): Account =
        repository.findById(id).orElseThrow { NotFoundException("Conta $id não encontrada") }
}
