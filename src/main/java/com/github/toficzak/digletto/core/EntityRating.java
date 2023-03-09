package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntityRating extends EntityBase {

    @NotNull
    @Min(0)
    @Max(10)
    private Integer value;

    @OneToOne
    private EntityUser user;

    @OneToOne
    private EntityIdea idea;
}
