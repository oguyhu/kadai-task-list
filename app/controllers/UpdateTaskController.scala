package controllers

import java.time.ZonedDateTime

import forms.TaskForm
import javax.inject._
import models.Task
import play.api.i18n.{I18nSupport, Messages}
import play.api.mvc._
import scalikejdbc.AutoSession

@Singleton
class UpdateTaskController @Inject()(components: ControllerComponents)
  extends AbstractController(components)
    with I18nSupport
    with TaskControllerSupport {

  def index(taskId: Long): Action[AnyContent] = Action { implicit request =>
    val result = Task.findById(taskId).get
    val filledForm = form.fill(TaskForm(result.id, result.title, result.content, result.schedule))
    Ok(views.html.edit(filledForm))
  }

  def update: Action[AnyContent] = Action { implicit request =>
    form
      .bindFromRequest()
      .fold(
        formWithErrors => BadRequest(views.html.edit(formWithErrors)), { model =>
          implicit val session = AutoSession
          val result = Task.updateById(model.id.get).withAttributes(
            'title -> model.title,
            'content -> model.content,
            'schedule -> model.schedule,
            'updateAt -> ZonedDateTime.now
          )
          if (result > 0)
            Redirect(routes.GetTasksController.index())
          else
            InternalServerError(Messages("UpdateTaskError"))
        }
      )
  }
}