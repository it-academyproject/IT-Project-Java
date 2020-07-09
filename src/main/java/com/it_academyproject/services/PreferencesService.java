package com.it_academyproject.services;

import com.it_academyproject.controllers.DTOs.DTOPreferencesI;
import com.it_academyproject.domains.Preferences;
import com.it_academyproject.repositories.PreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreferencesService {

    @Autowired
    PreferencesRepository preferencesRepository;

    public List<DTOPreferencesI> getAll() {
        return preferencesRepository.getPreferencesParameters();
    }

}
