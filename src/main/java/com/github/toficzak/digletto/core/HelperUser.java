package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.core.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class HelperUser {

    private final RepoUser repoUser;

    EntityUser findByIdOrThrow(Long id) {
        var optUser = repoUser.findById(id);
        if (optUser.isPresent()) {
            return optUser.get();
        }
        log.error("User{id={}} not found.", id);
        throw new UserNotFoundException();
    }
}
