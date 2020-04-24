# Integration Tests
Tests should pass according to data introduced in the DB via DML project's file active on April 2020.  
`Constants.java` file contains data corresponding to DB content filled with that file.   
## 1 - Common tests
### 1.1 - Context loads
Test: `contextLoads()`
- Ensure context is creating the controller
## 2 - MyAppUserControllerIT
### 2.1 - GET /api/students
#### 2.1.1 - Valid request test
Test: `getAllStudentsCheckNumResults()`  
Checks:
- Response status is 2xx
- Response content type is application/json
- Response number of elements is correct
#### 2.1.2 - Request asking for XML response
Test: `getAllStudentsShouldNotSupportMediaTypeXML()`  
Check:
- Response status is *NOT_ACCEPTABLE*
### 2.2 - GET /api/students/id/{id}
#### 2.2.1 - Valid student id request test
Test: `getStudentByIdValidRequest()`  
Checks:
- Response status is 2xx
- Response content type is application/json
- Response number of elements is correct
- Response content of elements is correct
 
#### 2.2.2 - Invalid id request test - FAILING TEST (23 Apr 2020)
Test: `getStudentByIdWithInvalidIdReturnNotFound()`  
Fail: response is 200 OK (expected 404 NOT FOUND)  
Check:
- Response status is *404 NOT_FOUND*

#### 2.2.3 - Non student id request test - FAILING TEST (23 Apr 2020)
Test: `getStudentByIdWithNonStudentIdReturnNotFound()`  
Fail: response is 200 OK (expected 404 NOT FOUND)  
Check:  
- Response status is *404 NOT_FOUND*

### 2.3 - PUT /api/students/id
**CAUTION:** When a PUT test fails after modifying the database, database values must manually restored to its original values. 
#### 2.3.1 - Valid request with firstName and lastName
Test: `putStudentByIdWithValidIdNameAndSurname`  
Checks:
- We receive a 2xx status code
- Received content type is application/json
- Received object is a MyAppUser object
- Stored MyAppUser is the same as before with updated firstName and lastName

#### 2.3.2 - Valid request with firstName only
Test: `putStudentByIdWithValidIdAndName`  
Checks:
- We receive a 2xx status code
- Received content type is application/json
- Received object is a MyAppUser object
- Stored MyAppUser is the same as before with updated firstName

#### 2.3.3 - Valid request with lastName only
Test: `putStudentByIdWithValidIdAndLastName`  
Checks:
- We receive a 2xx status code
- Received content type is application/json
- Received object is a MyAppUser object
- Stored MyAppUser is the same as before with updated lastName

#### 2.3.4 - Invalid request: non existing id - FAILING TEST (24 Apr 2020)
Test: `putStudentByIdWithNonExistingIdReturnNotFound`  
Fail: response is 200 OK (expected *404 NOT_FOUND*)  
Check:
- Received status is *404 NOT_FOUND*

#### 2.3.5 - Invalid request: not student id - FAILING TEST (24 Apr 2020)
Test: `putStudentByIdWithNonStudentIdReturnNotFound`  
Fail: response is 200 OK (expected *404 NOT_FOUND*)  
Check:
- Received status is *404 NOT_FOUND*


## 3 - UserExerciseControllerIT
### 3.1 - GET /api/exercises/student-id/{id}
#### 3.1.1 - Request for existing but not student id
Test: `getExercisesByStudentIdReturnNotFoundForNonStudentId()`
- Response status is *NOT_FOUND*
#### 3.1.2 - Request for non existing id
Test: `getExercisesByStudentIdReturnNotFoundForNonExistingId()`
- Response status is *NOT_FOUND*
#### 3.1.3 - Request for a valid student id
Test: `getExercisesByStudentIdReturnExpectedElementsForExistingStudentId()`  
Checks:
- Response status is 2xx
- Response content type is application/json
- Return expected number of exercises for given student

