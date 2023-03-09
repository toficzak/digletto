package com.github.toficzak.digletto.core.dto;

import com.github.toficzak.digletto.core.StatusIdea;

import java.time.OffsetDateTime;

public record Idea(Long id, OffsetDateTime created, String name, User owner, StatusIdea status) {

}
