package forms

import java.time.LocalDateTime

case class TaskForm(id: Option[Long],
                    title: String,
                    content: Option[String],
                    schedule: Option[LocalDateTime])