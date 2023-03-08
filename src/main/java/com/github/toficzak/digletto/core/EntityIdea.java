package com.github.toficzak.digletto.core;

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

    static EntityIdea fromDto(CreateViewDto dto) {
        return EntityIdea.builder()
                .name(dto.name())
                .ownerId(dto.userId())
                .status(StatusIdea.DRAFT)
                .build();
    }

    // TODO: should entity change to view/dto? Or Dto should have ability to be produced from entity?
    //  In latter case, view can be separated from core and view depends on core - this sounds more right.
    ViewIdea toView() {
        return new ViewIdea(super.id, super.created, this.name, this.ownerId, this.status);
    }

    @Override
    public String toString() {
        return "EntityIdea{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
