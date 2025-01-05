package com.mihir.musiclibrary.album.dto;

import java.util.UUID;

public class AlbumDTO {
    private UUID albumId;
    private UUID artistId;
    private String name;
    private int year;
    private boolean hidden;

    // Getters and Setters
    public UUID getAlbumId() { return albumId; }
    public void setAlbumId(UUID albumId) { this.albumId = albumId; }

    public UUID getArtistId() { return artistId; }
    public void setArtistId() { this.artistId = artistId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public boolean isHidden() { return hidden; }
    public void setHidden(boolean hidden) { this.hidden = hidden; }
}