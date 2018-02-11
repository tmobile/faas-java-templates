package function;

import io.vertx.core.Vertx;
import io.vertx.core.http.*;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.function.Consumer;


public class HandlerVerticle extends AbstractVerticle {
    private static Logger LOG = LoggerFactory.getLogger(HandlerVerticle.class);

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

        router.route().handler(BodyHandler.create());
        router.route().method(HttpMethod.POST).path("/").handler(routingContext -> {
            String payload = routingContext.getBodyAsString();
            LOG.info(payload);
            String response = String.format("Hello, Vertx. You said: %s", payload);
            routingContext.response()
                    .putHeader("Content-Length", String.valueOf(response.length()))
                    .setStatusCode(200)
                    .write(response);

            //end the response
            routingContext.response().end();
        });
        
        return router;
    }
}