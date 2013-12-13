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

package com.viadeo.visit

import com.viadeo.visits.core.ConsultationType
import com.viadeo.visits.core.PagedList
import com.viadeo.visits.core.ProfileVisit
import com.viadeo.visits.server.dao.ConsultationDao
import com.viadeo.visits.server.dao.HTableTemplate
import org.apache.hadoop.hbase.client.HTablePool
import org.joda.time.Interval
import org.vertx.groovy.platform.Verticle
import org.vertx.java.core.json.JsonArray
import org.vertx.java.core.json.JsonObject

class ConsultProfileVerticle extends Verticle {

    def start() {

        HTableTemplate template = new HTableTemplate(new HTablePool());
        ConsultationDao dao = new ConsultationDao(template)

        def consultHandler = { message ->
            //container.logger.info("Sent back pong groovy!")
            //println "receive on bus: ${message.body}"

            Map params = message.body
            // println "params : ${params}"
            PagedList<ProfileVisit> result

            if (params?.start > -1 && params?.end > -1) {
                result = dao.getVisitsAndTotalForPeriod(params.memberId, params?.type, new Interval(params?.start, params?.end), params?.offset, params?.count, params?.updateLastCount);
            }
            result = dao.getVisitsAndTotal(
                    params.memberId.toInteger(),
                    params.type ?: ConsultationType.VISITOR,
                    params.offset ?: 0,
                    params.count ?: 10,
                    params.updateLastCount ?: false);

            // println "result : ${result}"
            // println "result : ${result.properties}"
            JsonObject response = new JsonObject()
            if (result.items) {
                response.putArray("items", new JsonArray(result.items))
            } else {
                response.putValue("items", 0)
            }
            response.putValue("total", result.total ?: 0)
            message.reply(response.encode())
        }

        vertx.eventBus.registerLocalHandler("consultation", consultHandler)

        println "verticle consultprofile ready"

    }
}
