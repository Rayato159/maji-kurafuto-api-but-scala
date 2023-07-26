package app

// Internal Modules
import maji.{Maji, MajiHandler, MajiRepository}
import maji.MajiProtocol._

// Internal Package
import pkg.db.Db

import pkg.response.ErrResponse
import pkg.response.ErrResponseProtocol._

// External Package
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.Done
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import scala.io.StdIn

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

object Main:
  implicit val system: ActorSystem[_] = ActorSystem(Behaviors.empty, "MajiSpray")
  implicit val executionContext: ExecutionContext = system.executionContext

  // Modules
  val majiRepository: MajiRepository = MajiRepository()
  val majiHandler: MajiHandler = MajiHandler(majiRepository)

  def main(args: Array[String]): Unit =
    val route: Route =
      concat(
        get {
          pathPrefix("maji" / IntNumber) { majiId =>
            majiHandler.findOneMaji(majiId) match
              case Some(result) => complete(StatusCodes.OK, result)
              case None =>
                val res = ErrResponse("not found")
                complete(StatusCodes.BadRequest, res)
          }
        },
        get {
          path("maji") {
            complete(majiHandler.findMaji())
          }
        },
        post {
          path("maji") {
            entity(as[Maji]) { req =>
              majiHandler.createMaji(req) match
                case Some(result) => complete(StatusCodes.Created, result)
                case None =>
                  val res = ErrResponse("not found")
                  complete(StatusCodes.BadRequest, res)
            }
          }
        },
      )

    val bindingFuture: Future[Http.ServerBinding] = Http().newServerAt("localhost", 8080).bind(route)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done