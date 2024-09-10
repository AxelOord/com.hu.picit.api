package controllers;

import com.sun.net.httpserver.HttpExchange;

import annotations.HttpDelete;
import annotations.HttpGet;
import annotations.HttpPost;
import annotations.HttpPut;

import java.io.IOException;
import java.util.Map;

public class ResourceController extends BaseController {
    @HttpGet()
    private void getResources(HttpExchange exchange, Map<String, String> params) throws IOException {
        sendResponse(exchange, "All resources");
    }

    @HttpGet("/{id}")
    private void getresource(HttpExchange exchange, Map<String, String> params) throws IOException {
        sendResponse(exchange, "Resource with id: "  + params.get("id"));
    }

    @HttpPost()
    private void addResource(HttpExchange exchange, Map<String, String> params) throws IOException {
        sendResponse(exchange, "Resource added");
    }

    @HttpPut("/{id}")
    private void updateResource(HttpExchange exchange, Map<String, String> params) throws IOException {
        sendResponse(exchange, "Resource with id: " +  params.get("id") + " updated");
    }

    @HttpDelete("/{id}")
    private void deleteResource(HttpExchange exchange, Map<String, String> params) throws IOException {
        sendResponse(exchange, "Resource with id: " +  params.get("id") + " deleted");
    }
}