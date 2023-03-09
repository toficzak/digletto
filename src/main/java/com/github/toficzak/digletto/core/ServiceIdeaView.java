package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.core.dto.Idea;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceIdeaView {

    private final RepoIdea repoIdea;

    public Page<Idea> listing(Pageable pageable) {
        return repoIdea.findAll(pageable).map(EntityIdea::toDto);
    }


}
