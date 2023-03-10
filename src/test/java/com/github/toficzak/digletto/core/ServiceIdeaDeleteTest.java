package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.core.dto.DeleteIdea;
import com.github.toficzak.digletto.core.exception.IdeaCannotBeDeletedException;
import com.github.toficzak.digletto.core.exception.IdeaNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceIdeaDeleteTest {

    @Mock
    private RepoIdea repoIdea;

    @Mock
    private EntityIdea entityIdea;

    @InjectMocks
    private ServiceIdeaDelete serviceIdeaDelete;

    @Test
    void delete_shouldSucceed() {
        when(repoIdea.findById(any())).thenReturn(Optional.of(entityIdea));
        when(entityIdea.canBeDeleted()).thenReturn(true);
        doNothing().when(repoIdea).delete(entityIdea);

        DeleteIdea dto = new DeleteIdea(1L);

        // when
        serviceIdeaDelete.delete(dto);

        verify(repoIdea).delete(any());
    }

    @Test
    void delete_cannotBeDeleted_shouldThrowException() {
        when(repoIdea.findById(any())).thenReturn(Optional.of(entityIdea));
        when(entityIdea.canBeDeleted()).thenReturn(false);

        DeleteIdea dto = new DeleteIdea(1L);

        // when
        assertThrows(IdeaCannotBeDeletedException.class, () -> serviceIdeaDelete.delete(dto));

        verify(repoIdea, never()).save(any());
    }

    @Test
    void delete_notExistingIdea_shouldThrowException() {
        when(repoIdea.findById(any())).thenReturn(Optional.empty());

        DeleteIdea dto = new DeleteIdea(1L);

        // when
        assertThrows(IdeaNotFoundException.class, () -> serviceIdeaDelete.delete(dto));

        verify(repoIdea, never()).save(any());
    }
}
