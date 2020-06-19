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



class UpdateStatusControllerSpec extends FunSpec
  with MustMatchers
  with GuiceOneAppPerSuite {

  implicit lazy val materializer: Materializer = app.materializer

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .disable[PlayModule]
      .overrides(bind[TaskService].to[MockTaskService])
      .build()

  describe("UpdateStatusController") {
    describe("UpdateStatusController#change") {
      it("should be valid") {
        val updateStatusController = app.injector.instanceOf[UpdateStatusController]
        val result = updateStatusController.change(1L).apply(FakeRequest().withCSRFToken)
        status(result) mustBe SEE_OTHER
      }
    }
    describe("route of UpdateStatusController#change") {
      it("should be valid") {
        val result = route(app,
          FakeRequest(POST, routes.UpdateStatusController.change(1L).toString)).get
        status(result) mustBe SEE_OTHER
      }
    }
    describe("UpdateStatusController#undo") {
      it("should be valid") {
        val updateStatusController = app.injector.instanceOf[UpdateStatusController]
        val result = updateStatusController.undo(1L).apply(FakeRequest().withCSRFToken)
        status(result) mustBe SEE_OTHER
      }
    }
    describe("route of UpdateStatusController#undo") {
      it("should be valid") {
        val result = route(app,
          FakeRequest(POST, routes.UpdateStatusController.undo(1L).toString)).get
        status(result) mustBe SEE_OTHER
      }
    }
  }

}