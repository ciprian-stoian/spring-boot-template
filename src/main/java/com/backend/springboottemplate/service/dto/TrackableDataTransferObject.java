package com.backend.springboottemplate.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDate;

public abstract class TrackableDataTransferObject extends DataTransferObject implements Serializable {
    @Null(message = "Field createdOn must be null!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdOn;

    @Null(message = "Field updatedOn must be null!")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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
