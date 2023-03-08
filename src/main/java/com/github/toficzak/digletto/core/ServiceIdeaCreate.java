package com.github.toficzak.digletto.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceIdeaCreate {
    // TODO: think about those names, perhaps CreatorIdea/IdeaCreator? It lacks common name component like Service,
    //  but clearly indicates responsibility according to SRP
    private final RepoIdea repoIdea;

    public ViewIdea create(CreateViewDto dto) {
        EntityIdea idea = EntityIdea.fromDto(dto);
        repoIdea.save(idea);
        log.info("Created {}.", idea);
        return idea.toView();
    }
}
