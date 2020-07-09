package com.it_academyproject.controllers;

import com.it_academyproject.controllers.DTOs.DTOPreferencesI;
import com.it_academyproject.domains.Preferences;
import com.it_academyproject.exceptions.CustomException;
import com.it_academyproject.repositories.PreferencesRepository;
import com.it_academyproject.services.PreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
public class PreferencesController {

    @Autowired
    PreferencesService preferencesService;

    @Autowired
    PreferencesRepository preferencesRepository;

    @GetMapping("/api/preferences")
    public List<DTOPreferencesI> getAll() {
        List<DTOPreferencesI> preferences = preferencesService.getAll();
        return preferences;
    }

    @PostMapping("/api/preferences")
    public Preferences createPreference(@RequestBody Preferences preferences) {

        System.out.println("request received");

        if (preferencesRepository.findPreferenceByName(preferences.getName()) != null)
            throw new CustomException("Preference exists in database.");

        preferencesRepository.save(preferences);

        return preferences;
    }

    @PutMapping("api/preferences/{id}")
    public Preferences updatePreferences(@RequestBody Preferences p, @PathVariable Long id) throws Exception {

        if (preferencesRepository.findPreferenceById(id) == null)
            throw new CustomException("Preference not exists in database.");

        return preferencesRepository.findById(id).map(preferences -> {
            if(preferencesRepository.findPreferenceByName(p.getName()) != null)
                throw new CustomException("Field name is duplicated.");
            preferences.setName(p.getName());
            preferences.setValue(p.getValue());
            preferences.setActive(p.getActive());
            return preferencesRepository.save(preferences);
        }).orElseThrow(() -> {
            return new CustomException("Error.");
        });

    }

}
