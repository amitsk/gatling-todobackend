package com.github.amitsk.todobackend

import com.github.amitsk.todobackend.Operations._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._


class TodoSimulation extends Simulation {

  val httpConf = http
    .baseURL("http://localhost:8080/todos") // Here is the root for all relative URLs
    .acceptEncodingHeader("gzip, deflate")

  val scn = scenario("Create TODOs") // A scenario is a chain of requests and pauses
    .exec(createTodo())
    .exec(getTodo())
    .exec(updateTodo())
    .exec(deleteTodo())

  setUp(scn.
    inject(
      nothingFor(1 seconds),
      atOnceUsers(2),
      rampUsers(1) over(2 seconds),
      constantUsersPerSec(2) during(2 seconds) randomized
    )

    .protocols(httpConf))
}
