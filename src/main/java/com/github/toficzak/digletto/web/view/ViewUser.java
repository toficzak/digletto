package com.github.toficzak.digletto.web.view;

import com.github.toficzak.digletto.core.dto.User;

public record ViewUser(String email, String name) {

    public ViewUser(User user) {
        this(user.email(), user.name());
    }
}
