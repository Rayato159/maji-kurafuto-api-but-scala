package app

// Internal Modules
import modules.maji.{Maji, MajiUsecase, MajiRepository}
import modules.maji.MajiProtocol._

import modules.player.{Player, PlayerUsecase, PlayerRepository}
import modules.player.PlayerProtocol._

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
  // Maji
  val majiRepository: MajiRepository = MajiRepository()
  val majiUsecase: MajiUsecase = MajiUsecase(majiRepository)

  // Player
  val playerRepository: PlayerRepository = PlayerRepository()
  val playerUsecase: PlayerUsecase = PlayerUsecase(playerRepository)

  def main(args: Array[String]): Unit =
    // Maji routes
    val majiRoutes: Route =
      concat(
        get {
          pathPrefix("maji" / IntNumber) { majiId =>
            majiUsecase.findOneMaji(majiId) match
              case Some(result) => complete(StatusCodes.OK, result)
              case None =>
                val res = ErrResponse("not found")
                complete(StatusCodes.BadRequest, res)
          }
        },
        get {
          path("maji") {
            complete(majiUsecase.findMaji())
          }
        },
        post {
          path("maji") {
            entity(as[Maji]) { req =>
              majiUsecase.createMaji(req) match
                case Some(result) => complete(StatusCodes.Created, result)
                case None =>
                  val res = ErrResponse("not found")
                  complete(StatusCodes.BadRequest, res)
            }
          }
        },
        patch {
          pathPrefix("maji" / IntNumber) { majiId =>
            entity(as[Maji]) { req =>
              req.id = majiId
              majiUsecase.editMaji(req) match
                case Some(result) => complete(StatusCodes.OK, result)
                case None =>
                  val res = ErrResponse("not found")
                  complete(StatusCodes.BadRequest, res)
            }
          }
        },
        delete {
          pathPrefix("maji" / IntNumber) { majiId =>
            majiUsecase.deleteMaji(majiId) match
              case Some(result) => complete(StatusCodes.BadRequest, ErrResponse(result.getMessage))
              case None => complete(StatusCodes.OK, ErrResponse(s"maji_id: ${majiId} has been deleted"))
          }
        },
      )

    val playerRoutes: Route =
      concat(
        get {
          path("player") {
            parameter("search") {
              (searchQuery) => complete(playerUsecase.findPlayers(searchQuery))
            }
          }
        },
      )

    // Main routes
    val route: Route =
      concat(
        majiRoutes,
      )

    val bindingFuture: Future[Http.ServerBinding] = Http().newServerAt("localhost", 8080).bind(route)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done