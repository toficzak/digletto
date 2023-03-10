package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.core.dto.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HelperEntityRating {
    @Autowired
    private final RepoRating repoRating;
    public List<Rating> ratings = new ArrayList<>();

    public EntityRating persistTestRating(Integer value, EntityUser user) {
        EntityRating rating = EntityRating.builder()
                .score(value)
                .user(user)
                .build();

        repoRating.save(rating);
        Rating dto = rating.toDto();
        ratings.add(dto);
        return rating;
    }

    public void reinitialize() {
        repoRating.deleteAll();
        this.ratings.clear();
    }
}
