package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.core.dto.CreateIdea;
import com.github.toficzak.digletto.core.dto.Idea;
import com.github.toficzak.digletto.core.exception.IdeaNameAlreadyExistsForUserException;
import com.github.toficzak.digletto.core.exception.IdeaNotPersistedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceIdeaCreate {
    private final RepoIdea repoIdea;
    private final HelperUser helperUser;

    public Idea create(CreateIdea dto) {
        EntityUser owner = helperUser.findByIdOrThrow(dto.ownerId());
        boolean ideaAlreadyExists = repoIdea.existsByNameAndOwner(dto.name(), owner);
        if (ideaAlreadyExists) {
            throw new IdeaNameAlreadyExistsForUserException();
        }
        EntityIdea idea = EntityIdea.from(dto, owner);
        this.persist(idea);
        log.info("Created {}.", idea);
        return idea.toDto();
    }

    private void persist(EntityIdea idea) {
        try {
            repoIdea.save(idea);
        } catch (Exception e) {
            // general exception, I think parsing errors might be senseless, I will use fail-fast approach
            // to check data myself before  persisting
            log.error(idea.producePersistenceErrorMessage());
            throw new IdeaNotPersistedException();
        }
    }
}
