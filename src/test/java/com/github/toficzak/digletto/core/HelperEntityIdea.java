package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.core.dto.Idea;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Hashtable;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HelperEntityIdea {

    public static final String IDEA_NAME = "Test Name";
    public static final Long IDEA_USER_ID = 3L;
    public Map<Integer, Idea> ideas = new Hashtable<>();
    public int counter = 0;
    @Autowired
    private RepoIdea repoIdea;

    public void persistTestIdea() {
        EntityIdea idea = EntityIdea.builder()
                .name(IDEA_NAME)
                .ownerId(IDEA_USER_ID)
                .status(StatusIdea.DRAFT)
                .build();

        repoIdea.save(idea);
        Idea dto = idea.toDto();
        ideas.put(counter++, dto);
    }

    public Idea getLastPersisted() {
        return ideas.get(counter - 1);
    }
}
