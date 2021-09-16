## Changes

- Add Spring Security support:
  - Add DTO `RegistrationForm` and domain object `User`
  - Add repository `UserRepository`
  - Add controller `RegistrationController`
  - Add service `UserService`
  - Add config `SecurityConfig`
  - Add password constraint annotation and validator
  - Add login and registration pages
- Add test `RegistrationControllerTest`
- Rename `HomeControllerTest` to `ViewControllerTest`. Now it tests all views that don't
  have a controller
- Move common parts from html into thymeleaf fragments
- Replace `styles.css` with scss files (pom.xml plugin and sass directory)

## Available Endpoints

| HTTP method | URI path | Description |
| ----------- | -------- | ----------- |
| GET | / | Show home page |
| GET | /tacos | Show design taco form |
| POST | /tacos | Handle taco form submission |
| GET | /orders/current | Show taco order form |
| POST | /orders | Handle order form submission |
| GET | /login | Show login form |
| POST | /login | Handle login submission |
| GET | /register | Show registration form |
| POST | /register | Handle registration submission |

## Run the Application

To run the application, use the following command in a terminal window:

```
./mvnw spring-boot:run
```
