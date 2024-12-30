package com.mihir.musiclibrary.artist.services;


import com.mihir.musiclibrary.artist.dto.ArtistDTO;
import com.mihir.musiclibrary.artist.entity.ArtistEntity;
import com.mihir.musiclibrary.artist.repository.ArtistRepository;
import com.mihir.musiclibrary.auth.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private JwtService jwtService;

    public ArtistEntity addArtist(@Valid ArtistDTO artistDTO) {
        ArtistEntity artist = new ArtistEntity();
        artist.setArtistId(artistDTO.getArtistId());
        artist.setName(artistDTO.getName());
        artist.setGrammy(artistDTO.isGrammy());
        artist.setHidden(artistDTO.isHidden());

        return artistRepository.save(artist);
    }

    public List<ArtistEntity> fetchArtists() {
        return artistRepository.findAll();
    }

    public ArtistEntity fetchArtist(UUID id) {
        Optional<ArtistEntity> fetchedArtist = artistRepository.findById(id);
        if (fetchedArtist.isPresent())
            return fetchedArtist.get();
        else
            throw new RuntimeException("Artist not found");
    }

    public boolean deleteArtist(UUID id) {
        Optional<ArtistEntity> artist = artistRepository.findById(id);
        if (artist.isPresent()) {
            artistRepository.delete(artist.get());
            return true;
        }
        return false; // Artist not found
    }

    public ArtistEntity updateArtist(UUID id, Map<String, Object> updates) {
        ArtistEntity artist = artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        // Apply updates
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    artist.setName((String) value);
                    break;
                case "grammy":
                    artist.setGrammy((boolean) value);
                    break;
                case "hidden":
                    artist.setHidden((boolean) value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        // Save updated artist
        return artistRepository.save(artist);
    }



}
