# iStudentmicroservice
Application to manage students, parents, teacher, courses and grade

## Backend
### Microservices
* Naming Service - Eureka Discovery Server
* Api Gateway - Spring Cloud Gateway
* User Service - To manage users and login
* Student Service - To manage students
* Teacher Service - To manage teacher
* Parent Service - To manage students' parents
* Course Service - To manage courses
* Grade Service - To manage grades

### Database
- Mysql, jpa, and flyway

### Documentation - Aggregated Swagger
OpenApi, Swagger

#### To access:

1. Run all services
2. Head to localhost:8080/swagger-ui.html
3. Select different services from the top right Select a definition dropdown
 
### Security
* SpringBoot Security

## Frontend
* Javascript, React, Material-UI, Formik, React Final Form

### Login Page
<img src="images/login.png" width="450" height="320"/>

### Signup Page
<img src="images/signup.png" width="450" height="320"/>

### Dashboard Page
<img src="images/mockedDashboard.png" width="450" height="320"/>

### List of Students Page
<img src="images/viewStudents.png" width="450" height="320"/>

### Create Student Page
<img src="images/createStudent.png" width="450" height="320"/>

### Update Student Page
<img src="images/updateStudent.png" width="450" height="320"/>

### Github Actions
- Go to the directory Docker and run:
  ```docker-compose up -d```
- this command will download images from docker hub and build in your docker hub
- Once every service is started you can import the postman collections
- and run commands to access the endpoints
- the init.sql is the initial database creation
