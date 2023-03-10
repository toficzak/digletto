package com.github.toficzak.digletto.web.view;

import com.github.toficzak.digletto.core.dto.Rating;

public record ViewRating(Integer value) {

    public ViewRating(Rating rating) {
        this(rating.value());
    }
}
