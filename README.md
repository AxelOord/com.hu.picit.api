# Picit API for Picmin (ADSD Phase 1 Project)

This API is developed as part of the Associate Degree Software Development (ADSD) Phase 1 project. It is designed for the fictive company **Picmin** and will be used for their upcoming app, **Picit**.

## Prerequisites

- JDK 8 or higher

## Setup and Run

1. Clone the repository:
    ```bash
    git clone https://github.com/AxelOord/com.hu.picit.api
    ```
2. Compile the project:
    ```bash
    javac -cp . App.java
    ```
3. Run the server with the desired port (optional):
    ```bash
    java Api <port>
    ```
    If no port is specified, the server will default to port `8080`.

The API will be available at `http://localhost:<port>/api` (replace `<port>` with the actual port number).

## Structure

- `App.java`: Main entry point that sets up the HTTP server.
- `RouterController`: Handles routing to different controllers.

## Purpose

The **Picit API** will serve as the backend for the **Picit** app, providing necessary endpoints to manage app functionalities for **Picmin**.

## Creating a Controller

To create a controller that handles HTTP routes, you can define a class that extends from the `BaseController` and use annotations for each HTTP method (GET, POST, PUT, DELETE). This ensures that each method will be correctly registered to the context.

The name of the controller should always be a verb and end with controller, I.E. `VerbController`

Here is an example of a `ResourceController` class with annotations for different HTTP methods:

```java
package controllers;

public class ResourceController extends BaseController {

    // Handles GET requests for all resources
    @HttpGet()
    private void getResources(HttpExchange exchange, Map<String, String> params) throws IOException {
        sendResponse(exchange, "All resources");
    }

    // Handles GET requests for a specific resource by ID
    @HttpGet("/{id}")
    private void getResource(HttpExchange exchange, Map<String, String> params) throws IOException {
        sendResponse(exchange, "Resource with id: " + params.get("id"));
    }

    // Handles POST requests to add a new resource
    @HttpPost()
    private void addResource(HttpExchange exchange, Map<String, String> params) throws IOException {
        sendResponse(exchange, "Resource added");
    }

    // Handles PUT requests to update a specific resource by ID
    @HttpPut("/{id}")
    private void updateResource(HttpExchange exchange, Map<String, String> params) throws IOException {
        sendResponse(exchange, "Resource with id: " + params.get("id") + " updated");
    }

    // Handles DELETE requests to remove a specific resource by ID
    @HttpDelete("/{id}")
    private void deleteResource(HttpExchange exchange, Map<String, String> params) throws IOException {
        sendResponse(exchange, "Resource with id: " + params.get("id") + " deleted");
    }
}
```

### Handling Route Parameters with the RouteHandler Interface

Each method in the controller must follow the signature defined by the `RouteHandler` functional interface:

```java
@FunctionalInterface
public interface RouteHandler {
    void handle(HttpExchange exchange, Map<String, String> params) throws IOException;
}
```

## Customization

- Add additional controllers to the `RouterController` by listing them in the constructor (these will be automaticly registered as long as the controller inherits `BaseController`):
    ```java
    new RouterController(Arrays.asList(
        new ResourceController(),
        new YourNewController()
    ));
    ```

## License

This project is open-source and available under the MIT License.