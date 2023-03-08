package com.github.toficzak.digletto.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceIdeaDelete {

    private final RepoIdea repoIdea;

    void delete(DtoDeleteIdea dto) {
        EntityIdea idea = repoIdea.findById(dto.id()).orElseThrow(IdeaNotFoundException::new);
        repoIdea.delete(idea);
        log.info("Deleted {}", idea);
    }
}
