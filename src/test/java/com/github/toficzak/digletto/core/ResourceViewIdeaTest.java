package com.github.toficzak.digletto.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.OffsetDateTime;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ResourceViewIdeaTest {

    public static final String IDEA_NAME = "Test Name";
    private static final Long IDEA_USER_ID = 3L;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RepoIdea repoIdea;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void get_listing() {

        EntityIdea idea = persistTestIdea();

        try {
            mockMvc
                    .perform(get("/ideas"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").isNotEmpty())
                    .andExpect(jsonPath("$.name", is(IDEA_NAME)))
                    .andExpect(jsonPath("$.created").value(new ODTMatcher(idea.created, 1)))
                    .andExpect(jsonPath("$.ownerId", is(3)))
                    .andExpect(jsonPath("$.status", is(StatusIdea.DRAFT.toString())));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void create_shouldSucceedCreatingIdea() {

        CreateViewDto dto = new CreateViewDto(IDEA_NAME, IDEA_USER_ID);

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
                    .andExpect(jsonPath("$.name", is(IDEA_NAME)))
                    .andExpect(jsonPath("$.created").value(new ODTMatcher(now, 5)))
                    .andExpect(jsonPath("$.ownerId", is(IDEA_USER_ID.intValue())))
                    .andExpect(jsonPath("$.status", is(StatusIdea.DRAFT.toString())));
        } catch (Exception e) {
            fail(e);
        }

    }

    @Test
    public void delete_shouldDeleteIdea() {

        EntityIdea idea = persistTestIdea();

        try {
            mockMvc
                    .perform(delete("/ideas" + "/" + idea.id))
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

    private EntityIdea persistTestIdea() {
        EntityIdea idea = EntityIdea.builder()
                .name(IDEA_NAME)
                .ownerId(IDEA_USER_ID)
                .status(StatusIdea.DRAFT)
                .build();

        repoIdea.save(idea);
        return idea;
    }

    private String parseJsonToString(CreateViewDto dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    private ResultActions performHttpCallSafely(MockMvc mockMvc, MockHttpServletRequestBuilder request) {
        try {
            return mockMvc.perform(request);
        } catch (Exception e) {
            fail(e);
        }
        return null;
    }

}
