package services

import java.time.ZonedDateTime

import forms.TaskForm
import javax.inject.Singleton
import models.Task
import scalikejdbc._

import scala.collection.immutable.Range

@Singleton
class TaskServiceImpl extends TaskService {

  override def create(model: TaskForm)(implicit session: DBSession): Long = {
    val now = ZonedDateTime.now
    Task.createWithAttributes(
      'title -> model.title,
      'content -> model.content,
      'schedule -> model.schedule,
      'createAt -> now,
      'updateAt -> now
    )
  }

  override def findAll(implicit session: DBSession): List[Task] = Task.findAll()

  override def findById(id: Long)(implicit session: DBSession): Option[Task] = Task.findById(id)

  override def update(id: Long, model: TaskForm)(implicit session: DBSession): Int = {
    Task.updateById(id).withAttributes(
      'title -> model.title,
      'content -> model.content,
      'schedule -> model.schedule,
      'updateAt -> ZonedDateTime.now
    )
  }

  override def deleteById(id: Long)(implicit session: DBSession): Int = Task.deleteById(id)




  /**  完了していないタスクのリストを返す
   * @return List[Task] */
  override def getTasks(implicit session: DBSession): List[Task] = findAllByStatus(1)

  /**  完了したタスクのリストを返す
   * @return List[Task] */
  override def getDoneTasks(implicit session: DBSession): List[Task] = findAllByStatus(0)

  /**  完了していないタスクの数を返す
   * @return Long  */
  override def countTasks(implicit session: DBSession) = countByStatus(1)

  /**  完了したタスクの数を返す
   * @return Long  */
  override def countDoneTasks(implicit session: DBSession) = countByStatus(0)

  override def findAllByStatus(status: Int)(implicit session: DBSession): List[Task] = {
    val task = Task.defaultAlias
    Task.findAllBy(sqls.eq(task.status, status))
  }

  def countByStatus(status: Int)(implicit session: DBSession): Long = {
    val task = Task.defaultAlias
    Task.countBy(sqls.eq(task.status, status))
  }

  override def updateStatus(id: Long, status: Int)(implicit session: DBSession): Int =
    Task.updateById(id).withAttributes(
      'status -> status,
      'updateAt -> ZonedDateTime.now
    )

}
