package com.it_academyproject.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.it_academyproject.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.*;


@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MyAppUserControllerIT {


    @Autowired
    private WebTestClient client;

    @Autowired
    private MyAppUserController controller;

    // Ensure the context is creating the controller
    @Test
    public void contextLoads() {
        assertNotNull(controller);
    }

    /** Check that:
     *    -We receive a 2xx status code
     *    -Received content type is application/json
     *    -We receive NUM_STUDENTS elements
     */
    @Test
    void getAllStudentsCheckNumResults() {
        this.client
                .get()
                .uri("/api/students")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(NUM_STUDENTS);
    }

    /**
     * Check that API does not support asking for XML
     */
    @Test
    void getAllStudentsShouldNotSupportMediaTypeXML() {
        this.client
                .get()
                .uri("/api/students")
                .header(ACCEPT, APPLICATION_XML_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.NOT_ACCEPTABLE);
    }

    @Test
    void getStudentsByName() {
    }

    @Test
    void getStudentsBySurname() {
    }

    /**
     * Testing: GET /api/students/id/{id}
     * Check that given a existing student id:
     *   -We receive a 2xx status code
     *   -Received content type is application/json
     *   -Received element contains expected number of fields
     *   -Received values are those expected
     */
    @Test
    void getStudentByIdValidRequest() {
        this.client
                .get()
                .uri("/api/students/id/"+STUDENT_ID)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(SUMM_WITH_OTHERS_NUM_FIELDS)
                .jsonPath("$.id").isEqualTo(STUDENT_ID)
                .jsonPath("$.firstName").isEqualTo(STUDENT_FIRST_NAME)
                .jsonPath("$.lastName").isEqualTo(STUDENT_LAST_NAME)
                .jsonPath("$.email").isEqualTo(STUDENT_EMAIL)
                .jsonPath("$.gender").isEqualTo(STUDENT_GENDER)
                .jsonPath("$.age").isEqualTo(STUDENT_AGE)
                .jsonPath("$.portrait").isEqualTo(STUDENT_PORTRAIT)
                .jsonPath("$.seat.rowNumber").isEqualTo(STUDENT_SEAT_ROW)
                .jsonPath("$.seat.colNumber").isEqualTo(STUDENT_SEAT_COL)
                .jsonPath("$.seat.classRoom").isEqualTo(STUDENT_SEAT_ROOM)
                .jsonPath("$.courses").isNotEmpty()
                .jsonPath("$.courses.id").isEqualTo(STUDENT_COURSE_ID);
    }

    /**
     * Testing: GET /api/students/id/{id}
     * Check that given a non existing student id:
     *   -We receive a 404 NOT_FOUND status code
     */
    @Test
    void getStudentByIdWithInvalidIdReturnNotFound() {
        this.client
                .get()
                .uri("/api/students/id/" + INVALID_ID)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * Testing: GET /api/students/id/{id}
     * Check that given a non student id:
     *   -We receive a 404 NOT_FOUND status code
     */
    @Test
    void getStudentByIdWithNonStudentIdReturnNotFound() {
        this.client
                .get()
                .uri("/api/students/id/" + STUDENT_ID)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    void testGetStudentById() {
    }

    @Test
    void putStudentById() {
    }
}