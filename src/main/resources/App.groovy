import com.mycompany.myproject.GroovyPingVerticle

def webServerConf = [

        // Normal web server stuff
        port: 8080,
        host: 'localhost',

        bridge: true,  // also act like an event bus bridge

        inbound_permitted: [ // allow messages from the client --> server
                [:]
        ],
        outbound_permitted: [
                [:]
        ] // allow messages from the server --> client

]

// Now we deploy the modules that we need

container.with {

    deployVerticle "groovy:"+GroovyPingVerticle.class.getName()

    // Start the web server, with the config we defined above
    deployModule('io.vertx~mod-web-server~2.0.0-final', webServerConf)

}