package com.mihir.musiclibrary.albums.entity;

import java.util.UUID;
import jakarta.persistence.*;
//@Entity
public class AlbumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "album_id", columnDefinition = "BINARY(16)")
    private UUID albumId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "hidden", nullable = false)
    private boolean hidden;

    // Getters and setters omitted for brevity
}