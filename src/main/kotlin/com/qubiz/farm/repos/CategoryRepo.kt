package com.qubiz.farm.repos

import com.qubiz.farm.base.BaseRepo
import com.qubiz.farm.models.domain.Category
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepo: BaseRepo<Category, Long>
