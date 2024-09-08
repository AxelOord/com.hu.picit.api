# Picit API for Picmin (ADSD Phase 1 Project)

This API is developed as part of the Associate Degree Software Development (ADSD) Phase 1 project. It is designed for the fictive company **Picmin** and will be used for their upcoming app, **Picit**.

## Prerequisites

- JDK 8 or higher

## Setup and Run

1. Clone the repository:
    ```bash
    git clone <repository-url>
    ```
2. Compile the project:
    ```bash
    javac -cp . App.java
    ```
3. Run the server with the desired port (optional):
    ```bash
    java App <port>
    ```
    If no port is specified, the server will default to port `8080`.

The API will be available at `http://localhost:<port>/api` (replace `<port>` with the actual port number).

## Structure

- `App.java`: Main entry point that sets up the HTTP server.
- `RouterController`: Handles routing to different controllers.

## Purpose

The **Picit API** will serve as the backend for the **Picit** app, providing necessary endpoints to manage app functionalities for **Picmin**.

## Customization

- Add additional controllers to the `RouterController` by listing them in the constructor:
    ```java
    new RouterController(Arrays.asList(
        new ResourceController(),
        new YourNewController()
    ));
    ```

## License

This project is open-source and available under the MIT License.