package com.github.amitsk.todobackend

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.HeaderNames._
import io.gatling.http.HeaderValues._
import io.codearte.jfairy.Fairy


object Operations {

  def getTodo() = exec(http("Get a TODO").get("/${todoId}").check(status.is(200)))

  def createTodo() = exec {
    session =>
      val textProducer = Fairy.create().textProducer()
      val title = textProducer.sentence(10)
      session.set("title", title)
  }.exec {
    // Defining a map of headers before the scenario allows you to reuse these in several requests
    val sentHeaders = Map(ContentType -> ApplicationJson, Accept -> ApplicationJson)

    http(" POST a TODO").post("/").headers(sentHeaders).body(ElFileBody("payloads/todo.json")).asJSON
      .check(status.is(201))
      .check(jsonPath("$.id").saveAs("todoId"))
  }

  def deleteTodo() = exec (http ( "Delete a TODO" ).delete("/${todoId}").check(status.is(200)) )

  def updateTodo() = exec {
    // Defining a map of headers before the scenario allows you to reuse these in several requests
    val sentHeaders = Map(ContentType -> ApplicationJson, Accept -> ApplicationJson)

    http(" Update/Patch a TODO").patch("/${todoId}")
      .headers(sentHeaders)
      .body(ElFileBody("payloads/todo.json")).asJSON
      .check(status.is(200))
      .check(jsonPath("$.id").saveAs("todoId"))
  }

}
