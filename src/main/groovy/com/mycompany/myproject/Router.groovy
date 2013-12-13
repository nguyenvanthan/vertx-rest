package com.mycompany.myproject

import org.vertx.groovy.core.http.RouteMatcher
import org.vertx.groovy.platform.Verticle

class Router extends Verticle {

    def start() {

        RouteMatcher router = new RouteMatcher()
        // the matcher for the complete list and the search
        router.get("/ping/:message", { req ->
            println "receive message: ${req.params['message']}"
            vertx.eventBus.send("ping", req.params['message']) { message ->
                //println "I received a reply ${message.body}"
                req.response.end "OK !"
            }
        })

        // the matcher for a specific id
        router.get("/up", { req ->
            req.response.end 'UP !'
        })

        vertx.createHttpServer().requestHandler(router.asClosure()).listen(8888)

    }


}
