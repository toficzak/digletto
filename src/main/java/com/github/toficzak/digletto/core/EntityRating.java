package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.EntityBase;
import com.github.toficzak.digletto.core.dto.Rating;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PACKAGE)
class EntityRating extends EntityBase {

    @NotNull
    @Min(0)
    @Max(10)
    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private EntityUser user;

    public Rating toDto() {
        return new Rating(this.id, this.created, this.score);
    }
}
