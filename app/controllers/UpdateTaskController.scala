package controllers

import java.time.ZonedDateTime

import forms.TaskForm
import javax.inject._
import models.Task
import play.api.i18n.{I18nSupport, Messages}
import play.api.mvc._
import scalikejdbc.AutoSession
import services.TaskService

@Singleton
class UpdateTaskController @Inject()(components: ControllerComponents, taskService: TaskService)
  extends AbstractController(components)
    with I18nSupport
    with TaskControllerSupport {

  def index(taskId: Long): Action[AnyContent] = Action { implicit request =>
    taskService.findById(taskId) match {
      case Some(task) =>
        val filledForm = form.fill(TaskForm (task.id, task.title, task.content, task.schedule) )
        Ok (views.html.edit(filledForm) )
      case _ => NotFound
    }
  }

  def update: Action[AnyContent] = Action { implicit request =>
    form
      .bindFromRequest()
      .fold(
        formWithErrors => BadRequest(views.html.edit(formWithErrors)), { model =>
          implicit val session = AutoSession
          model.id match {
            case Some(id) =>
              val result = taskService.update(id, model)
              if (result > 0)
                Redirect(routes.GetTasksController.index())
              else
                InternalServerError(Messages("UpdateTaskError"))
            case _ => NotFound
          }
        }
      )
  }
}
