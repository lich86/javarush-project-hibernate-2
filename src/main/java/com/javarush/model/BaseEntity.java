package com.javarush.model;

import jakarta.persistence.Column;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseEntity {
    @Column(name = "last_update", columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    LocalDateTime lastUpdate;
}
