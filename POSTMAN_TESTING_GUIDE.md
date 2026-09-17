# Postman Testing Guide - Authentication & Course Management

## Prerequisites
- Spring Boot application running on `http://localhost:8000`
- Postman installed and configured
- MySQL database configured with the `expert` schema

---

## TASK 1: AUTHENTICATION MODULE

### 1. Register Endpoint
**URL:** `POST http://localhost:8000/auth/register`  
**Content-Type:** `application/json`

#### Test Case 1.1: Register New Student (Success)
```json
{
  "id": 1,
  "name": "John Doe",
  "department": "Computer Science",
  "age": 20,
  "username": "johndoe",
  "password": "password123"
}
```
**Expected Response (Status 200):**
```json
{
  "status": "success",
  "message": "Student registered successfully"
}
```

#### Test Case 1.2: Register with Duplicate Username (Error)
Use the same request as 1.1 again.  
**Expected Response (Status 200):**
```json
{
  "status": "error",
  "message": "Username already exists"
}
```

#### Test Case 1.3: Register Another Student (Success)
```json
{
  "id": 2,
  "name": "Jane Smith",
  "department": "Electrical Engineering",
  "age": 21,
  "username": "janesmith",
  "password": "securepass456"
}
```
**Expected Response (Status 200):**
```json
{
  "status": "success",
  "message": "Student registered successfully"
}
```

---

### 2. Login Endpoint
**URL:** `POST http://localhost:8000/auth/login`  
**Content-Type:** `application/json`

#### Test Case 2.1: Login with Correct Credentials (Success)
```json
{
  "username": "johndoe",
  "password": "password123"
}
```
**Expected Response (Status 200):**
```json
{
  "status": "success",
  "message": "Login Successful",
  "studentName": "John Doe"
}
```

#### Test Case 2.2: Login with Non-existent Username (Error)
```json
{
  "username": "invaliduser",
  "password": "anypassword"
}
```
**Expected Response (Status 200):**
```json
{
  "status": "error",
  "message": "Username not found"
}
```

#### Test Case 2.3: Login with Correct Username but Wrong Password (Error)
```json
{
  "username": "johndoe",
  "password": "wrongpassword"
}
```
**Expected Response (Status 200):**
```json
{
  "status": "error",
  "message": "Invalid Password"
}
```

#### Test Case 2.4: Login with Another Student (Success)
```json
{
  "username": "janesmith",
  "password": "securepass456"
}
```
**Expected Response (Status 200):**
```json
{
  "status": "success",
  "message": "Login Successful",
  "studentName": "Jane Smith"
}
```

---

## TASK 2: COURSE MANAGEMENT MODULE

### 1. Create Course
**URL:** `POST http://localhost:8000/api/courses`  
**Content-Type:** `application/json`

#### Test Case 1.1: Create First Course (Success)
```json
{
  "courseName": "Data Structures",
  "department": "Computer Science",
  "duration": 4,
  "fees": 50000
}
```
**Expected Response (Status 201):**
```json
{
  "courseId": 1,
  "courseName": "Data Structures",
  "department": "Computer Science",
  "duration": 4,
  "fees": 50000
}
```

#### Test Case 1.2: Create Second Course
```json
{
  "courseName": "Circuit Analysis",
  "department": "Electrical Engineering",
  "duration": 3,
  "fees": 45000
}
```
**Expected Response (Status 201):**
```json
{
  "courseId": 2,
  "courseName": "Circuit Analysis",
  "department": "Electrical Engineering",
  "duration": 3,
  "fees": 45000
}
```

#### Test Case 1.3: Create Third Course
```json
{
  "courseName": "Database Management",
  "department": "Computer Science",
  "duration": 3,
  "fees": 55000
}
```
**Expected Response (Status 201):**
```json
{
  "courseId": 3,
  "courseName": "Database Management",
  "department": "Computer Science",
  "duration": 3,
  "fees": 55000
}
```

---

### 2. Get All Courses
**URL:** `GET http://localhost:8000/api/courses`  
**Method:** GET

