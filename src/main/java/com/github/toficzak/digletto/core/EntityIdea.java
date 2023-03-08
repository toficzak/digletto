package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.EntityBase;
import com.github.toficzak.digletto.core.dto.CreateIdea;
import com.github.toficzak.digletto.core.dto.Idea;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
class EntityIdea extends EntityBase {

    @NotNull
    private String name;
    @NotNull
    private Long ownerId;
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusIdea status;

    static EntityIdea from(CreateIdea dto) {
        return EntityIdea.builder()
                .name(dto.name())
                .ownerId(dto.userId())
                .status(StatusIdea.DRAFT)
                .build();
    }

    Idea toDto() {
        return new Idea(super.id, super.created, this.name, this.ownerId, this.status);
    }

    @Override
    public String toString() {
        return "EntityIdea{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
