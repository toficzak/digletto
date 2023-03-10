package com.github.toficzak.digletto.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.toficzak.digletto.config.ErrorCodes;
import com.github.toficzak.digletto.core.HelperEntityIdea;
import com.github.toficzak.digletto.core.HelperEntityRating;
import com.github.toficzak.digletto.core.HelperEntityUser;
import com.github.toficzak.digletto.core.dto.CreateIdea;
import com.github.toficzak.digletto.core.dto.Idea;
import com.github.toficzak.digletto.core.dto.User;
import com.github.toficzak.digletto.core.enums.StatusIdea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static com.github.toficzak.digletto.core.HelperEntityIdea.OTHER_IDEA_NAME;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ResourceIdeaIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private HelperEntityIdea helperEntityIdea;
    @Autowired
    private HelperEntityUser helperEntityUser;
    @Autowired
    private HelperEntityRating helperEntityRating;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void initialize() {
        helperEntityIdea.reinitialize();
        helperEntityUser.reinitialize();
        helperEntityRating.reinitialize();
    }

    @Test
    void get_shouldGetIdea() {
        helperEntityIdea.persistTestIdeaWithRatings();
        Idea idea = helperEntityIdea.getLastPersisted();

        try {
            mockMvc
                    .perform(get("/ideas/" + idea.id()))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.id").value(idea.id()))
                    .andExpect(jsonPath("$.created").value(new ODTMatcher(idea.created(), 1)))
                    .andExpect(jsonPath("$.name").value(idea.name()))
                    .andExpect(jsonPath("$.owner.email").value(idea.owner().email()))
                    .andExpect(jsonPath("$.owner.name").value(idea.owner().name()))
                    .andExpect(jsonPath("$.status").value(idea.status().toString()))
                    .andExpect(jsonPath("$.ratings").isArray())
                    .andExpect(jsonPath("$.ratings[0].value").value(1))
                    .andExpect(jsonPath("$.ratings[1].value").value(2));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void get_ideaNotFound_throwException() {
        try {
            mockMvc
                    .perform(get("/ideas/" + 99))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("errorCode").value(ErrorCodes.IDEA_NOT_FOUND));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void listing_shouldGetIdeas() {
        helperEntityIdea.persistAnotherTestIdea();
        helperEntityIdea.persistTestIdea();
        Idea otherIdea = helperEntityIdea.getLastPersisted();
        Idea idea = helperEntityIdea.get(0);

        try {
            mockMvc
                    .perform(get("/ideas"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content", hasSize(2)))
                    .andExpect(jsonPath("$.content[0].id").value(idea.id()))
                    .andExpect(jsonPath("$.content[0].created").value(new ODTMatcher(idea.created(), 1)))
                    .andExpect(jsonPath("$.content[0].name").value(idea.name()))
                    .andExpect(jsonPath("$.content[0].owner.email").value(idea.owner().email()))
                    .andExpect(jsonPath("$.content[0].owner.name").value(idea.owner().name()))
                    .andExpect(jsonPath("$.content[0].status").value(idea.status().toString()))
                    .andExpect(jsonPath("$.content[1].id").value(otherIdea.id()))
                    .andExpect(jsonPath("$.content[1].created").value(new ODTMatcher(otherIdea.created(), 1)))
                    .andExpect(jsonPath("$.content[1].name").value(otherIdea.name()))
                    .andExpect(jsonPath("$.content[1].owner.email").value(otherIdea.owner().email()))
                    .andExpect(jsonPath("$.content[1].owner.name").value(otherIdea.owner().name()))
                    .andExpect(jsonPath("$.content[1].status").value(otherIdea.status().toString()))
                    .andExpect(jsonPath("$.pageable.pageSize").value(20))
                    .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                    .andExpect(jsonPath("$.totalPages").value(1))
                    .andExpect(jsonPath("$.totalElements").value(2));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void create_shouldSucceedCreatingIdea() {
        helperEntityUser.persistTestUser();
        User user = helperEntityUser.getLastPersisted();
        CreateIdea dto = new CreateIdea(OTHER_IDEA_NAME, user.id());
        String jsonContent = parseJsonToString(dto);
        OffsetDateTime now = OffsetDateTime.now();

        try {
            mockMvc
                    .perform(post("/ideas")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonContent))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").isNotEmpty())
                    .andExpect(jsonPath("$.name", is(OTHER_IDEA_NAME)))
                    .andExpect(jsonPath("$.created").value(new ODTMatcher(now, 1)))
                    .andExpect(jsonPath("$.owner.email").value(user.email()))
                    .andExpect(jsonPath("$.owner.name").value(user.name()))
                    .andExpect(jsonPath("$.status", is(StatusIdea.DRAFT.toString())));
        } catch (Exception e) {
            fail(e);
        }

    }

    @Test
    void create_nameAlreadyExistsForUser_shouldThrow409() {
        helperEntityUser.persistTestUser();
        User user = helperEntityUser.getLastPersisted();

        helperEntityIdea.persistTestIdea(user.id());
        Idea idea = helperEntityIdea.getLastPersisted();

        CreateIdea dto = new CreateIdea(idea.name(), user.id());
        String jsonContent = parseJsonToString(dto);

        try {
            mockMvc
                    .perform(post("/ideas")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonContent))
                    .andDo(print())
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("errorCode").value(ErrorCodes.IDEA_NAME_ALREADY_USED_FOR_THIS_USER));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void delete_shouldDeleteIdea() {

        helperEntityIdea.persistTestIdea();
        Idea idea = helperEntityIdea.getLastPersisted();
        try {
            mockMvc
                    .perform(delete("/ideas" + "/" + idea.id()))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void delete_ideaNotFound_throwException() {

        try {
            mockMvc
                    .perform(delete("/ideas" + "/99"))
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("errorCode").value(ErrorCodes.IDEA_NOT_FOUND));
        } catch (Exception e) {
            fail(e);
        }
    }

    private String parseJsonToString(CreateIdea dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
