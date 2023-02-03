package com.qubiz.farm.models.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.validator.constraints.Length
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(indexes = [Index(name = "idx_email", columnList = "email"), Index(name = "idx_name", columnList = "userName")])
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Length(min = 3, max = 32)
    var username: String? = null,
    @Length(max = 128)
    var email: String? = null,
    var passwd: String? = null,
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], mappedBy = "users")
    var roles: MutableList<Role>? = null,
    @JsonIgnore
    @CreatedDate
    @Temporal(TemporalType.DATE)
    var created: Date? = null,
    @JsonIgnore
    @LastModifiedDate
    @Temporal(TemporalType.DATE)
    var lastModified: Date? = null,
    @JsonIgnore
    @Version
    var version: Int? = 0
)
