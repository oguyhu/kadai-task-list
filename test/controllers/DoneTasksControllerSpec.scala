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



class DoneTasksControllerSpec extends FunSpec
  with MustMatchers
  with GuiceOneAppPerSuite {

  implicit lazy val materializer: Materializer = app.materializer

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .disable[PlayModule]
      .overrides(bind[TaskService].to[MockTaskService])
      .build()

  describe("DoneTasksController") {
    describe("route of DoneTasksController#index") {
      it("should be valid") {
        val result = route(app, FakeRequest(GET, routes.DoneTasksController.index.toString)).get
        status(result) mustBe OK
      }
    }
  }

}