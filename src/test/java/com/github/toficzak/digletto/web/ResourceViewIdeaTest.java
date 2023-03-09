package com.github.toficzak.digletto.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.toficzak.digletto.config.ErrorCodes;
import com.github.toficzak.digletto.core.HelperEntityIdea;
import com.github.toficzak.digletto.core.StatusIdea;
import com.github.toficzak.digletto.core.dto.CreateIdea;
import com.github.toficzak.digletto.core.dto.Idea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static com.github.toficzak.digletto.core.HelperEntityIdea.OTHER_IDEA_NAME;
import static com.github.toficzak.digletto.core.HelperEntityIdea.OTHER_IDEA_USER_ID;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ResourceViewIdeaTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private HelperEntityIdea helperEntityIdea;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void initialize() {
        helperEntityIdea.clearTable();
    }

    @Test
    public void listing_shouldGetIdeas() {
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
                    .andExpect(jsonPath("$.content[0].created").value(new ODTMatcher(idea.created(), 5)))
                    .andExpect(jsonPath("$.content[0].name").value(idea.name()))
                    .andExpect(jsonPath("$.content[0].ownerId").value(idea.ownerId().intValue()))
                    .andExpect(jsonPath("$.content[0].status").value(idea.status().toString()))
                    .andExpect(jsonPath("$.content[1].id").value(otherIdea.id()))
                    .andExpect(jsonPath("$.content[1].created").value(new ODTMatcher(otherIdea.created(), 5)))
                    .andExpect(jsonPath("$.content[1].name").value(otherIdea.name()))
                    .andExpect(jsonPath("$.content[1].ownerId").value(otherIdea.ownerId().intValue()))
                    .andExpect(jsonPath("$.content[1].status").value(otherIdea.status().toString()))
                    .andExpect(jsonPath("$.pageable.pageSize").value(20))
                    .andExpect(jsonPath("$.pageable.pageNumber").value(0))
                    .andExpect(jsonPath("$.totalPages").value(1))
                    .andExpect(jsonPath("$.totalElements").value(1));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void create_shouldSucceedCreatingIdea() {

        CreateIdea dto = new CreateIdea(OTHER_IDEA_NAME, OTHER_IDEA_USER_ID);

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
                    .andExpect(jsonPath("$.created").value(new ODTMatcher(now, 5)))
                    .andExpect(jsonPath("$.ownerId", is(OTHER_IDEA_USER_ID.intValue())))
                    .andExpect(jsonPath("$.status", is(StatusIdea.DRAFT.toString())));
        } catch (Exception e) {
            fail(e);
        }

    }

    @Test
    public void delete_shouldDeleteIdea() {

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
    public void delete_ideaNotFound_throwException() {

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
