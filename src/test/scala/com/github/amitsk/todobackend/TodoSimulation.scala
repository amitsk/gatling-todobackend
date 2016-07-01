package com.github.amitsk.todobackend

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck

import scala.concurrent.duration._

class TodoSimulation extends Simulation {

  val httpConf = http
    .baseURL("http://localhost:8080/todos") // Here is the root for all relative URLs
    //.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Here are the common headers
    //.acceptEncodingHeader("gzip, deflate")
    //.acceptLanguageHeader("en-US,en;q=0.5")

  val checks = status.is(200)

  val scn = scenario("Get TODOs") // A scenario is a chain of requests and pauses
    .exec( Operations.getTodo(1, checks))

  setUp(scn.inject(atOnceUsers(1)).protocols(httpConf))

}
