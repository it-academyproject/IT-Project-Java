package com.it_academyproject.controllers;

import com.it_academyproject.domains.Course;
import com.it_academyproject.domains.MyAppUser;
import com.it_academyproject.services.MyAppUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.it_academyproject.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.*;


@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Transactional
class MyAppUserControllerIT {

    @Autowired
    private WebTestClient client;
    @Autowired
    private MyAppUserController controller;
    @Autowired
    private MyAppUserService userService;

    private static final String TEST_NAME = "Test Name";
    private static final String TEST_SURNAME = "Test Surname";

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
        EntityExchangeResult<MyAppUser> response =
                client.get()
                        .uri("/api/students/id/"+STUDENT_ID)
                        .header(ACCEPT, APPLICATION_JSON_VALUE)
                        .exchange()
                        .expectStatus().is2xxSuccessful()
                        .expectHeader().contentType(APPLICATION_JSON)
                        .expectBody(MyAppUser.class)
                        .returnResult();

        checkTestStudentValues(response.getResponseBody());
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

    /**
     * Testing: PUT /api/students/id
     * Check that given request body with a valid student id, firstName and lastName:
     *   -We receive a 2xx status code
     *   -Received content type is application/json
     *   -Received object is a MyAppUser
     *   -Stored MyAppUser is the same as before with updated firstName and lastName
     *   TODO: When the test fails, the updated element in the DB won't return to its original value. Fix this.
     */
    @Test
    @Transactional
    void putStudentByIdWithValidIdNameAndSurname() {
        // Get stored user with given id from DB
        MyAppUser originalUser = userService.getById(STUDENT_ID);
        // Create new user with id, name and surname
        MyAppUser requestUser = new MyAppUser();
        requestUser.setId(STUDENT_ID);
        requestUser.setFirstName(TEST_NAME);
        requestUser.setLastName(TEST_SURNAME);

        // Make the call to the API with created user, checking response header and status
        EntityExchangeResult<MyAppUser> response =
        client.put()
                .uri("/api/students/id/")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .bodyValue(requestUser)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(MyAppUser.class)
                .returnResult();

        // Get modified user from DB
        MyAppUser modifiedUser = userService.getById(STUDENT_ID);
        // Assign given values to original user
        originalUser.setFirstName(TEST_NAME);
        originalUser.setLastName(TEST_SURNAME);
        // Compare original user with changed values to modified stored user
        assertEquals(originalUser, modifiedUser);

        // Ensure that returned MyAppUser's values are as stored in DB's MyAppUser
        checkSummaryWithOthersView(modifiedUser, response.getResponseBody());

        // Check that returned values are the expected
        checkTestStudentValues(response.getResponseBody(), TEST_NAME, TEST_SURNAME);

        // Let the stored student as it was before the test
        requestUser.setFirstName(STUDENT_FIRST_NAME);
        requestUser.setLastName(STUDENT_LAST_NAME);
        // TODO Try to improve this: calling userService.editStudent doesn't persist changes in the DB
        // userService.editStudent(originalUser);
        // Necessary due to problems to persist DB using MyAppUserService
        client.put()
                .uri("/api/students/id/")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .bodyValue(requestUser)
                .exchange();
    }

    /**
     * Testing: PUT /api/students/id
     * Check that given request body with a valid student id and firstName:
     *   -We receive a 2xx status code
     *   -Received content type is application/json
     *   -Received object is a MyAppUser
     *   -Stored MyAppUser is the same as before with updated firstName
     *   TODO: When the test fails, the updated element in the DB won't return to its original value. Fix this.
     */
    @Test
    @Transactional
    void putStudentByIdWithValidIdAndName() {
        // Get stored user with given id from DB
        MyAppUser originalUser = userService.getById(STUDENT_ID);
        // Create new user with id and first name
        MyAppUser requestUser = new MyAppUser();
        requestUser.setId(STUDENT_ID);
        requestUser.setFirstName(TEST_NAME);

        // Make the call to the API with created user, checking response header and status
        EntityExchangeResult<MyAppUser> response =
        client.put()
                .uri("/api/students/id/")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .bodyValue(requestUser)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(MyAppUser.class)
                .returnResult();

        // Get modified user from DB
        MyAppUser modifiedUser = userService.getById(STUDENT_ID);
        // Assign given values to original user
        originalUser.setFirstName(TEST_NAME);
        // Compare original user with changed values to modified stored user
        assertEquals(originalUser, modifiedUser);

        // Ensure that returned MyAppUser's values are as stored in DB's MyAppUser
        checkSummaryWithOthersView(modifiedUser, response.getResponseBody());

        // Check that returned values are the expected
        checkTestStudentValues(response.getResponseBody(), TEST_NAME);

        // Let the stored student as it was before the test
        requestUser.setFirstName(STUDENT_FIRST_NAME);
        // TODO Try to improve this: calling userService.editStudent doesn't persist changes in the DB
        // userService.editStudent(originalUser);
        // Necessary due to problems to persist DB using MyAppUserService
        client.put()
                .uri("/api/students/id/")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .bodyValue(requestUser)
                .exchange();
    }

