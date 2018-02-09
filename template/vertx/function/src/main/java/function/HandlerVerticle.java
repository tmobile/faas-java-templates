package function;

import io.vertx.core.Vertx;
import io.vertx.core.http.*;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

import java.util.function.Consumer;

public class HandlerVerticle extends AbstractVerticle {
    public static void main(String[] args) {
        Consumer<Vertx> runner = vertx -> {
            try {
                vertx.deployVerticle("function.HandlerVerticle");
            } catch (Throwable t) {
                t.printStackTrace();
            }
        };
        Vertx vertx = Vertx.vertx();
        runner.accept(vertx);
    }

    @Override
    public void start() {
        HttpServerOptions serverOptions = new HttpServerOptions();
        serverOptions.setPort(8081);

        vertx.createHttpServer(serverOptions)
                .requestHandler(newRouter()::accept)
                .listen();
    }

    @Override
    public void stop() {

    }

    private Router newRouter() {
        Router router = Router.router(vertx);

        Route route = router.route("/").handler(routingContext -> {
            String payload = routingContext.getBodyAsString();
            String response = String.format("Hello, Vertx. You said: %s", payload);

            routingContext.response()
                    .write(response)
                    .putHeader("Content-Length", String.valueOf(response.length()))
                    .setStatusCode(200);

        });

        return router;
    }
}