package com.example.demo.application.model.generic;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
@Data
public abstract class DataModelObject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "creation_date")
    public LocalDate creationDate;
    @Column(name = "modification_date")
    public LocalDate modificationDate;

    @PrePersist
    public void onCreate() {
        this.creationDate = this.modificationDate = LocalDate.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.modificationDate = LocalDate.now();
    }
}
