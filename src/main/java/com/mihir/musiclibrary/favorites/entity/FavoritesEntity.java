package com.mihir.musiclibrary.favorites.entity;

import java.util.UUID;
import jakarta.persistence.*;

//@Entity
public class FavoritesEntity {

    @Id

    @Column(name = "favorite_id", nullable = false)
    private UUID favoriteId;

    // Getters and setters omitted for brevity

    // Assuming a relationship with other entities is established
    // through additional fields and annotations.
}