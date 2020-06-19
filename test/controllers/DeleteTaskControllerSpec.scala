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



class DeleteTaskControllerSpec extends FunSpec
  with MustMatchers
  with GuiceOneAppPerSuite {

  implicit lazy val materializer: Materializer = app.materializer

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .disable[PlayModule]
      .overrides(bind[TaskService].to[MockTaskService])
      .build()

  describe("DeleteTaskController") {
    describe("DeleteTaskController#delete") {
      it("should be valid") {
        val deleteTaskController = app.injector.instanceOf[DeleteTaskController]
        val result = deleteTaskController.delete(1L).apply(FakeRequest().withCSRFToken)
        status(result) mustBe SEE_OTHER
      }
    }
    describe("route of DeleteTaskController#delete") {
      it("should be valid") {
        val result = route(app,
          FakeRequest(POST, routes.DeleteTaskController.delete(1L).toString)).get
        status(result) mustBe SEE_OTHER
      }
    }
  }

}