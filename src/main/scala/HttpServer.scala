import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.ActorMaterializer

import scala.io.StdIn
import scala.util.{Failure, Success}

object HttpServer extends App with Directives{
  implicit  val system= ActorSystem("akka-http")
  implicit  val mat=ActorMaterializer()
  import system.dispatcher
  val port=8080
  val host="0.0.0.0"//localhost
  val route:Route =concat(
    post{
      path("register"){
        entity(as[String]){name=>
          complete(s"You have been successfully registered in Akka http mailing list $name")
        }
      }
    },
    path("greetings"){
    //Segment is used to extract path parameter from the request
    path(Segment) {name=>
      get {  //get rejects all non-GET requests
        complete(StatusCodes.OK, s"Accept warm greetings from Akka Http Server $name")
      }
    }

  })
  val bindingFuture=Http().bindAndHandle(route,host,port)
  //bind this route to this ip & port
  // or a set of routes to this ip & port
  println("press Enter to stop")
  bindingFuture.onComplete{
    case Success(serverBinding)=>println(s"Listening to ${serverBinding.localAddress}")
    case Failure(exception)=>
      println(s"Error occurred ${exception.getMessage} ")
      system.terminate()
  }
  StdIn.readLine()//Server stops upon pressing ENTER

  bindingFuture .flatMap(_.unbind())//unbind from port
    .onComplete{
      case _=> system.terminate()
    }
}
