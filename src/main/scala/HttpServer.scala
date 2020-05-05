import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.ActorMaterializer

import scala.io.StdIn

object HttpServer extends App with Directives{
  implicit  val system= ActorSystem("akka-http")
  implicit  val mat=ActorMaterializer()
  import system.dispatcher
  val port=8080
  val host="0.0.0.0"//localhost
  val route:Route =path("hello"){
    //get rejects all non-GET requests
    get {
      complete("Hello from Akka Http Server")
    }
  }
  val bindingFuture=Http().bindAndHandle(route,host,port)
  //bind this route to this ip & port
  // or a set of routes to this ip & port
  println(s"Server started on $port on host $host")
  println("press Enter to stop")
  StdIn.readLine()//Server stops upon pressing ENTER

  bindingFuture.flatMap(_.unbind())//unbind from port and host
    .onComplete{
      case _=>system.terminate()
    }
}
