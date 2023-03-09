package com.github.toficzak.digletto.web;

import com.github.toficzak.digletto.core.ServiceIdeaCreate;
import com.github.toficzak.digletto.core.ServiceIdeaDelete;
import com.github.toficzak.digletto.core.ServiceIdeaView;
import com.github.toficzak.digletto.core.dto.CreateIdea;
import com.github.toficzak.digletto.core.dto.DeleteIdea;
import com.github.toficzak.digletto.core.dto.Idea;
import com.github.toficzak.digletto.web.view.ViewIdea;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/ideas")
@RequiredArgsConstructor
public class ResourceIdea {

    private final ServiceIdeaView serviceIdeaView;
    private final ServiceIdeaCreate serviceIdeaCreate;
    private final ServiceIdeaDelete serviceIdeaDelete;

    @GetMapping
    public Page<ViewIdea> listing(Pageable pageable) {
        Page<Idea> ideas = serviceIdeaView.listing(pageable);
        return ideas.map(ViewIdea::new);
    }

    @GetMapping("/{ideaId}")
    public ViewIdea get(@PathVariable("ideaId") Long ideaId) {
        Idea idea = serviceIdeaView.get(ideaId);
        return new ViewIdea(idea);
    }


    @PostMapping
    public ResponseEntity<ViewIdea> create(@RequestBody CreateIdea dto) {
        Idea idea = serviceIdeaCreate.create(dto);
        ViewIdea view = new ViewIdea(idea);
        URI uri = URI.create("/ideas/" + view.id().toString());
        return ResponseEntity.created(uri).body(view);
    }

    @DeleteMapping("/{ideaId}")
    public ResponseEntity<Void> delete(@PathVariable("ideaId") Long ideaId) {
        DeleteIdea dto = new DeleteIdea(ideaId);
        serviceIdeaDelete.delete(dto);
        return ResponseEntity.ok().build();
    }
}
