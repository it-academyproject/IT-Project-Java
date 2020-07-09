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
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PreferencesController {

    @Autowired
    PreferencesService preferencesService;

    @Autowired
    PreferencesRepository preferencesRepository;

    @GetMapping("/api/projects/preferences")
    public List<DTOPreferencesI> getAll() {
        List<DTOPreferencesI> preferences = preferencesService.getAll();
        return preferences;
    }

    @PostMapping("/api/projects/preferences")
    public Preferences createPreference(@RequestBody Preferences preferences) {

        System.out.println("request received");

        if (preferencesRepository.findPreferenceByName(preferences.getPreference_name()) != null)
            throw new CustomException("Preference exists in database.");

        preferencesRepository.save(preferences);

        return preferences;
    }

    @PutMapping("api/projects/preferences/{id}")
    public Preferences updatePreferences(@RequestBody Preferences p, @PathVariable Long id) throws Exception {

        if (preferencesRepository.findPreferenceById(id) == null)
            throw new CustomException("Preference not exists in database.");

        return preferencesRepository.findById(id).map(preferences -> {
            preferences.setPreference_name(p.getPreference_name());
            preferences.setPreference_value(p.getPreference_value());
            preferences.setActive(p.getActive());
            return preferencesRepository.save(preferences);
        }).orElseThrow(() -> {
            return new Exception("Invalid Parameters");
        });

    }

}
