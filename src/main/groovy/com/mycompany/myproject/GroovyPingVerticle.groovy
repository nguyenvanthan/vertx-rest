/*
 * Copyright 2013 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 *
 */

package com.mycompany.myproject

import org.vertx.groovy.platform.Verticle

class GroovyPingVerticle extends Verticle {

    def start() {

        def pingHandler = { message ->
            //container.logger.info("Sent back pong groovy!")
            //println "receive on bus: ${message.body}"
            message.reply("pong ${message.body}!".toString())
        }

        vertx.eventBus.registerLocalHandler("ping", pingHandler)
        println "Go go go !!"
        //container.logger.info("GroovyPingVerticle started");
    }
}
