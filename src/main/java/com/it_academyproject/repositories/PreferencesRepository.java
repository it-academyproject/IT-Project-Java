package com.it_academyproject.repositories;

import com.it_academyproject.controllers.DTOs.DTOPreferencesI;
import com.it_academyproject.domains.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferencesRepository extends JpaRepository<Preferences, Long> {

    List<Preferences> findAll();

    @Query(value = "SELECT id, name, value, active " +
            "       FROM preferences", nativeQuery = true)
    List<DTOPreferencesI> getPreferencesParameters();

    @Query(value = "SELECT id FROM preferences WHERE name = ?1", nativeQuery = true)
    Long findPreferenceByName(@Param("name") String name);

    @Query(value = "SELECT id FROM preferences WHERE id = ?1", nativeQuery = true)
    Long findPreferenceById(@Param("id") Long id);

}