**Expected Response (Status 200):**
```json
[
  {
    "courseId": 1,
    "courseName": "Data Structures",
    "department": "Computer Science",
    "duration": 4,
    "fees": 50000
  },
  {
    "courseId": 2,
    "courseName": "Circuit Analysis",
    "department": "Electrical Engineering",
    "duration": 3,
    "fees": 45000
  },
  {
    "courseId": 3,
    "courseName": "Database Management",
    "department": "Computer Science",
    "duration": 3,
    "fees": 55000
  }
]
```

---

### 3. Get Course by ID
**URL:** `GET http://localhost:8000/api/courses/{id}`  
**Method:** GET

#### Test Case 3.1: Get Course with ID 1
**URL:** `GET http://localhost:8000/api/courses/1`

**Expected Response (Status 200):**
```json
{
  "courseId": 1,
  "courseName": "Data Structures",
  "department": "Computer Science",
  "duration": 4,
  "fees": 50000
}
```

#### Test Case 3.2: Get Course with ID 2
**URL:** `GET http://localhost:8000/api/courses/2`

**Expected Response (Status 200):**
```json
{
  "courseId": 2,
  "courseName": "Circuit Analysis",
  "department": "Electrical Engineering",
  "duration": 3,
  "fees": 45000
}
```

#### Test Case 3.3: Get Non-existent Course (Error)
**URL:** `GET http://localhost:8000/api/courses/999`

**Expected Response (Status 404):**
No body returned

---

### 4. Update Course
**URL:** `PUT http://localhost:8000/api/courses/{id}`  
**Content-Type:** `application/json`  
**Method:** PUT

#### Test Case 4.1: Update Course with ID 1
**URL:** `PUT http://localhost:8000/api/courses/1`

```json
{
  "courseName": "Advanced Data Structures",
  "department": "Computer Science",
  "duration": 5,
  "fees": 60000
}
```

**Expected Response (Status 200):**
```json
{
  "courseId": 1,
  "courseName": "Advanced Data Structures",
  "department": "Computer Science",
  "duration": 5,
  "fees": 60000
}
```

#### Test Case 4.2: Update Course with ID 3
**URL:** `PUT http://localhost:8000/api/courses/3`

```json
{
  "courseName": "Advanced Database Management",
  "department": "Computer Science",
  "duration": 4,
  "fees": 65000
}
```

**Expected Response (Status 200):**
```json
{
  "courseId": 3,
  "courseName": "Advanced Database Management",
  "department": "Computer Science",
  "duration": 4,
  "fees": 65000
}
```

#### Test Case 4.3: Update Non-existent Course (Error)
**URL:** `PUT http://localhost:8000/api/courses/999`

```json
{
  "courseName": "Non-existent Course",
  "department": "Unknown",
  "duration": 1,
  "fees": 1000
}
```

**Expected Response (Status 404):**
No body returned

---

### 5. Delete Course
**URL:** `DELETE http://localhost:8000/api/courses/{id}`  
**Method:** DELETE

#### Test Case 5.1: Delete Course with ID 2
**URL:** `DELETE http://localhost:8000/api/courses/2`

**Expected Response (Status 204):**
No body returned

#### Test Case 5.2: Verify Deletion - Get All Courses
**URL:** `GET http://localhost:8000/api/courses`

**Expected Response (Status 200):** Only courses 1 and 3 should be returned
```json
[
  {
    "courseId": 1,
    "courseName": "Advanced Data Structures",
    "department": "Computer Science",
    "duration": 5,
    "fees": 60000
  },
  {
    "courseId": 3,
    "courseName": "Advanced Database Management",
    "department": "Computer Science",
    "duration": 4,
    "fees": 65000
  }
]
```

#### Test Case 5.3: Delete Non-existent Course (Error)
**URL:** `DELETE http://localhost:8000/api/courses/999`

**Expected Response (Status 404):**
No body returned

---

## Summary of All Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register a new student |
| POST | `/auth/login` | Login with username and password |
| POST | `/api/courses` | Create a new course |
| GET | `/api/courses` | Get all courses |
| GET | `/api/courses/{id}` | Get course by ID |
| PUT | `/api/courses/{id}` | Update a course |
| DELETE | `/api/courses/{id}` | Delete a course |

---

## Notes
- All dates in responses are in ISO 8601 format
- Error responses include status and message fields
- Ensure your database is running before testing
- The application must be running on `http://localhost:8000`
- Course endpoints use **plural** path: `/api/courses` (not `/api/course`)
