package com.mihir.musiclibrary.artist.repository;

import com.mihir.musiclibrary.artist.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistEntity, UUID> {
    Optional<ArtistEntity> findById(java.util.UUID id);
}
