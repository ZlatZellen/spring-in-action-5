## Changes

- Update tests: `TacoControllerTest`, `OrderControllerTest`
- Add repositories: `IngredientRepository`, `TacoRepository`, `OrderRepository`
- Add repositories implementations: `JdbcIngredientRepository`, `JdbcTacoRepository`, `JdbcOrderRepository`
- Add datasource scripts `schema.sql` and `data.sql`
- Add common ancestor `BaseEntity` for `Order` and `Taco`

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
