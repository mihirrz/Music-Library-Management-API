package com.mihir.musiclibrary.album.controllers;

import com.mihir.musiclibrary.Response.ApiResponse;
import com.mihir.musiclibrary.Response.ErrorDetails;
import com.mihir.musiclibrary.album.dto.AlbumDTO;
import com.mihir.musiclibrary.album.entity.AlbumEntity;
import com.mihir.musiclibrary.album.services.AlbumService;
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
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @GetMapping("/albums")
    public ResponseEntity<ApiResponse<?>> getAllAlbums()  {
        List<AlbumEntity>  fetchedAlbums = albumService.fetchAlbums();
        if(fetchedAlbums.isEmpty())
            return ResponseEntity.ok(new ApiResponse<>(200, null, "No Albums found", null));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, fetchedAlbums, "Albums fetched successfully.", null));
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<ApiResponse<?>> getAlbum(@PathVariable UUID id)
    {
        try {
            AlbumEntity fetchedAlbum = albumService.fetchAlbum(id);
            return ResponseEntity.ok(new ApiResponse<>(200, fetchedAlbum ,"Album fetched successfully", null));
        } catch (RuntimeException e) {
            // This will handle the case where the album is not found (or any RuntimeException)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, null, e.getMessage(), null));
        } catch (Exception e) {
            // Handle other unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, null, "An unexpected error occurred", null));
        }
    }

    @PostMapping("/albums/add-album")
    public ResponseEntity<ApiResponse<?>> addAlbum(@Valid @RequestBody AlbumDTO albumDTO, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            List<ErrorDetails> errorDetails = bindingResult.getFieldErrors().stream()
                    .map(error -> new ErrorDetails(error.getField(), error.getDefaultMessage()))
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(400, null, "Validation failed", errorDetails));
        }

        AlbumEntity newAlbum = albumService.addAlbum(albumDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, null, "Album added successfully.", null));
    }

    @PutMapping("/albums/{id}")
    public ResponseEntity<ApiResponse<?>> updateAlbum(@PathVariable UUID id,  @RequestBody Map<String, Object> updates)
    {
        try {
            // Update the album
            AlbumEntity updatedAlbum = albumService.updateAlbum(id, updates);

            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>(204, updatedAlbum, "Album updated successfully.", null));
        } catch (RuntimeException ex) {
            // If album not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, null, "Album not found",
                            Collections.singletonList(new ErrorDetails("albumId", ex.getMessage()))));
        } catch (Exception ex) {
            // For other unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, null, "An unexpected error occurred.",
                            Collections.singletonList(new ErrorDetails("exception", ex.getMessage()))));
        }
    }

}
