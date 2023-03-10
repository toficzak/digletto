package com.github.toficzak.digletto.web.view;

import com.github.toficzak.digletto.core.dto.Idea;
import com.github.toficzak.digletto.core.enums.StatusIdea;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;

public record ViewIdea(Long id, OffsetDateTime created, String name, ViewUser owner, StatusIdea status,
                       List<ViewRating> ratings) {

    public ViewIdea(Idea idea) {
        this(idea.id(), idea.created(), idea.name(), new ViewUser(idea.owner()), idea.status(), idea.ratings().stream()
                .map(ViewRating::new)
                .sorted(Comparator.comparingInt(ViewRating::value))
                .toList());
    }
}
