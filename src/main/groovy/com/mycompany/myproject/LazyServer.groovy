package com.mycompany.myproject

import org.vertx.groovy.core.http.RouteMatcher
import org.vertx.groovy.platform.Verticle

class LazyServer extends Verticle {

    def start() {

        def server = vertx.createHttpServer()

        def routeMatcher = new RouteMatcher()

        routeMatcher.get("/ping") { req ->
            req.response.end "Pong !"
        }

        routeMatcher.get("/ping/:message") { req ->
            String msg = req.params.get("message")
            req.response.end "Pong ${msg} !"
        }

        server.requestHandler(routeMatcher.asClosure()).listen(9000, "localhost")

    }


}
