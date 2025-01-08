package com.mihir.musiclibrary.tracks.controller;

import com.mihir.musiclibrary.Response.ApiResponse;
import com.mihir.musiclibrary.Response.ErrorDetails;
import com.mihir.musiclibrary.album.entity.AlbumEntity;
import com.mihir.musiclibrary.tracks.dto.TrackDTO;
import com.mihir.musiclibrary.tracks.entity.TrackEntity;
import com.mihir.musiclibrary.tracks.services.TrackService;
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
public class TrackController {

    @Autowired
    private TrackService trackService;

    @GetMapping("/tracks")
    public ResponseEntity<ApiResponse<?>> getAllTracks()
    {
        List<TrackEntity> fetchedTracks = trackService.fetchAllTracks();
        if (fetchedTracks.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(200, null, "No Tracks found", null));
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(new ApiResponse<>(302, fetchedTracks, "Tracks fetched successfully.", null));
    }

    // Add a new track
    @PostMapping("/tracks/add-track")
    public ResponseEntity<ApiResponse<?>> addTrack(@Valid @RequestBody TrackDTO trackDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<ErrorDetails> errorDetails = bindingResult.getFieldErrors().stream()
                    .map(error -> new ErrorDetails(error.getField(), error.getDefaultMessage()))
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null, "Validation failed", errorDetails));
        }

        TrackEntity createdTrack = trackService.addTrack(trackDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, null, "Track added successfully.", null));
    }

    @PutMapping("/tracks/{id}")
    public ResponseEntity<ApiResponse<?>> updateTrack(@PathVariable UUID id, @RequestBody Map<String, Object> updates)
    {
        try {
            // Update the Track
            TrackEntity updatedTrack = trackService.updateTrack(id, updates);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse<>(200, updatedTrack, "Track updated successfully.", null));
        } catch (RuntimeException ex) {
            // If track not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, null, "Track not found",
                            Collections.singletonList(new ErrorDetails("trackId", ex.getMessage()))));
        } catch (Exception ex) {
            // For other unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, null, "An unexpected error occurred.",
                            Collections.singletonList(new ErrorDetails("exception", ex.getMessage()))));
        }
    }

    @GetMapping("/tracks/{id}")
    public ResponseEntity<ApiResponse<?>> getTrack(@PathVariable UUID id)
    {
        try {
            TrackEntity fetchedTrack = trackService.fetchTrack(id);
            return ResponseEntity.ok(new ApiResponse<>(200, fetchedTrack ,"Track fetched successfully", null));
        } catch (RuntimeException e) {
            // This will handle the case where the track is not found (or any RuntimeException)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, null, e.getMessage(), null));
        } catch (Exception e) {
            // Handle other unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, null, "An unexpected error occurred", null));
        }
    }

    @DeleteMapping("/tracks/{id}")
    public ResponseEntity<ApiResponse<?>> deleteTrack(@PathVariable UUID id) {
        try {
            trackService.deleteTrack(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>(204, null, "Track deleted successfully.", null));
        } catch (RuntimeException ex) {
            // If track not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, null, "Track not found",
                            Collections.singletonList(new ErrorDetails("trackId", ex.getMessage()))));
        } catch (Exception ex) {
            // For other unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, null, "An unexpected error occurred.",
                            Collections.singletonList(new ErrorDetails("exception", ex.getMessage()))));
        }
    }

}
