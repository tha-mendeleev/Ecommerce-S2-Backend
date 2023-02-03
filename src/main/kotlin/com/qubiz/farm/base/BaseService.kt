package com.qubiz.farm.base

interface BaseService<T, K> {
    fun create(req: T): T
    fun update(req: T): T
    fun remove(req: K)
}
