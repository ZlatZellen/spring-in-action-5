## Changes

- Update tests: `TacoControllerTest`, `OrderControllerTest`
- Add repositories (Spring JPA): `IngredientRepository`, `TacoRepository`, `OrderRepository`
- Add converter `IdToIngredientConverter`
- Add datasource script `data.sql`
- Add common ancestor `BaseEntity` for `Order` and `Taco`
- Address data are moved to a separate embeddable class `Address`

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
