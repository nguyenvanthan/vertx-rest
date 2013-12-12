package fr.javageek;


import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Verticle;

public class JavaServer extends Verticle{

    @Override
    public void start() {
        HttpServer server = vertx.createHttpServer();
        RouteMatcher routeMatcher = new RouteMatcher();
        routeMatcher.get("/ping", new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                req.response().end("pong");
            }
        });
        server.requestHandler(routeMatcher).listen(7777, "localhost");
    }
}
