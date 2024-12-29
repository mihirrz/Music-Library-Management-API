package com.mihir.musiclibrary.artist.services;


import com.mihir.musiclibrary.artist.dto.ArtistDTO;
import com.mihir.musiclibrary.artist.entity.ArtistEntity;
import com.mihir.musiclibrary.artist.repository.ArtistRepository;
import com.mihir.musiclibrary.auth.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private JwtService jwtService;

    public ArtistEntity saveArtist(@Valid ArtistDTO artistDTO) {
        ArtistEntity artist = new ArtistEntity();
        artist.setArtistId(artistDTO.getArtistId());
        artist.setName(artistDTO.getName());
        artist.setGrammy(artistDTO.isGrammy());

        return artistRepository.save(artist);
    }
}
