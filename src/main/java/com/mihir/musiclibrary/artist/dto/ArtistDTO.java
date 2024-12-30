package com.mihir.musiclibrary.artist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;


@Data
public class ArtistDTO {

    private UUID artistId;

    @NotBlank(message = "Artist name cannot be blank.")
    private String name;

    @NotNull(message = "Grammy award status cannot be blank.")
    private boolean grammy;

    @NotNull(message = "Visibility toggle cannot be blank")
    private boolean hidden;

    public UUID getArtistId() { return artistId; }

    public String getName() { return name; }

    public boolean isGrammy() { return grammy; }

    public boolean isHidden() { return hidden; }
}
