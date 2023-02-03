package com.qubiz.farm.models.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.qubiz.farm.base.BaseEntity
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
data class Item (
    @OneToOne
    val product: Product? = null,
    var qyt: Int? = 0,
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    val cart: ShoppingCart? = null
) : BaseEntity()
