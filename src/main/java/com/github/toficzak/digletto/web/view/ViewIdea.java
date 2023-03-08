package com.github.toficzak.digletto.web.view;

import com.github.toficzak.digletto.core.StatusIdea;
import com.github.toficzak.digletto.core.dto.Idea;

import java.time.OffsetDateTime;

public record ViewIdea(Long id, OffsetDateTime created, String name, Long ownerId, StatusIdea status) {

    public ViewIdea(Idea idea) {
        this(idea.id(), idea.created(), idea.name(), idea.ownerId(), idea.status());
    }
}
