package com.github.toficzak.digletto.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceIdeaView {

    private final RepoIdea repoIdea;

    public ViewIdea get() {
        return repoIdea.findAll().get(0).toView();
    }


}
