package com.example.new_aggregator.repository;

import com.example.new_aggregator.models.entities.PreferenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PreferenceRepository extends JpaRepository<PreferenceEntity, Long>
{
    Optional<PreferenceEntity> findByUserId(Long userId);
}
