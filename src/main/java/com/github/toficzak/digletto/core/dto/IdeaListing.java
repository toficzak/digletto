package com.github.toficzak.digletto.core.dto;

import com.github.toficzak.digletto.core.enums.StatusIdea;

import java.time.OffsetDateTime;

public record IdeaListing(Long id, OffsetDateTime created, String name, String username, StatusIdea status,
                          Double avgScore) {

}
