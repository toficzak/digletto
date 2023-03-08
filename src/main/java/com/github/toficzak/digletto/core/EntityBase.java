package com.github.toficzak.digletto.core;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

@MappedSuperclass
abstract class EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @NotNull
    protected OffsetDateTime created = OffsetDateTime.now();
}
