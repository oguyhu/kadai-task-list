package controllers

import javax.inject._
import play.api.i18n.I18nSupport
import play.api.mvc._
import services.TaskService

@Singleton
class GetTaskController @Inject()(components: ControllerComponents, taskService: TaskService)
  extends AbstractController(components) with I18nSupport {

  def index(taskId: Long): Action[AnyContent] = Action { implicit request =>
    taskService.findById(taskId) match {
      case Some(task) => Ok(views.html.show(task))
      case _ => NotFound
    }
  }

}
