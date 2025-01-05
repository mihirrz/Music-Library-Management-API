package com.mihir.musiclibrary.album.services;

import com.mihir.musiclibrary.album.dto.AlbumDTO;
import com.mihir.musiclibrary.album.entity.AlbumEntity;
import com.mihir.musiclibrary.album.repository.AlbumRepository;
import com.mihir.musiclibrary.artist.entity.ArtistEntity;
import com.mihir.musiclibrary.artist.repository.ArtistRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    public List<AlbumEntity> fetchAlbums() {
        return albumRepository.findAll();
    }

    public AlbumEntity fetchAlbum(UUID id) {
        Optional<AlbumEntity> fetchedAlbum = albumRepository.findById(id);
        if (fetchedAlbum.isPresent())
            return fetchedAlbum.get();
        else
            throw new RuntimeException("Album not found");
    }

    public AlbumEntity addAlbum(@Valid AlbumDTO albumDTO) {
        Optional<ArtistEntity> artistOptional = artistRepository.findById(albumDTO.getArtistId());
        if(artistOptional.isEmpty())
            throw new RuntimeException("Target artist not find to set album");

        AlbumEntity newAlbum = new AlbumEntity();
        newAlbum.setArtist(artistOptional.get());
        newAlbum.setName(albumDTO.getName());
        newAlbum.setYear(albumDTO.getYear());
        newAlbum.setHidden(albumDTO.isHidden());

        return albumRepository.save(newAlbum);
    }

    public AlbumEntity updateAlbum(UUID id, Map<String, Object> updates) {
        AlbumEntity album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not found"));

        // Apply updates
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    album.setName((String) value);
                    break;
                case "artistId":
                    album.setAlbumId((UUID) value);
                    break;
                case "year":
                    album.setYear((int) value);
                    break;
                case "hidden":
                    album.setHidden((boolean) value);
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        // Save updated album
        return albumRepository.save(album);
    }
}