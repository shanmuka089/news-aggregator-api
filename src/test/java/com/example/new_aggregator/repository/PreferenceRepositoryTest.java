package com.example.new_aggregator.repository;

import com.example.new_aggregator.models.entities.PreferenceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class PreferenceRepositoryTest
{

    @Autowired
    private PreferenceRepository preferenceRepository;
    
    @BeforeEach
    void setup() {
        System.setProperty("spring.profiles.active", "test");
    }
    
    @Test
    void testFindByUserId()
    {
        Long userId = 1L;
        PreferenceEntity preference = new PreferenceEntity();
        preference.setUserId(userId);
        preferenceRepository.save(preference);
        
        Optional<PreferenceEntity> foundPreference = preferenceRepository.findByUserId(userId);
        
        assertTrue(foundPreference.isPresent());
        assertEquals(userId, foundPreference.get().getUserId());

        preferenceRepository.deleteById(userId);
        
        Optional<PreferenceEntity> deletedPreference = preferenceRepository.findByUserId(userId);
        assertTrue(deletedPreference.isEmpty(), "Preference should be deleted");
    }
}
