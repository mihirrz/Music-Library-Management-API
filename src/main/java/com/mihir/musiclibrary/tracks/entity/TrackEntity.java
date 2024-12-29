package com.mihir.musiclibrary.tracks.entity;

import java.util.UUID;
import jakarta.persistence.*;

//@Entity
public class TrackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "track_id", columnDefinition = "BINARY(16)")
    private UUID trackId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "hidden", nullable = false)
    private boolean hidden;

    // Getters and setters omitted for brevity
}