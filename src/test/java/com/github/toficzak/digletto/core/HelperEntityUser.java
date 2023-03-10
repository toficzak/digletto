package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.core.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Hashtable;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HelperEntityUser {

    private static final String USER_NAME = "test name";
    private static final String USER_EMAIL = "test@email.com";
    @Autowired
    private final RepoUser repoUser;

    public Map<Integer, User> users = new Hashtable<>();
    public int counter = 0;

    public EntityUser persistTestUser() {
        EntityUser user = EntityUser.builder()
                .email(USER_EMAIL)
                .name(USER_NAME)
                .build();

        repoUser.saveAndFlush(user);
        User dto = user.toDto();
        users.put(counter++, dto);
        return user;
    }

    public User getLastPersisted() {
        return users.get(counter - 1);
    }

    public void reinitialize() {
        repoUser.deleteAll();
        this.users.clear();
    }

    EntityUser getById(Long id) {
        return repoUser.findById(id).orElseThrow();
    }
}
