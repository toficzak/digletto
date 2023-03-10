package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.core.dto.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Hashtable;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HelperEntityRating {
    @Autowired
    private final RepoRating repoRating;
    public Map<Integer, Rating> ratings = new Hashtable<>();
    public int counter = 0;

    public EntityRating persistTestRating(Integer value, EntityUser user) {
        EntityRating rating = EntityRating.builder()
                .score(value)
                .user(user)
                .build();

        repoRating.save(rating);
        Rating dto = rating.toDto();
        ratings.put(counter++, dto);
        return rating;
    }

    public void clearTable() {
        repoRating.deleteAll();
    }
}
