## Changes

- Replace `HomeController` with `WebConfig`
- Add domain objects: `Ingredient`, `Taco`, `Order`
- Add validation for domain objects
- Add controllers: `TacoController`, `OrderController`
- Add tests: `TacoControllerTest`, `OrderControllerTest`
- Add pages: `tacoForm.html`, `orderForm.html`
- Add stylesheet `styles.css`

## Available Endpoints

| HTTP method | URI path | Description |
| ----------- | -------- | ----------- |
| GET | / | Show home page |
| GET | /tacos | Show design taco form |
| POST | /tacos | Handle taco form submission |
| GET | /orders/current | Show taco order form |
| POST | /orders | Handle order form submission |

## Run the Application

To run the application, use the following command in a terminal window:

```
./mvnw spring-boot:run
```
