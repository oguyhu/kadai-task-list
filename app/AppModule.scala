import com.google.inject.AbstractModule
import services.{ TaskService, TaskServiceImpl }

class AppModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[TaskService]).to(classOf[TaskServiceImpl])
  }

}