package com.github.toficzak.digletto.core;

import com.github.toficzak.digletto.EntityBase;
import com.github.toficzak.digletto.core.dto.User;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntityUser extends EntityBase {

    @Email
    @NotNull
    private String email;
    @NotNull
    private String name;

    User toDto() {
        return new User(this.id, this.created, this.email, this.name);
    }
}
