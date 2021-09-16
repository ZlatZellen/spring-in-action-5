## Changes

- Change application properties:
  - Convert `application.properties` to `application.yml`
  - Add custom property `taco.orders.page-size`
  - Add description for custom property (`META-INF` directory)
  - Add `prod` profile with MySQL
- Add `OrderProperties` for extracting value from `taco.orders.page-size` property
- Add controller `BadRequestController` for checking error page via exception
- Add order list and error pages
- Replace `data.sql` with `DataConfig`

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
| GET | /exception | Throw RuntimeException |
| GET | /orders | Show latest orders |

## Run the Application

### Development Mode

To run the application, use the following command in a terminal window:

```
./mvnw spring-boot:run
```