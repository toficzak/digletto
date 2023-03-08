package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.core.dto.Idea;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceIdeaView {

    private final RepoIdea repoIdea;

    public Idea get() {
        return repoIdea.findAll().get(0).toDto();
    }


}
