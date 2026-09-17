# Postman Testing Guide - Enrollment & Marks Management

## Prerequisites
- Spring Boot application running on `http://localhost:8000`
- Postman installed and configured
- MySQL database configured with the `expert` schema
- Students and Courses already created in database

---

## TASK 3: STUDENT-COURSE ENROLLMENT MODULE

### Overview
Endpoints for managing student enrollments in courses with validation for student/course existence and duplicate prevention.

### Base URL: `http://localhost:8000/api/enrollments`

---

## 1. Create Enrollment

**URL:** `POST http://localhost:8000/api/enrollments`  
**Content-Type:** `application/json`

### Test Case 1.1: Valid Enrollment (Success)
```json
{
  "studentId": 1,
  "courseId": 1,
  "enrollmentDate": "2026-09-17",
  "status": "ACTIVE"
}
```
**Expected Response (Status 201):**
```json
{
  "status": "success",
  "message": "Enrollment created successfully",
  "enrollment": {
    "enrollmentId": 1,
    "studentId": 1,
    "courseId": 1,
    "enrollmentDate": "2026-09-17",
    "status": "ACTIVE"
  }
}
```

### Test Case 1.2: Invalid Student ID (Error)
```json
{
  "studentId": 999,
  "courseId": 1,
  "enrollmentDate": "2026-09-17",
  "status": "ACTIVE"
}
```
**Expected Response (Status 400):**
```json
{
  "status": "error",
  "message": "Student not found"
}
```

### Test Case 1.3: Invalid Course ID (Error)
```json
{
  "studentId": 1,
  "courseId": 999,
  "enrollmentDate": "2026-09-17",
  "status": "ACTIVE"
}
```
**Expected Response (Status 400):**
```json
{
  "status": "error",
  "message": "Course not found"
}
```

### Test Case 1.4: Duplicate Enrollment (Error)
Use the same studentId and courseId as Test Case 1.1.
```json
{
  "studentId": 1,
  "courseId": 1,
  "enrollmentDate": "2026-09-17",
  "status": "ACTIVE"
}
```
**Expected Response (Status 400):**
```json
{
  "status": "error",
  "message": "Student already enrolled"
}
```

### Test Case 1.5: Another Valid Enrollment
```json
{
  "studentId": 1,
  "courseId": 2,
  "enrollmentDate": "2026-09-17",
  "status": "ACTIVE"
}
```
**Expected Response (Status 201):** Success with enrollmentId 2

---

## 2. Get All Enrollments

**URL:** `GET http://localhost:8000/api/enrollments`  
**Method:** GET

**Expected Response (Status 200):**
```json
[
  {
    "enrollmentId": 1,
    "studentId": 1,
    "courseId": 1,
    "enrollmentDate": "2026-09-17",
    "status": "ACTIVE"
  },
  {
    "enrollmentId": 2,
    "studentId": 1,
    "courseId": 2,
    "enrollmentDate": "2026-09-17",
    "status": "ACTIVE"
  }
]
```

---

## 3. Get Enrollment by ID

**URL:** `GET http://localhost:8000/api/enrollments/{id}`  
**Method:** GET

### Test Case 3.1: Get Enrollment with ID 1
**URL:** `GET http://localhost:8000/api/enrollments/1`

**Expected Response (Status 200):**
```json
{
  "enrollmentId": 1,
  "studentId": 1,
  "courseId": 1,
  "enrollmentDate": "2026-09-17",
  "status": "ACTIVE"
}
```

### Test Case 3.2: Get Non-existent Enrollment (Error)
**URL:** `GET http://localhost:8000/api/enrollments/999`

**Expected Response (Status 404):** No body returned

---

## 4. Update Enrollment

**URL:** `PUT http://localhost:8000/api/enrollments/{id}`  
**Content-Type:** `application/json`  
**Method:** PUT

### Test Case 4.1: Update Enrollment Status
**URL:** `PUT http://localhost:8000/api/enrollments/1`

```json
{
  "studentId": 1,
  "courseId": 1,
  "enrollmentDate": "2026-09-17",
  "status": "COMPLETED"
}
```

**Expected Response (Status 200):**
```json
{
  "status": "success",
  "message": "Enrollment updated successfully",
  "enrollment": {
    "enrollmentId": 1,
    "studentId": 1,
    "courseId": 1,
    "enrollmentDate": "2026-09-17",
    "status": "COMPLETED"
  }
}
```

### Test Case 4.2: Cancel Enrollment
**URL:** `PUT http://localhost:8000/api/enrollments/2`

```json
{
  "studentId": 1,
  "courseId": 2,
  "enrollmentDate": "2026-09-17",
  "status": "CANCELLED"
}
```

**Expected Response (Status 200):** Updated successfully with CANCELLED status

