package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.core.dto.Idea;
import com.github.toficzak.digletto.core.dto.IdeaListing;
import com.github.toficzak.digletto.core.exception.IdeaNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceIdeaView {

    private final RepoIdea repoIdea;
    private final RepoVIdeaListing repoVIdeaListing;

    public Page<IdeaListing> listing(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return repoVIdeaListing.findAll(pageable).map(EntityVIdeaListing::toDto);
    }

    public Idea get(Long id) {
        return repoIdea.findById(id)
                .orElseThrow(IdeaNotFoundException::new)
                .toDto();
    }

}
