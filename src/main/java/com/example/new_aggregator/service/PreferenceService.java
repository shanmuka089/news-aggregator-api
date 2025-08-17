package com.example.new_aggregator.service;

import com.example.new_aggregator.models.dto.PreferenceDto;

public interface PreferenceService
{
    /**
     * Fetches user preferences.
     * @return PreferenceDto containing user preferences.
     */
    PreferenceDto fetchPreferences();

    /**
     * Updates user preferences.
     *
     * @param preferenceDto PreferenceDto containing updated preferences.
     *
     * @return String message indicating success or failure of the update operation.
     */
    PreferenceDto updatePreference(PreferenceDto preferenceDto);
    
    /**
     * Saves user preferences.
     * @param preferenceDto PreferenceDto containing preferences to be saved.
     * @return PreferenceDto containing the saved preferences.
     */
    PreferenceDto savePreferences(PreferenceDto preferenceDto);
    
    /**
     * Deletes user preferences.
     */
    void deletePreferences(Long preferenceId);
}
