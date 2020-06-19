package controllers

import javax.inject._
import models.Task
import play.api.i18n.I18nSupport
import play.api.mvc._
import services.TaskService

@Singleton
class GetTasksController @Inject()(components: ControllerComponents, taskService: TaskService)
  extends AbstractController(components) with I18nSupport {

  def index: Action[AnyContent] = Action { implicit request =>
    val result = taskService.getTasks
    val count = taskService.countDoneTasks.toInt
    Ok(views.html.index(result)(count))
  }

}