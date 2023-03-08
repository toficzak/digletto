package com.github.toficzak.digletto.core;

import lombok.RequiredArgsConstructor;
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
    public ViewIdea get() {
        return serviceIdeaView.get();
    }

    @PostMapping
    public ResponseEntity<ViewIdea> create(@RequestBody CreateViewDto dto) {
        ViewIdea view = serviceIdeaCreate.create(dto);
        URI uri = URI.create("/ideas/" + view.id().toString());
        return ResponseEntity.created(uri).body(view);
    }

    @DeleteMapping("/{ideaId}")
    public ResponseEntity<Void> delete(@PathVariable("ideaId") Long ideaId) {
        DtoDeleteIdea dto = new DtoDeleteIdea(ideaId);
        serviceIdeaDelete.delete(dto);
        return ResponseEntity.ok().build();
    }
}