    /**
     * Testing: PUT /api/students/id
     * Check that given request body with a valid student id and firstName:
     *   -We receive a 2xx status code
     *   -Received content type is application/json
     *   -Received object is a MyAppUser
     *   -Stored MyAppUser is the same as before with updated firstName
     *   TODO: When the test fails, the updated element in the DB won't return to its original value. Fix this.
     */
    @Test
    @Transactional
    void putStudentByIdWithValidIdAndLastName() {
        // Get stored user with given id from DB
        MyAppUser originalUser = userService.getById(STUDENT_ID);
        // Create new user with id and first name
        MyAppUser requestUser = new MyAppUser();
        requestUser.setId(STUDENT_ID);
        requestUser.setLastName(TEST_SURNAME);

        // Make the call to the API with created user, checking response header and status
        EntityExchangeResult<MyAppUser> response =
        client.put()
                .uri("/api/students/id/")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .bodyValue(requestUser)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(MyAppUser.class)
                .returnResult();

        // Get modified user from DB
        MyAppUser modifiedUser = userService.getById(STUDENT_ID);
        // Assign given values to original user
        originalUser.setLastName(TEST_SURNAME);
        // Compare original user with changed values to modified stored user
        assertEquals(originalUser, modifiedUser);

        // Ensure that returned MyAppUser's values are as stored in DB's MyAppUser
        checkSummaryWithOthersView(modifiedUser, response.getResponseBody());

        // Check that returned values are the expected
        checkTestStudentValues(response.getResponseBody(), null, TEST_SURNAME);

        // Let the stored student as it was before the test
        requestUser.setLastName(STUDENT_LAST_NAME);
        // TODO Try to improve this: calling userService.editStudent doesn't persist changes in the DB
        // userService.editStudent(originalUser);
        // Necessary due to problems to persist DB using MyAppUserService
        client.put()
                .uri("/api/students/id/")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .bodyValue(requestUser)
                .exchange();
    }


    /**
     * Testing: PUT /api/students/id
     * Check that given a student with non existing id:
     *   -We receive a 404 NOT_FOUND status code
     */
    @Test
    void putStudentByIdWithNonExistingIdReturnNotFound() {
        MyAppUser user = new MyAppUser();
        user.setId(INVALID_ID);
        this.client
                .put()
                .uri("/api/students/id/")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .bodyValue(user)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * Testing: PUT /api/students/id
     * Check that given a user with a non student id:
     *   -We receive a 404 NOT_FOUND status code
     */
    @Test
    void putStudentByIdWithNonStudentIdReturnNotFound() {
        MyAppUser user = new MyAppUser();
        user.setId(TEACHER_ID);
        this.client
                .put()
                .uri("/api/students/id/")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .bodyValue(user)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * Check that the given student contains the expected values, compared to those stored in Constants.java
     * firstName and lastName can be passed as params via args
     * @param student MyAppUser student to check
     * @param args {firstName, lastName} Optional params to compare to
     */
    private void checkTestStudentValues(MyAppUser student, String... args) {
        assertEquals(STUDENT_ID, student.getId());
        assertEquals((args.length!=0 && args[0]!=null)?args[0]:STUDENT_FIRST_NAME, student.getFirstName());
        assertEquals((args.length==2)?args[1]:STUDENT_LAST_NAME, student.getLastName());
        assertEquals(STUDENT_EMAIL, student.getEmail());
        assertEquals(STUDENT_GENDER, student.getGender());
        assertEquals(STUDENT_AGE, student.getAge());
        assertEquals(STUDENT_PORTRAIT, student.getPortrait());
        assertEquals(STUDENT_SEAT_ROW, student.getSeat().getRowNumber());
        assertEquals(STUDENT_SEAT_COL, student.getSeat().getColNumber());
        assertEquals(STUDENT_SEAT_ROOM, student.getSeat().getClassRoom());
        assertEquals(STUDENT_COURSE_ID, student.getCourses().get(0).getId());
    }

    /**
     * Given two MyAppUser, check that fields values returned in view View.SummaryWithOthers match
     * @param expected MyAppUser stored in the database
     * @param actual MyAppUser returned from API
     */
    private void checkSummaryWithOthersView(MyAppUser expected, MyAppUser actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getGender(), actual.getGender());
        assertEquals(expected.getAge(), actual.getAge());
        assertEquals(expected.getPortrait(), actual.getPortrait());
        assertEquals(expected.getSeat(), actual.getSeat());
        assertEquals(expected.getCourses().size(), actual.getCourses().size());
        if (actual.getCourses().size()>0)
            // Check Course fields included in View.SummaryWithOthers, assuming courses are in the same order
            for (int i=0; i<actual.getCourses().size(); i++) {
                assertEquals(expected.getCourses().get(i).getId(), actual.getCourses().get(i).getId());
                assertEquals(expected.getCourses().get(i).getEndDate(), actual.getCourses().get(i).getEndDate());
                // Check Itinerary fields included in View.Summary.WithOthers
                assertEquals(expected.getCourses().get(i).getItinerary().getId(),
                        actual.getCourses().get(i).getItinerary().getId());
                assertEquals(expected.getCourses().get(i).getItinerary().getName(),
                        actual.getCourses().get(i).getItinerary().getName());
            }
    }
}