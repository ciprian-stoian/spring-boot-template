package com.backend.springboottemplate.repository.entity;

import com.backend.springboottemplate.repository.listener.IdentifiableEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
@EntityListeners(IdentifiableEntityListener.class)
public abstract class TrackableEntity extends IdentifiableEntity implements Serializable {
    @Column(name = "created_on", nullable = false)
    private LocalDate createdOn;

    @Column(name = "updated_on")
    private LocalDate updatedOn;

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(final LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(final LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }
}
