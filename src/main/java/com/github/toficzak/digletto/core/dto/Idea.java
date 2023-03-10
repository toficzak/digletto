package com.github.toficzak.digletto.core.dto;

import com.github.toficzak.digletto.core.StatusIdea;

import java.time.OffsetDateTime;
import java.util.Set;

public record Idea(Long id, OffsetDateTime created, String name, User owner, StatusIdea status, Set<Rating> ratings) {

}
