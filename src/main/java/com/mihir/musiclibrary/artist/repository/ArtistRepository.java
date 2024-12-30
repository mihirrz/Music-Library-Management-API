package com.mihir.musiclibrary.artist.repository;

import com.mihir.musiclibrary.artist.entity.ArtistEntity;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistEntity, UUID> {}
