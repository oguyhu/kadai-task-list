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



class DeleteTasksControllerSpec extends FunSpec
  with MustMatchers
  with GuiceOneAppPerSuite {

  implicit lazy val materializer: Materializer = app.materializer

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .disable[PlayModule]
      .overrides(bind[TaskService].to[MockTaskService])
      .build()

  describe("DeleteTasksController") {
    describe("route of DeleteTasksController#deleteDoneTasks") {
      it("should be valid") {
        val result = route(app, FakeRequest(POST, routes.DeleteTasksController.deleteDoneTasks.toString)).get
        status(result) mustBe SEE_OTHER
      }
    }
  }

}