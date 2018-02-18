// =========================================================================
// Copyright 2018 T-Mobile, US
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// See the readme.txt file for additional language around disclaimer of warranties.
// =========================================================================
package com.tmobile.faas;

import function.Handler;
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
                vertx.deployVerticle("com.tmobile.faas.HandlerVerticle");
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
        router.route()
                .method(HttpMethod.POST)
                .path("/")
                .handler(routingContext -> {
                    String payload = routingContext.getBodyAsString();

                    //dumping the posted payload for debugging purposes
                    LOG.debug(payload);

                    Handler handler = new Handler();
                    String response = handler.Handle(payload.getBytes());
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