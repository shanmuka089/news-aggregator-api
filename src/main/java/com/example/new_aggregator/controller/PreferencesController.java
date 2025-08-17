package com.example.new_aggregator.controller;

import com.example.new_aggregator.config.ApiDocsConfig;
import com.example.new_aggregator.models.dto.PreferenceDto;
import com.example.new_aggregator.models.dto.ResponseDto;
import com.example.new_aggregator.utils.ResponseStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "Preferences")
@RequestMapping("${spring.application.base-path}")
public class PreferencesController
{

    @ApiDocsConfig
    @GetMapping("/preferences")
    public ResponseEntity<ResponseDto<PreferenceDto>> fetchPreferences() {
        PreferenceDto preferenceDto = null;
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, preferenceDto));
    }
    
    @ApiDocsConfig
    @PutMapping("/preferences")
    public ResponseEntity<ResponseDto<String>> updatePreferences(@RequestBody PreferenceDto preferenceDto)
    {
        // Logic to update preferences
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS,"Preferences updated successfully"));
    }
}
