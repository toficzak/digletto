package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.core.dto.Idea;
import com.github.toficzak.digletto.core.enums.StatusIdea;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HelperEntityIdea {

    public static final String IDEA_NAME = "Test Name";
    public static final String OTHER_IDEA_NAME = "Test Name";
    @Autowired
    private final HelperEntityUser helperEntityUser;
    @Autowired
    private final HelperEntityRating helperEntityRating;
    public List<Idea> ideas = new ArrayList<>();
    @Autowired
    private RepoIdea repoIdea;

    public void persistTestIdea() {
        EntityUser owner = helperEntityUser.persistTestUser();
        this.persistTestIdea(owner);
    }

    public void persistTestIdea(StatusIdea status) {
        EntityUser owner = helperEntityUser.persistTestUser();
        this.persistTestIdeaWithRatings(owner, status);
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
        ideas.add(dto);
    }

    public void persistTestIdeaWithRatings() {
        EntityUser owner = helperEntityUser.persistTestUser();
        this.persistTestIdeaWithRatings(owner, StatusIdea.DRAFT);
    }

    public void persistTestIdeaWithRatings(EntityUser owner, StatusIdea status) {
        EntityIdea idea = EntityIdea.builder()
                .name(IDEA_NAME)
                .owner(owner)
                .status(status)
                .build();

        EntityRating rating1 = helperEntityRating.persistTestRating(1, owner);
        EntityRating rating2 = helperEntityRating.persistTestRating(2, owner);

        idea.addRating(rating1);
        idea.addRating(rating2);
        repoIdea.saveAndFlush(idea);

        Idea dto = idea.toDto();
        ideas.add(dto);
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
        ideas.add(dto);
    }

    public Idea getLastPersisted() {
        return ideas.get(ideas.size() - 1);
    }

    public Idea get(int index) {
        return ideas.get(index);
    }

    public void reinitialize() {
        repoIdea.deleteAll();
        this.ideas.clear();
    }
}
