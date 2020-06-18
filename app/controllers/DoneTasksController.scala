package controllers

import javax.inject._
import models.Task
import play.api.i18n.I18nSupport
import play.api.mvc._

@Singleton
class DoneTasksController @Inject()(components: ControllerComponents)
  extends AbstractController(components) with I18nSupport {

  def index: Action[AnyContent] = Action { implicit request =>
    val result = Task.getDoneTasks
    if (result.isEmpty) {
      Redirect(routes.GetTasksController.index())
    } else {
      Ok(views.html.done(result))
    }
  }

}