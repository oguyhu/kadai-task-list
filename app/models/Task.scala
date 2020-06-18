package models

import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, ZonedDateTime}
import java.util.Locale

import scalikejdbc._
import skinny.orm._

case class Task(id: Option[Long],
                title: String,
                content: Option[String],
                schedule: Option[LocalDateTime],
                status: Int,
                createAt: ZonedDateTime,
                updateAt: ZonedDateTime)

object Task extends SkinnyCRUDMapper[Task] {

  override def tableName = "tasks"

  override def defaultAlias: Alias[Task] = createAlias("t")

  //findAllなどで内部的に使用されている。レコードからTaskを作成する
  override def extract(rs: WrappedResultSet, n: ResultName[Task]): Task =
    autoConstruct(rs, n)

  /**  完了していないタスクのリストを返す
   * @return List[Task] */
  def getTasks: List[Task] = findAllByStatus(1)

  /**  完了したタスクのリストを返す
   * @return List[Task] */
  def getDoneTasks: List[Task] = findAllByStatus(0)

  /**  完了していないタスクの数を返す
   * @return Long  */
  def countTasks = countByStatus(1)

  /**  完了したタスクの数を返す
   * @return Long  */
  def countDoneTasks = countByStatus(0)

  def findAllByStatus(status: Int) = {
    val task = Task.defaultAlias
    findAllBy(sqls.eq(task.status, status))
  }

  def countByStatus(status: Int) = {
    val task = Task.defaultAlias
    countBy(sqls.eq(task.status, status))
  }

  val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("'at' h:mm a 'on' EEEE, dd'th' MMMM", Locale.ENGLISH)

  /**  scheduleがNoneの場合は空文字を返す。
   *   formatterを使ってフォーマットしている
   * @return String  */
  def dateToString(task: Task): String = {
    task.schedule match {
      case Some(v) => v.format(formatter)
      case _ => ""
    }
  }
}