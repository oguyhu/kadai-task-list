package controllers

import java.time.ZonedDateTime

import javax.inject._
import models.Task
import play.api.i18n.{I18nSupport, Messages}
import play.api.mvc._
import scalikejdbc.AutoSession
import services.TaskService

@Singleton
class UpdateStatusController @Inject()(components: ControllerComponents, taskService: TaskService)
  extends AbstractController(components)
    with I18nSupport
    with TaskControllerSupport {

  def change(taskId: Long) = Action { implicit request =>
    implicit val session = AutoSession
    val result = taskService.updateStatus(taskId, 0)
    if (result > 0)
      Redirect(routes.GetTasksController.index())
    else
      InternalServerError(Messages("UpdateStatusError"))
  }

  def undo(taskId: Long) = Action { implicit request =>
    implicit val session = AutoSession
    val result = taskService.updateStatus(taskId, 1)
    if (result > 0)
      Redirect(routes.DoneTasksController.index())
    else
      InternalServerError(Messages("UpdateStatusError"))
  }
}