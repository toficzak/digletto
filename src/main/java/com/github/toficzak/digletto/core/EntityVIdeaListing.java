package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.EntityBase;
import com.github.toficzak.digletto.core.dto.IdeaListing;
import com.github.toficzak.digletto.core.enums.StatusIdea;
import jakarta.persistence.*;
import org.springframework.data.annotation.Immutable;

@Entity
@Immutable
@Table(name = "`v_idea_listing`")
class EntityVIdeaListing extends EntityBase {

    private String name;
    @Enumerated(EnumType.STRING)
    private StatusIdea status;
    private String username;
    @Column(name = "avg_score", columnDefinition = "FLOAT(5,2)")
    private Float avgScore;

    IdeaListing toDto() {
        Double doubleAvgScore = null;
        if (avgScore != null) {
            doubleAvgScore = Double.valueOf(this.avgScore);
        }
        return new IdeaListing(super.id, super.created, this.name, this.username, this.status, doubleAvgScore);
    }

}
