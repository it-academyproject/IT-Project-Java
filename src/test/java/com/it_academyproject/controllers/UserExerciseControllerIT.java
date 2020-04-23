package com.it_academyproject.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.it_academyproject.Constants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserExerciseControllerIT {

    @Autowired
    WebTestClient client;

    @Autowired
    UserExerciseController controller;

    // Ensure the context is creating the controller
    @Test
    public void contextLoads() {
        assertNotNull(controller);
    }

    private static final String BASE_URI = "/api/exercises";

    @Test
    void getExerciseStudentByItinerary() {
    }

    @Test
    void getAllExerciseswithStudents() {
    }

    /**
     * Check that when you introduce a non student but existing id you receive a NOT_FOUND status
     */
    @Test
    void getExercisesByStudentIdReturnNotFoundForNonStudentId() {
        this.client
                .get()
                .uri(BASE_URI+"/student-id/"+TEACHER_ID)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(NOT_FOUND);
    }

    /**
     * Check that when you introduce a non existing id you receive a NOT_FOUND status
     */
    @Test
    void getExercisesByStudentIdReturnNotFoundForNonExistingId() {
        this.client
                .get()
                .uri(BASE_URI+"/student-id/"+INVALID_ID)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(NOT_FOUND);
    }

    /**
     * Check that when you introduce a valid student id:
     *   -You receive a 2xx status code
     *   -You receive the expected num of elements (exercises)
     */
    @Test
    void getExercisesByStudentIdReturnExpectedElementsForExistingStudentId() {
        this.client
                .get()
                .uri(BASE_URI+"/student-id/"+STUDENT_ID)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(STUDENT_ID_NUMBER_OF_EXERCISES);
    }


    @Test
    void setUserExerciseStatusData() {
    }
}