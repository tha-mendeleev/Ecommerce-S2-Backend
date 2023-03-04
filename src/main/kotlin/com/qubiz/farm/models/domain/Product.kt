package com.qubiz.farm.models.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.qubiz.farm.base.BaseEntity
import javax.persistence.*

@Entity
data class Product(

    var name: String? = null,
    var qty: Int? = 0,
    var sold: Int? = 0,
    var price: Double? = 0.0,
    var description: String? = null,

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var images: MutableList<ProductImage>? = null,

    @ManyToOne
    var store: Store? = null,
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "productCategory",
        joinColumns = [JoinColumn(name = "productId", foreignKey = ForeignKey(name = "fk_pro_id"))],
        inverseJoinColumns = [JoinColumn(name = "categoryId", foreignKey = ForeignKey(name = "fk_cat_id"))]
    )
    var categories: MutableList<Category>? = null
): BaseEntity()
