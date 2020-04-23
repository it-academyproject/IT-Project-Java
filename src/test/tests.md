# Integration Tests
Tests should pass according to data introduced in the DB via DML project's file active on April 2020.  
Data corresponding to DB content in class `Constants`.   
## Common tests
### Context loads
Test: `contextLoads()`
- Ensure context is creating the controller
## MyAppUserControllerIT
### GET /api/students
#### Valid request test
Test: `getAllStudentsCheckNumResults()`
- Response status is 2xx
- Response content type is application/json
- Response number of elements is correct
#### Request asking for XML response
Test: `getAllStudentsShouldNotSupportMediaTypeXML()`
- Response status is *NOT_ACCEPTABLE*
### GET /api/students/id/{id}
#### Valid student id request test - FAILING TEST (23 Apr 2020)
Test: `getStudentByIdValidRequest()`
- Response status is 2xx
- Response content type is application/json
- Response number of elements is correct
- Response content of elements is correct
  - Fail: 'course' field is null
#### Invalid id request test - FAILING TEST (23 Apr 2020)
Test: `getStudentByIdWithInvalidIdReturnNotFound()`
- Response status is *NOT_FOUND*
  - Fail: response is 200 OK
#### Non student id request test - FAILING TEST (23 Apr 2020)
Test: `getStudentByIdWithNonStudentIdReturnNotFound()`
- Response status is *NOT_FOUND*
  - Fail: response is 200 OK
## UserExerciseControllerIT
### GET /api/exercises/student-id/{id}
#### Request for existing but not student id
Test: `getExercisesByStudentIdReturnNotFoundForNonStudentId()`
- Response status is *NOT_FOUND*
#### Request for non existing id
Test: `getExercisesByStudentIdReturnNotFoundForNonExistingId()`
- Response status is *NOT_FOUND*
#### Request for a valid student id
Test: `getExercisesByStudentIdReturnExpectedElementsForExistingStudentId()`
- Response status is 2xx
- Response content type is application/json
- Return expected number of exercises for given student

