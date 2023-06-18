package com.kpo.springshaurma.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Ingredient {

    @Id
    private String id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;


    public enum Type {
        SAUCE,
        WRAP
    }
}