### Test Case 4.3: Update Non-existent Enrollment (Error)
**URL:** `PUT http://localhost:8000/api/enrollments/999`

**Expected Response (Status 404):**
```json
{
  "status": "error",
  "message": "Enrollment not found"
}
```

---

## 5. Delete Enrollment

**URL:** `DELETE http://localhost:8000/api/enrollments/{id}`  
**Method:** DELETE

### Test Case 5.1: Delete Enrollment with ID 2
**URL:** `DELETE http://localhost:8000/api/enrollments/2`

**Expected Response (Status 200):**
```json
{
  "status": "success",
  "message": "Enrollment deleted successfully"
}
```

### Test Case 5.2: Verify Deletion - Get All Enrollments
**URL:** `GET http://localhost:8000/api/enrollments`

**Expected Response (Status 200):** Only enrollment 1 should be returned

### Test Case 5.3: Delete Non-existent Enrollment (Error)
**URL:** `DELETE http://localhost:8000/api/enrollments/999`

**Expected Response (Status 404):**
```json
{
  "status": "error",
  "message": "Enrollment not found"
}
```

---

---

## TASK 4: STUDENT MARKS MANAGEMENT MODULE

### Overview
Endpoints for recording and managing student marks with validation for student/course existence and mark range validation.

### Base URL: `http://localhost:8000/api/marks`

---

## 1. Create Marks Record

**URL:** `POST http://localhost:8000/api/marks`  
**Content-Type:** `application/json`

### Test Case 1.1: Valid Marks Record (Success)
```json
{
  "studentId": 1,
  "courseId": 1,
  "examName": "Internal 1",
  "marks": 78,
  "totalMarks": 100
}
```
**Expected Response (Status 201):**
```json
{
  "status": "success",
  "message": "Marks recorded successfully",
  "marks": {
    "markId": 1,
    "studentId": 1,
    "courseId": 1,
    "examName": "Internal 1",
    "marks": 78,
    "totalMarks": 100
  }
}
```

### Test Case 1.2: Invalid Student ID (Error)
```json
{
  "studentId": 999,
  "courseId": 1,
  "examName": "Internal 1",
  "marks": 75,
  "totalMarks": 100
}
```
**Expected Response (Status 400):**
```json
{
  "status": "error",
  "message": "Student not found"
}
```

### Test Case 1.3: Invalid Course ID (Error)
```json
{
  "studentId": 1,
  "courseId": 999,
  "examName": "Internal 1",
  "marks": 75,
  "totalMarks": 100
}
```
**Expected Response (Status 400):**
```json
{
  "status": "error",
  "message": "Course not found"
}
```

### Test Case 1.4: Negative Marks (Error)
```json
{
  "studentId": 1,
  "courseId": 1,
  "examName": "Internal 2",
  "marks": -5,
  "totalMarks": 100
}
```
**Expected Response (Status 400):**
```json
{
  "status": "error",
  "message": "Marks cannot be negative"
}
```

### Test Case 1.5: Marks Greater Than Total (Error)
```json
{
  "studentId": 1,
  "courseId": 1,
  "examName": "Midterm",
  "marks": 105,
  "totalMarks": 100
}
```
**Expected Response (Status 400):**
```json
{
  "status": "error",
  "message": "Marks cannot be greater than total marks"
}
```

### Test Case 1.6: Another Valid Marks Record
```json
{
  "studentId": 1,
  "courseId": 1,
  "examName": "Midterm",
  "marks": 85,
  "totalMarks": 100
}
```
**Expected Response (Status 201):** Success with markId 2

### Test Case 1.7: More Marks Records
```json
{
  "studentId": 1,
  "courseId": 2,
  "examName": "Internal 1",
  "marks": 92,
  "totalMarks": 100
}
```
**Expected Response (Status 201):** Success with markId 3

---

## 2. Get All Marks Records

**URL:** `GET http://localhost:8000/api/marks`  
**Method:** GET

**Expected Response (Status 200):**
```json
[
  {
    "markId": 1,
    "studentId": 1,
    "courseId": 1,
    "examName": "Internal 1",
    "marks": 78,
    "totalMarks": 100
  },
  {
    "markId": 2,
    "studentId": 1,
    "courseId": 1,
    "examName": "Midterm",
    "marks": 85,
    "totalMarks": 100
  },
  {
    "markId": 3,
    "studentId": 1,
    "courseId": 2,
    "examName": "Internal 1",
    "marks": 92,
    "totalMarks": 100
  }
]
```

---

## 3. Get Marks by ID

**URL:** `GET http://localhost:8000/api/marks/{id}`  
**Method:** GET

### Test Case 3.1: Get Marks with ID 1
**URL:** `GET http://localhost:8000/api/marks/1`

**Expected Response (Status 200):**
```json
{
  "markId": 1,
  "studentId": 1,
  "courseId": 1,
  "examName": "Internal 1",
  "marks": 78,
  "totalMarks": 100
}
```

