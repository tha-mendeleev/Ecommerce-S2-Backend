package com.qubiz.farm.base

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface BaseRepo<T, K>: JpaRepository<T, K>, JpaSpecificationExecutor<T>
