package com.github.toficzak.digletto.core;

import java.time.OffsetDateTime;

public record ViewIdea(Long id, OffsetDateTime created, String name, Long ownerId, StatusIdea status) {


}
