package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.core.dto.CreateIdea;
import com.github.toficzak.digletto.core.exception.IdeaNameAlreadyExistsForUserException;
import com.github.toficzak.digletto.core.exception.IdeaNotPersistedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceIdeaCreateTest {

    private static final CreateIdea DTO = new CreateIdea("name", 3L);

    @Mock
    private HelperUser helperUser;
    @Mock
    private RepoIdea repoIdea;
    @Mock
    private EntityUser user;

    @InjectMocks
    private ServiceIdeaCreate serviceIdeaCreate;

    @Test
    void create_errorWhilePersisting_shouldThrowException() {

        when(helperUser.findByIdOrThrow(any())).thenReturn(user);
        when(repoIdea.existsByNameAndOwner(any(), any())).thenReturn(false);
        when(repoIdea.save(any())).thenThrow(DataIntegrityViolationException.class);

        // when
        assertThrows(IdeaNotPersistedException.class, () -> serviceIdeaCreate.create(DTO));
    }

    @Test
    void create_ideaAlreadyExists_shouldThrowException() {

        when(helperUser.findByIdOrThrow(any())).thenReturn(user);
        when(repoIdea.existsByNameAndOwner(any(), any())).thenReturn(true);

        // when
        assertThrows(IdeaNameAlreadyExistsForUserException.class, () -> serviceIdeaCreate.create(DTO));
    }

    @Test
    void create_shouldSucceed() {

        when(helperUser.findByIdOrThrow(any())).thenReturn(user);
        when(repoIdea.existsByNameAndOwner(any(), any())).thenReturn(false);
        when(repoIdea.save(any())).thenReturn(null); // not used anyway

        // when
        serviceIdeaCreate.create(DTO);

        verify(repoIdea).save(any());
    }
}
