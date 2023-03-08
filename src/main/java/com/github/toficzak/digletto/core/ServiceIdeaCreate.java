package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.core.dto.CreateIdea;
import com.github.toficzak.digletto.core.dto.Idea;
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

    public Idea create(CreateIdea dto) {
        EntityIdea idea = EntityIdea.from(dto);
        repoIdea.save(idea);
        log.info("Created {}.", idea);
        return idea.toDto();
    }
}