### Test Case 3.2: Get Marks with ID 2
**URL:** `GET http://localhost:8000/api/marks/2`

**Expected Response (Status 200):**
```json
{
  "markId": 2,
  "studentId": 1,
  "courseId": 1,
  "examName": "Midterm",
  "marks": 85,
  "totalMarks": 100
}
```

### Test Case 3.3: Get Non-existent Marks (Error)
**URL:** `GET http://localhost:8000/api/marks/999`

**Expected Response (Status 404):** No body returned

---

## 4. Update Marks Record

**URL:** `PUT http://localhost:8000/api/marks/{id}`  
**Content-Type:** `application/json`  
**Method:** PUT

### Test Case 4.1: Update Marks Value
**URL:** `PUT http://localhost:8000/api/marks/1`

```json
{
  "studentId": 1,
  "courseId": 1,
  "examName": "Internal 1",
  "marks": 82,
  "totalMarks": 100
}
```

**Expected Response (Status 200):**
```json
{
  "status": "success",
  "message": "Marks updated successfully",
  "marks": {
    "markId": 1,
    "studentId": 1,
    "courseId": 1,
    "examName": "Internal 1",
    "marks": 82,
    "totalMarks": 100
  }
}
```

### Test Case 4.2: Invalid Update - Negative Marks (Error)
**URL:** `PUT http://localhost:8000/api/marks/2`

```json
{
  "studentId": 1,
  "courseId": 1,
  "examName": "Midterm",
  "marks": -10,
  "totalMarks": 100
}
```

**Expected Response (Status 400):**
```json
{
  "status": "error",
  "message": "Marks cannot be negative"
}
```

### Test Case 4.3: Invalid Update - Marks Greater Than Total (Error)
**URL:** `PUT http://localhost:8000/api/marks/3`

```json
{
  "studentId": 1,
  "courseId": 2,
  "examName": "Internal 1",
  "marks": 150,
  "totalMarks": 100
}
```

**Expected Response (Status 400):**
```json
{
  "status": "error",
  "message": "Marks cannot be greater than total marks"
}
```

### Test Case 4.4: Update Non-existent Marks (Error)
**URL:** `PUT http://localhost:8000/api/marks/999`

**Expected Response (Status 404):**
```json
{
  "status": "error",
  "message": "Marks record not found"
}
```

---

## 5. Delete Marks Record

**URL:** `DELETE http://localhost:8000/api/marks/{id}`  
**Method:** DELETE

### Test Case 5.1: Delete Marks with ID 3
**URL:** `DELETE http://localhost:8000/api/marks/3`

**Expected Response (Status 200):**
```json
{
  "status": "success",
  "message": "Marks record deleted successfully"
}
```

### Test Case 5.2: Verify Deletion - Get All Marks
**URL:** `GET http://localhost:8000/api/marks`

**Expected Response (Status 200):** Only marks 1 and 2 should be returned

### Test Case 5.3: Delete Non-existent Marks (Error)
**URL:** `DELETE http://localhost:8000/api/marks/999`

**Expected Response (Status 404):**
```json
{
  "status": "error",
  "message": "Marks record not found"
}
```

---

## Summary of All New Endpoints

### Enrollment Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/enrollments` | Create new enrollment |
| GET | `/api/enrollments` | Get all enrollments |
| GET | `/api/enrollments/{id}` | Get enrollment by ID |
| PUT | `/api/enrollments/{id}` | Update enrollment |
| DELETE | `/api/enrollments/{id}` | Delete enrollment |

### Marks Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/marks` | Create marks record |
| GET | `/api/marks` | Get all marks records |
| GET | `/api/marks/{id}` | Get marks by ID |
| PUT | `/api/marks/{id}` | Update marks record |
| DELETE | `/api/marks/{id}` | Delete marks record |

---

## Testing Order

**Recommended testing order:**

1. **Enrollment Module:**
   - Test Case 1.1 (Valid enrollment)
   - Test Case 1.2-1.4 (Validation checks)
   - Test Case 1.5 (Another enrollment)
   - Get all enrollments
   - Get by ID
   - Update status tests
   - Delete tests

2. **Marks Module:**
   - Test Case 1.1 (Valid marks)
   - Test Case 1.2-1.5 (Validation checks)
   - Test Case 1.6-1.7 (Additional records)
   - Get all marks
   - Get by ID
   - Update tests with validation
   - Delete tests

---

## Notes
- All responses include status and message fields for clarity
- Validation is performed on both POST and PUT operations
- Enrollment validates student, course existence and no duplicates
- Marks validates student, course existence, and mark ranges
- Proper HTTP status codes are used (201 for create, 400 for validation errors, 404 for not found)
- Base URL: `http://localhost:8000`
