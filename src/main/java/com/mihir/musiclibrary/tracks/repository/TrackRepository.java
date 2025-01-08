package com.mihir.musiclibrary.tracks.repository;

import com.mihir.musiclibrary.tracks.entity.TrackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrackRepository extends JpaRepository<TrackEntity, UUID> {}
