package com.example.new_aggregator.controller;

import com.example.new_aggregator.models.dto.PreferenceDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.service.PreferenceService;
import com.example.new_aggregator.utils.ResponseStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${spring.application.base-path}")
public class PreferencesController
{
    
    @Autowired
    private PreferenceService preferenceService;

    /**
     * Saves user preferences.
     * @param preferenceDto PreferenceDto containing preferences to be saved.
     * @return ResponseDto containing the saved preferences.
     */
    @PostMapping("/preferences")
    public ResponseDto<PreferenceDto> savePreferences(@Valid @RequestBody PreferenceDto preferenceDto) {
        PreferenceDto savedPreference = preferenceService.savePreferences(preferenceDto);
        return new ResponseDto<>(ResponseStatus.SUCCESS, savedPreference);
    }
    
    /**
     * Fetches user preferences.
     * @return ResponseDto containing user preferences.
     */
    @GetMapping("/preferences")
    public ResponseEntity<ResponseDto<PreferenceDto>> fetchPreferences() {
        PreferenceDto preferenceDto = preferenceService.fetchPreferences();
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, preferenceDto));
    }
    
    /**
     * Updates user preferences.
     * @param preferenceDto PreferenceDto containing updated preferences.
     * @return ResponseDto indicating success of the update operation.
     */
    @PutMapping("/preferences")
    public ResponseEntity<ResponseDto<String>> updatePreferences(@Valid @RequestBody PreferenceDto preferenceDto)
    {
        PreferenceDto updatedPreference = preferenceService.updatePreference(preferenceDto);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS,"Preferences updated successfully"));
    }
    
    /**
     * Deletes user preferences.
     * @param preferenceId The ID of the preference to be deleted.
     * @return ResponseDto indicating success of the delete operation.
     */
    @DeleteMapping("/preferences/{preferenceId}")
    public ResponseEntity<ResponseDto<String>> deletePreferences(@PathVariable("preferenceId") Long preferenceId) {
        preferenceService.deletePreferences(preferenceId);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, "Preferences deleted successfully"));
    }
}
