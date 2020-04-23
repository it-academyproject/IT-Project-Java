package com.it_academyproject.controllers;

import io.netty.handler.codec.http.HttpContent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.*;


@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MyAppUserControllerIT {

    private static final int NUM_STUDENTS = 58;

    @Autowired
    private WebTestClient client;

    @Autowired
    private MyAppUserController controller;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


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
    void getAllStudentsShouldNotSupportMediaTyPeXML() {
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

    @Test
    void getStudentById() {
    }

    @Test
    void testGetStudentById() {
    }

    @Test
    void putStudentById() {
    }
}