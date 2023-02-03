package com.qubiz.farm.models.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.qubiz.farm.base.BaseEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne

@Entity
data class ProductImage(
    var filename: String? = null,
    @JsonIgnore
    @ManyToOne
    var product: Product? = null
): BaseEntity()
