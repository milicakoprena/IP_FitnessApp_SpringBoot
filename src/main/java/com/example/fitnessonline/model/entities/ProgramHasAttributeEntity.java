package com.example.fitnessonline.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
@Table(name = "program_has_attribute")
public class ProgramHasAttributeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", referencedColumnName = "id", nullable = false)
    private ProgramEntity program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id", referencedColumnName = "id", nullable = false)
    private AttributeEntity attribute;
    @Basic
    @Column(name = "value", nullable = false, length = 255)
    private String value;
}
