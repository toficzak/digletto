package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.core.dto.Idea;
import com.github.toficzak.digletto.core.enums.StatusIdea;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Hashtable;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class HelperEntityIdea {

    public static final String IDEA_NAME = "Test Name";
    public static final String OTHER_IDEA_NAME = "Test Name";
    @Autowired
    private final HelperEntityUser helperEntityUser;
    @Autowired
    private final HelperEntityRating helperEntityRating;
    public Map<Integer, Idea> ideas = new Hashtable<>();
    public int counter = 0;
    @Autowired
    private RepoIdea repoIdea;

    public void persistTestIdea() {
        EntityUser owner = helperEntityUser.persistTestUser();
        this.persistTestIdea(owner);
    }

    public void persistTestIdea(Long ownerId) {
        EntityUser owner = helperEntityUser.getById(ownerId);
        this.persistTestIdea(owner);
    }

    public void persistTestIdea(EntityUser owner) {
        EntityIdea idea = EntityIdea.builder()
                .name(IDEA_NAME)
                .owner(owner)
                .status(StatusIdea.DRAFT)
                .build();

        repoIdea.saveAndFlush(idea);
        Idea dto = idea.toDto();
        ideas.put(counter++, dto);
    }

    public void persistTestIdeaWithRatings() {
        EntityUser owner = helperEntityUser.persistTestUser();

        EntityIdea idea = EntityIdea.builder()
                .name(IDEA_NAME)
                .owner(owner)
                .status(StatusIdea.DRAFT)
                .build();

        EntityRating rating1 = helperEntityRating.persistTestRating(1, owner);
        EntityRating rating2 = helperEntityRating.persistTestRating(2, owner);

        idea.addRating(rating1);
        idea.addRating(rating2);
        repoIdea.saveAndFlush(idea);

        Idea dto = idea.toDto();
        ideas.put(counter++, dto);
    }


    public void persistAnotherTestIdea() {
        EntityUser owner = helperEntityUser.persistTestUser();
        EntityIdea idea = EntityIdea.builder()
                .name(OTHER_IDEA_NAME)
                .owner(owner)
                .status(StatusIdea.CANCELLED)
                .build();

        repoIdea.saveAndFlush(idea);
        Idea dto = idea.toDto();
        ideas.put(counter++, dto);
    }

    public Idea getLastPersisted() {
        return ideas.get(counter - 1);
    }

    public Idea get(int index) {
        return ideas.get(index);
    }

    public void reinitialize() {
        repoIdea.deleteAll();
        this.ideas.clear();
    }
}
