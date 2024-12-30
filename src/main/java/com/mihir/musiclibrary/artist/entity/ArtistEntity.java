package com.mihir.musiclibrary.artist.entity;

import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name = "artist")
public class ArtistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "artist_id", columnDefinition = "BINARY(16)")
    private UUID artistId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "grammy", nullable = false)
    private boolean grammy;

    @Column(name = "hidden", nullable = false)
    private boolean hidden;

    public UUID getArtistId() { return artistId; }

    public void setArtistId(UUID artistId) { this.artistId = artistId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public boolean isGrammy() { return grammy; }

    public void setGrammy(boolean grammy) { this.grammy = grammy; }

    public boolean isHidden() { return hidden; }

    public void setHidden(boolean hidden) { this.hidden = hidden; }

    public ArtistEntity(UUID artistId, String name, boolean grammy, boolean hidden) {
        this.artistId = artistId;
        this.name = name;
        this.grammy = grammy;
        this.hidden = hidden;
    }

    public ArtistEntity(){};
}