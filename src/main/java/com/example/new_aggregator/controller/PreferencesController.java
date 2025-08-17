package com.example.new_aggregator.controller;

import com.example.new_aggregator.config.annotations.ApiResponseDto;
import com.example.new_aggregator.models.dto.PreferenceDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.service.PreferenceService;
import com.example.new_aggregator.utils.ResponseStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("${spring.application.base-path}")
public class PreferencesController
{
    
    @Autowired
    private PreferenceService preferenceService;

    @PostMapping("/preferences")
    public ResponseDto<PreferenceDto> savePreferences(@Valid @RequestBody PreferenceDto preferenceDto) {
        PreferenceDto savedPreference = preferenceService.savePreferences(preferenceDto);
        return new ResponseDto<>(ResponseStatus.SUCCESS, savedPreference);
    }
    
    @GetMapping("/preferences")
    public ResponseEntity<ResponseDto<PreferenceDto>> fetchPreferences() {
        PreferenceDto preferenceDto = preferenceService.fetchPreferences();
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, preferenceDto));
    }
    
    @PutMapping("/preferences")
    public ResponseEntity<ResponseDto<String>> updatePreferences(@Valid @RequestBody PreferenceDto preferenceDto)
    {
        PreferenceDto updatedPreference = preferenceService.updatePreference(preferenceDto);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS,"Preferences updated successfully"));
    }
    
    @DeleteMapping("/preferences/{preferenceId}")
    public ResponseEntity<ResponseDto<String>> deletePreferences(@PathVariable("preferenceId") Long preferenceId) {
        preferenceService.deletePreferences(preferenceId);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, "Preferences deleted successfully"));
    }
}
