package com.mihir.musiclibrary.tracks.entity;

import com.mihir.musiclibrary.album.entity.AlbumEntity;
import com.mihir.musiclibrary.artist.entity.ArtistEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

@Entity
@Table(name = "tracks")
public class TrackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "track_id", columnDefinition = "BINARY(16)")
    private UUID trackId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "artist_id", referencedColumnName = "artist_id", nullable = false)
    private ArtistEntity artist;

    @ManyToOne(optional = false)
    @JoinColumn(name = "album_id", referencedColumnName = "album_id", nullable = false)
    private AlbumEntity album;

    @NotBlank(message = "Track name cannot be blank")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "duration", nullable = false)
    private int duration; // Duration in seconds

    @Column(name = "hidden", nullable = false)
    private boolean hidden;

    public TrackEntity() {}

    // Getters and Setters
    public UUID getTrackId() { return trackId; }
    public void setTrackId(UUID trackId) { this.trackId = trackId; }

    public ArtistEntity getArtist() { return artist; }
    public void setArtist(ArtistEntity artist) { this.artist = artist; }

    public AlbumEntity getAlbum() { return album; }
    public void setAlbum(AlbumEntity album) { this.album = album; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public boolean isHidden() { return hidden; }
    public void setHidden(boolean hidden) { this.hidden = hidden; }
}
