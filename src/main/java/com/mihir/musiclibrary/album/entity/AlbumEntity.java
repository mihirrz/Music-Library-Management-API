package com.mihir.musiclibrary.album.entity;

import com.mihir.musiclibrary.artist.entity.ArtistEntity;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "albums")
public class AlbumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "album_id", columnDefinition = "BINARY(16)")
    private UUID albumId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "artist_id", referencedColumnName = "artist_id", nullable = false)
    private ArtistEntity artist;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "\"year\"", nullable = false)
    private int year;

    @Column(name = "hidden", nullable = false)
    private boolean hidden;

    // Getters and Setters
    public UUID getAlbumId() { return albumId; }
    public void setAlbumId(UUID albumId) { this.albumId = albumId; }

    public ArtistEntity getArtist() { return artist; }
    public void setArtist(ArtistEntity artist) { this.artist = artist; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public boolean isHidden() { return hidden; }
    public void setHidden(boolean hidden) { this.hidden = hidden; }

    // Constructors
    public AlbumEntity() {}

    public AlbumEntity(ArtistEntity artist, String name, int year, boolean hidden) {
        this.artist = artist;
        this.name = name;
        this.year = year;
        this.hidden = hidden;
    }
}