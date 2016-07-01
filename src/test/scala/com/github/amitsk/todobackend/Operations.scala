package com.github.amitsk.todobackend

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.HeaderNames._
import io.gatling.http.check.HttpCheck
import io.gatling.http.request._

import scala.concurrent.duration._

object Operations {

   def getTodo(id:Int, checks:HttpCheck) = exec( http("Get a TODO").get( s"/${id}").check(checks) )

}
