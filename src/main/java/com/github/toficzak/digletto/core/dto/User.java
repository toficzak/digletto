package com.github.toficzak.digletto.core.dto;

import java.time.OffsetDateTime;

public record User(Long id, OffsetDateTime created, String email, String name) {
}
