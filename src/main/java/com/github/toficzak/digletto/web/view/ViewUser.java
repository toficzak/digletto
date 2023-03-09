package com.github.toficzak.digletto.web.view;

import com.github.toficzak.digletto.core.dto.User;

import java.time.OffsetDateTime;

public record ViewUser(Long id, OffsetDateTime created, String email, String name) {

    public ViewUser(User user) {
        this(user.id(), user.created(), user.email(), user.name());
    }
}
