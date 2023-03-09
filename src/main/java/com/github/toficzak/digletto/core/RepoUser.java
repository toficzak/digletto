package com.github.toficzak.digletto.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RepoUser extends JpaRepository<EntityUser, Long> {
}
