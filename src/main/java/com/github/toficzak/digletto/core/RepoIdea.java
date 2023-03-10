package com.github.toficzak.digletto.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RepoIdea extends JpaRepository<EntityIdea, Long> {

    boolean existsByNameAndOwner(String name, EntityUser owner);
}
