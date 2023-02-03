package com.qubiz.farm.models.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.qubiz.farm.base.BaseEntity
import javax.persistence.*

@Entity
data class Store(
    var name: String? = null,
    var account: String? = null,
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var products: MutableList<Product>? = null,
    var profileImage: String? = null,
    var coverImage: String? = null,
    @JsonIgnore
    @OneToOne
    var owner: User? = null,
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val orderDetails: MutableList<OrderDetail>? = null
): BaseEntity()
