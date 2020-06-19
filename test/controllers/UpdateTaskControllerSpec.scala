package controllers

import akka.stream.Materializer
import org.scalatest._
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.test._
import play.api.test.Helpers._
import play.api.test.CSRFTokenHelper._
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import scalikejdbc.PlayModule
import services.{MockTaskService, TaskService}



class UpdateTaskControllerSpec extends FunSpec
  with MustMatchers
  with GuiceOneAppPerSuite {

  implicit lazy val materializer: Materializer = app.materializer

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .disable[PlayModule]
      .overrides(bind[TaskService].to[MockTaskService])
      .build()

  describe("UpdateTaskController") {
    describe("route of UpdateTaskController#index") {
      it("should be valid") {
        val result = route(app, FakeRequest(GET, routes.UpdateTaskController.index(1L).toString)).get
        status(result) mustBe OK
      }
    }
    describe("UpdateTaskController#update") {
      it("should be valid") {
        val updateTaskController = app.injector.instanceOf[UpdateTaskController]
        val result = updateTaskController.update.apply(
          FakeRequest()
            .withFormUrlEncodedBody("id" -> "1", "title" -> "a", "content" -> "b", "schedule" -> "2020-06-18T20:20")
            .withCSRFToken)
        status(result) mustBe SEE_OTHER
      }
    }
    describe("route of UpdateTaskController#update") {
      it("should be valid") {
        val result = route(app,
          FakeRequest(POST, routes.UpdateTaskController.update().toString)
            .withFormUrlEncodedBody("id" -> "1", "title" -> "a", "content" -> "b", "schedule" -> "2020-06-18T20:20")).get
        status(result) mustBe SEE_OTHER
      }
    }
  }

}