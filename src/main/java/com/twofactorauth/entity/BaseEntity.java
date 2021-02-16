package com.twofactorauth.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

abstract class BaseEntity implements Serializable {

    @Column(updatable = false)
    LocalDateTime createdAt;

    @Column(updatable = true)
    LocalDateTime updatedAt;

}
