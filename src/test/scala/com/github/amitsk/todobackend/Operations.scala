package com.github.amitsk.todobackend

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.HeaderNames._
import io.gatling.http.HeaderValues._
import io.gatling.http.check.HttpCheck
import io.gatling.http.request._
import io.codearte.jfairy.Fairy
import io.gatling.core.session.Expression

import scala.concurrent.duration._

object Operations {

  def getTodo(checks: HttpCheck) = exec(http("Get a TODO").get("/${todoId}").check(checks))

  def createTodo() = exec {
    session =>
      val textProducer = Fairy.create().textProducer()
      val title = textProducer.sentence(10)
      session.set("title", title)
  }.exec { // Defining a map of headers before the scenario allows you to reuse these in several requests
    val sentHeaders = Map(ContentType -> ApplicationJson, Accept -> ApplicationJson)

    http(" POST a TODO").post("/").headers(sentHeaders).body(ElFileBody("payloads/todo.json")).asJSON
      .check(status.is(201))
      .check(jsonPath("$.id").saveAs("todoId"))
  }
}
