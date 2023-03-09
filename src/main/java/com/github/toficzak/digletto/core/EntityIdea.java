package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.EntityBase;
import com.github.toficzak.digletto.core.dto.CreateIdea;
import com.github.toficzak.digletto.core.dto.Idea;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
class EntityIdea extends EntityBase {

    @NotNull
    private String name;
    @OneToOne
    private EntityUser owner;
    @OneToMany
    private Set<EntityUser> users;
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusIdea status;


    static EntityIdea from(CreateIdea dto, EntityUser user) {
        return EntityIdea.builder()
                .name(dto.name())
                .owner(user)
                .status(StatusIdea.DRAFT)
                .build();
    }

    Idea toDto() {
        return new Idea(super.id, super.created, this.name, this.owner.toDto(), this.status);
    }

    @Override
    public String toString() {
        return "EntityIdea{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
