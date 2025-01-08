package com.mihir.musiclibrary.tracks.services;

import com.mihir.musiclibrary.album.entity.AlbumEntity;
import com.mihir.musiclibrary.album.repository.AlbumRepository;
import com.mihir.musiclibrary.artist.entity.ArtistEntity;
import com.mihir.musiclibrary.artist.repository.ArtistRepository;
import com.mihir.musiclibrary.tracks.dto.TrackDTO;
import com.mihir.musiclibrary.tracks.entity.TrackEntity;
import com.mihir.musiclibrary.tracks.repository.TrackRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Service
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    public List<TrackEntity> fetchAllTracks() {
        return trackRepository.findAll();
    }

    public TrackEntity addTrack(@Valid TrackDTO trackDTO) {
        Optional<ArtistEntity> artistOptional = artistRepository.findById(trackDTO.getArtistId());
        if(artistOptional.isEmpty())
            throw new RuntimeException("Target artist not find to set track");

        Optional<AlbumEntity> albumOptional = albumRepository.findById(trackDTO.getAlbumId());
        if(albumOptional.isEmpty())
            throw new RuntimeException("Target album not find to set track");

        TrackEntity trackEntity = new TrackEntity();
        trackEntity.setArtist(artistOptional.get());
        trackEntity.setAlbum(albumOptional.get());
        trackEntity.setName(trackDTO.getName());
        trackEntity.setDuration(trackDTO.getDuration());
        trackEntity.setHidden(trackDTO.isHidden());

        return trackRepository.save(trackEntity);
    }


    public TrackEntity updateTrack(UUID id, Map<String, Object> updates) {
        TrackEntity track = trackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Track not found"));

        // Apply updates
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    track.setName((String) value);
                    break;
                case "artistId":
                    Optional<ArtistEntity> artistOptional = artistRepository.findById((UUID) value);
                    artistOptional.ifPresent(track::setArtist); // Set the artist
                    break;
                case "albumId":
                    Optional<AlbumEntity> albumOptional = albumRepository.findById((UUID) value);
                    albumOptional.ifPresent(track::setAlbum); // Set the album
                    break;
                case "duration":
                    track.setDuration((int) value);
                    break;
                case "hidden":
                    track.setHidden((boolean) value);
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        });

        // Save updated album
        return trackRepository.save(track);
    }

    public void deleteTrack(UUID id) {
        TrackEntity track = trackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Track not found"));

        trackRepository.delete(track);  // Deleting the track from the repository
    }

    public TrackEntity fetchTrack(UUID id) {
        Optional<TrackEntity> fetchedTrack = trackRepository.findById(id);
        if (fetchedTrack.isPresent())
            return fetchedTrack.get();
        else
            throw new RuntimeException("Track not found");
    }
}
