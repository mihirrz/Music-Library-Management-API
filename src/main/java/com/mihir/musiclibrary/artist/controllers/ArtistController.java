package com.mihir.musiclibrary.artist.controllers;

import com.mihir.musiclibrary.Response.ApiResponse;
import com.mihir.musiclibrary.Response.ErrorDetails;
import com.mihir.musiclibrary.artist.dto.ArtistDTO;
import com.mihir.musiclibrary.artist.entity.ArtistEntity;
import com.mihir.musiclibrary.artist.services.ArtistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @PostMapping("/artists/add-artist")
    public ResponseEntity<ApiResponse<?>> addArtist(@Valid @RequestBody ArtistDTO artistDTO, BindingResult bindingResult)
    {

        if (bindingResult.hasErrors()) {
            List<ErrorDetails> errorDetails = bindingResult.getFieldErrors().stream()
                    .map(error -> new ErrorDetails(error.getField(), error.getDefaultMessage()))
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null, "Validation failed", errorDetails));
        }

        ArtistEntity addedArtist = artistService.addArtist(artistDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, null, "Artist added successfully.", null));
    }

    @GetMapping("/artists")
    public ResponseEntity<ApiResponse<?>> getAllArtist()
    {
        List<ArtistEntity> fetchedArtists = artistService.fetchArtists();
        if(fetchedArtists.isEmpty())
            return ResponseEntity.ok(new ApiResponse<>(200, null, "No Artists found", null));
        return ResponseEntity.ok(new ApiResponse<>(200, fetchedArtists, "Artists fetched successfully", null));
    }

    @GetMapping("/artists/{id}")
    public ResponseEntity<ApiResponse<?>> getArtist(@PathVariable UUID id) {
        try {
            ArtistEntity fetchedArtist = artistService.fetchArtist(id);
            return ResponseEntity.ok(new ApiResponse<>(200, fetchedArtist, "Artist fetched successfully", null));
        } catch (RuntimeException e) {
            // This will handle the case where the artist is not found (or any RuntimeException)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, null, e.getMessage(), null));
        } catch (Exception e) {
            // Handle other unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, null, "An unexpected error occurred", null));
        }
    }



    @DeleteMapping("/artists/{id}")
    public ResponseEntity<ApiResponse<?>> deleteArtist(
            @PathVariable UUID id,
            @RequestHeader("Authorization") String token)
    {
        try {
            // Check if the artist exists
            boolean isDeleted = artistService.deleteArtist(id);
            if (isDeleted) {
                return ResponseEntity.ok(
                        new ApiResponse<>(200, null, "Artist deleted successfully", null)
                );
            } else {
                return ResponseEntity.status(404).body(
                        new ApiResponse<>(404, null, "Artist not found", null)
                );
            }
        } catch (Exception ex) {
            return ResponseEntity.status(500).body(
                    new ApiResponse<>(500, null, "An error occurred while deleting the artist",
                            Collections.singletonList(new ErrorDetails("Exception", ex.getMessage())))
            );
        }
    }

    @PutMapping("/artists/{id}")
    public ResponseEntity<ApiResponse<?>> updateArtist(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> updates,
            @RequestHeader("Authorization") String token) {

        try {
            // Update the artist
            ArtistEntity updatedArtist = artistService.updateArtist(id, updates);

            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>(204, updatedArtist, "Artist updated successfully.", null));
        } catch (RuntimeException ex) {
            // If artist not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, null, "Artist not found",
                            Collections.singletonList(new ErrorDetails("artistId", ex.getMessage()))));
        } catch (Exception ex) {
            // For other unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, null, "An unexpected error occurred.",
                            Collections.singletonList(new ErrorDetails("exception", ex.getMessage()))));
        }
    }
}
