package controllers

import javax.inject._
import models.Task
import play.api.i18n.I18nSupport
import play.api.mvc._
import services.TaskService

@Singleton
class DoneTasksController @Inject()(components: ControllerComponents, taskService: TaskService)
  extends AbstractController(components) with I18nSupport {

  def index: Action[AnyContent] = Action { implicit request =>
    val result = taskService.getDoneTasks
    if (result.isEmpty) {
      Redirect(routes.GetTasksController.index())
    } else {
      Ok(views.html.done(result))
    }
  }

}