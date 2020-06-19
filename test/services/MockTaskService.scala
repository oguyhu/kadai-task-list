package services

import java.time.{LocalDateTime, ZonedDateTime}

import forms.TaskForm
import models.Task
import scalikejdbc.DBSession

class MockTaskService extends TaskService {
  
  override def create(model: TaskForm)(implicit session: DBSession): Long = 1L

  override def findAll(implicit session: DBSession): List[Task] =
    List(Task(Some(1L), "a", Some("b"), Some(LocalDateTime.now), 1, ZonedDateTime.now, ZonedDateTime.now))

  override def findById(id: Long)(implicit session: DBSession): Option[Task] =
    Some(Task(Some(1L), "a", Some("b"), Some(LocalDateTime.now), 1, ZonedDateTime.now, ZonedDateTime.now))

  override def update(id: Long, model: TaskForm)(implicit session: DBSession): Int = 1

  override def deleteById(id: Long)(implicit session: DBSession): Int = 1


  override def getTasks(implicit session: DBSession): List[Task] = findAllByStatus(1)

  override def getDoneTasks(implicit session: DBSession): List[Task] = findAllByStatus(0)

  override def countTasks(implicit session: DBSession): Long = 1L

  override def countDoneTasks(implicit session: DBSession): Long = 1L

  override def findAllByStatus(status: Int)(implicit session: DBSession): List[Task] =
    List(Task(Some(1L), "a", Some("b"), Some(LocalDateTime.now), status, ZonedDateTime.now, ZonedDateTime.now))

  override def updateStatus(id: Long, status: Int)(implicit session: DBSession): Int = 1

  override def countByStatus(status: Int)(implicit session: DBSession): Long = 1L
}
