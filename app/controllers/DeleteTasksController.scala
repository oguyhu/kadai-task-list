package controllers

import javax.inject._
import models.Task
import play.api.i18n.{I18nSupport, Messages}
import play.api.mvc._
import scalikejdbc.AutoSession
import services.TaskService

@Singleton
class DeleteTasksController @Inject()(components: ControllerComponents, taskService: TaskService)
  extends AbstractController(components) with I18nSupport {

  /**  完了済のタスクのレコードをすべて削除する */
  def deleteDoneTasks: Action[AnyContent] = {
    val doneTasks = taskService.getDoneTasks
    deleteAll(doneTasks)
  }

  def deleteAll(tasks: Seq[Task]): Action[AnyContent] = Action { implicit request =>
    implicit val session = AutoSession
    for (task <- tasks) {
      task.id match {
        case Some(id) =>
          val result = taskService.deleteById (id)
          if (result <= 0) InternalServerError (Messages ("DeleteTaskError") )
        case _ => NotFound
      }
    }
    Redirect(routes.GetTasksController.index())
  }

}