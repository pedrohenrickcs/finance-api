package com.example.finance.category

import com.example.finance.common.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryService(private val repository: CategoryRepository) {

    @Transactional(readOnly = true)
    fun list(type: CategoryType?): List<CategoryResponse> =
        (if (type == null) repository.findAll() else repository.findAllByType(type)).map { it.toResponse() }

    @Transactional(readOnly = true)
    fun get(id: Long): CategoryResponse = find(id).toResponse()

    @Transactional
    fun create(request: CategoryRequest): CategoryResponse =
        repository.saveAndFlush(Category(request.name.trim(), request.type)).toResponse()

    @Transactional
    fun update(id: Long, request: CategoryRequest): CategoryResponse {
        val category = find(id).apply {
            name = request.name.trim()
            type = request.type
        }
        return repository.saveAndFlush(category).toResponse()
    }

    @Transactional
    fun delete(id: Long) {
        repository.delete(find(id))
        repository.flush()
    }

    private fun find(id: Long): Category =
        repository.findById(id).orElseThrow { NotFoundException("Categoria $id não encontrada") }
}
