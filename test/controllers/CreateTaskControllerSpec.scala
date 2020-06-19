package controllers

import org.scalatest._
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.Application
import play.api.test._
import play.api.test.Helpers._
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import scalikejdbc.PlayModule
import services.{TaskService, MockTaskService}



class CreateTaskControllerSpec extends FunSpec
  with MustMatchers
  with GuiceOneAppPerSuite {

  override def fakeApplication(): Application =
    new GuiceApplicationBuilder()
      .disable[PlayModule]
      .overrides(bind[TaskService].to[MockTaskService])
      .build()

  describe("CreateTaskController") {
    describe("route of CreateTaskController#index") {
      it("should be valid") {
        val result = route(app, FakeRequest(GET, routes.CreateTaskController.index.toString)).get
        status(result) mustBe OK
      }
    }
    describe("route of CreateTaskController#create") {
      it("should be valid") {
        val result =
          route(app,
            FakeRequest(POST, routes.CreateTaskController.create.toString)
              .withFormUrlEncodedBody("title" -> "a", "content" -> "b", "schedule" -> "2020-06-18T20:20")).get
        status(result) mustBe SEE_OTHER
      }
    }
  }

}