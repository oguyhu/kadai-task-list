package controllers

import java.time.ZonedDateTime

import javax.inject._
import models.Task
import play.api.i18n.{I18nSupport, Messages}
import play.api.mvc._
import scalikejdbc.AutoSession

@Singleton
class UpdateStatusController @Inject()(components: ControllerComponents)
  extends AbstractController(components)
    with I18nSupport
    with TaskControllerSupport {

  def change(taskId: Long) = Action { implicit request =>
    val task = Task.findById(taskId).get
    val result = updateStatus(task, 0)
    if (result > 0)
      Redirect(routes.GetTasksController.index())
    else
      InternalServerError(Messages("UpdateStatusError"))
  }

  def undo(taskId: Long) = Action { implicit request =>
    val task = Task.findById(taskId).get
    val result = updateStatus(task, 1)
    if (result > 0)
      Redirect(routes.DoneTasksController.index())
    else
      InternalServerError(Messages("UpdateStatusError"))
  }

  def updateStatus(task: Task, status: Int) = {
    implicit val session = AutoSession
    Task.updateById(task.id.get).withAttributes(
      'status -> status,
      'updateAt -> ZonedDateTime.now
    )
  }
}