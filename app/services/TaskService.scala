package services

import forms.TaskForm
import models.Task
import scalikejdbc.{AutoSession, DBSession}

trait TaskService {

  def create(model: TaskForm)(implicit session: DBSession = AutoSession): Long

  def findAll(implicit session: DBSession = AutoSession): List[Task]

  def findById(id: Long)(implicit session: DBSession = AutoSession): Option[Task]

  def update(id: Long, model: TaskForm)(implicit session: DBSession = AutoSession): Int

  def deleteById(id: Long)(implicit session: DBSession = AutoSession): Int




  def getTasks(implicit session: DBSession = AutoSession): List[Task]

  def getDoneTasks(implicit session: DBSession = AutoSession): List[Task]

  def countTasks(implicit session: DBSession = AutoSession): Long

  def countDoneTasks(implicit session: DBSession = AutoSession): Long

  def findAllByStatus(status: Int)(implicit session: DBSession = AutoSession): List[Task]

  def countByStatus(status: Int)(implicit session: DBSession = AutoSession): Long

  def updateStatus(id: Long, status: Int)(implicit session: DBSession = AutoSession): Int
}
