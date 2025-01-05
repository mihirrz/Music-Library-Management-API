package com.mihir.musiclibrary.album.repository;

import com.mihir.musiclibrary.album.entity.AlbumEntity;
import com.mihir.musiclibrary.artist.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, UUID> {
    List<AlbumEntity> findByArtist(ArtistEntity artist);
}
