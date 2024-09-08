package controllers;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.util.List;

public class RouterController extends BaseController {

    public RouterController(List<IController> controllers) {
        // Automatically register all controllers by their base path
        for (IController controller : controllers) {
            String controllerPath = controller.getBasePath();
            registerController(controllerPath, (BaseController) controller);
        }
    }

    private void registerController(String basePath, BaseController controller) {
        controller.registerRoutesWithBasePath(basePath);
    }

    // DELETE: This method is not needed
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // BaseController logic handles the request
        super.handle(exchange);
    }
}
