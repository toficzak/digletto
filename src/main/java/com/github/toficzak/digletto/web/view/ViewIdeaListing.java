package com.github.toficzak.digletto.web.view;

import com.github.toficzak.digletto.core.dto.IdeaListing;
import com.github.toficzak.digletto.core.enums.StatusIdea;

import java.time.OffsetDateTime;

public record ViewIdeaListing(Long id, OffsetDateTime created, String name, StatusIdea status, String username,
                              Double avgScore) {
    public ViewIdeaListing(IdeaListing dto) {
        this(dto.id(), dto.created(), dto.name(), dto.status(), dto.username(), dto.avgScore());
    }
}
