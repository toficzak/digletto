package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.EntityBase;
import com.github.toficzak.digletto.core.dto.CreateIdea;
import com.github.toficzak.digletto.core.dto.Idea;
import com.github.toficzak.digletto.core.dto.Rating;
import com.github.toficzak.digletto.core.enums.StatusIdea;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PACKAGE)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "owner_id"}))
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
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<EntityRating> ratings = new HashSet<>();

    static EntityIdea from(CreateIdea dto, EntityUser user) {
        return EntityIdea.builder()
                .name(dto.name())
                .owner(user)
                .status(StatusIdea.DRAFT)
                .build();
    }

    void addRating(EntityRating rating) {
        if (this.ratings == null) {
            ratings = new HashSet<>();
        }
        this.ratings.add(rating);
    }

    Idea toDto() {
        Set<Rating> ratingDtos = new HashSet<>();
        if (this.ratings != null) {
            ratingDtos = this.ratings.stream().map(EntityRating::toDto).collect(Collectors.toSet());
        }
        return new Idea(super.id, super.created, this.name, this.owner.toDto(), this.status, ratingDtos);
    }

    @Override
    public String toString() {
        return "EntityIdea{" +
                "id=" + id + '\'' +
                "name='" + name + '\'' +
                "status='" + status + '\'' +
                '}';
    }

    String producePersistenceErrorMessage() {
        return String.format("Error while persisting idea{name=%s, owner=%s}", this.name, this.owner.toString());
    }

    boolean canBeDeleted() {
        return StatusIdea.DRAFT == this.status;
    }
}
