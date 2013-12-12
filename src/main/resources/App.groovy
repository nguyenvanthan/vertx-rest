import com.mycompany.myproject.GroovyPingVerticle
import com.mycompany.myproject.LazyServer
import com.mycompany.myproject.Router
import fr.javageek.JavaServer

def webServerConf = [

        // Normal web server stuff
        port: 8080,
        host: 'localhost',

        //bridge: true,  // also act like an event bus bridge

        inbound_permitted: [ // allow messages from the client --> server
                [:]
        ],
        outbound_permitted: [
                [:]
        ] // allow messages from the server --> client

]

// Now we deploy the modules that we need

container.with {

    println 'deploy router verticle'
    deployVerticle "groovy:" + Router.class.getName()

    println 'deploy ping verticle'
    deployVerticle "groovy:" + GroovyPingVerticle.class.getName()

    println 'deploy lazy verticle'
    deployVerticle "groovy:" + LazyServer.class.getName()

    println 'deploy java server'
    deployVerticle JavaServer.class.getName()


    // Start the web server, with the config we defined above
    deployModule('io.vertx~mod-web-server~2.0.0-final', webServerConf)

}