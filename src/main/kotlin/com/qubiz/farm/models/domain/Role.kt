package com.qubiz.farm.models.domain

import com.qubiz.farm.base.BaseEntity
import javax.persistence.*

@Entity
data class Role(
    var name: String? = null,
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id", foreignKey = ForeignKey(name = "fk_role_id"))],
        inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = ForeignKey(name = "fk_user_id"))]
    )
    var users: MutableList<User>? = null
) : BaseEntity()
