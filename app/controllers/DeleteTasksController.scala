package controllers

import javax.inject._

import models.Task
import play.api.i18n.{ I18nSupport, Messages }
import play.api.mvc._
import scalikejdbc.AutoSession

@Singleton
class DeleteTasksController @Inject()(components: ControllerComponents)
  extends AbstractController(components) with I18nSupport {

  /**  完了済のタスクのレコードをすべて削除する */
  def deleteDoneTasks: Action[AnyContent] = {
    val doneTasks = Task.getDoneTasks
    deleteAll(doneTasks)
  }

  def deleteAll(tasks: Seq[Task]): Action[AnyContent] = Action { implicit request =>
    implicit val session = AutoSession
    for (task <- tasks) {
      val id = task.id.get
      val result = Task.deleteById(id)
      if (result <= 0) InternalServerError(Messages("DeleteTaskError"))
    }
    Redirect(routes.GetTasksController.index())
  }

}