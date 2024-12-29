package com.mihir.musiclibrary.artist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.UUID;

@Data
public class ArtistDTO {

    private UUID artistId;

    @NotBlank(message = "Artist name cannot be blank.")
    private String name;

    @NotNull(message = "Grammy award status cannot be blank.")
    private boolean grammy;

    @NotNull(message = "Visibility toggle cannot be blank")
    private boolean hidden;
}
