package com.luke.filmdb.application.model.generic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataModelObject that = (DataModelObject) o;
        return Objects.equals(getCreationDate(), that.getCreationDate()) &&
                Objects.equals(getModificationDate(), that.getModificationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCreationDate(), getModificationDate());
    }
}
