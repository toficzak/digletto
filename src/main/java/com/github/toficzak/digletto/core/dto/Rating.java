package com.github.toficzak.digletto.core.dto;

import java.time.OffsetDateTime;

public record Rating(Long id, OffsetDateTime created, Integer value) {

}
