package com.qubiz.farm.base

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.Date

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
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
