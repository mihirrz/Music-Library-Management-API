package com.mihir.musiclibrary.tracks.dto;

import java.util.UUID;

public class TrackDTO {

    private UUID trackId;
    private UUID artistId;
    private UUID albumId;
    private String name;
    private int duration;
    private boolean hidden;

    public TrackDTO(){}
    // Getters and Setters
    public UUID getTrackId() { return trackId; }
    public void setTrackId(UUID trackId) { this.trackId = trackId; }

    public UUID getArtistId() { return artistId; }
    public void setArtistId(UUID artistId) { this.artistId = artistId; }

    public UUID getAlbumId() { return albumId; }
    public void setAlbumId(UUID albumId) { this.albumId = albumId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public boolean isHidden() { return hidden; }
    public void setHidden(boolean hidden) { this.hidden = hidden; }
}
