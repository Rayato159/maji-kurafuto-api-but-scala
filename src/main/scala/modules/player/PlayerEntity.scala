package modules.player

import com.sksamuel.elastic4s.{Hit, HitReader}

import java.sql.Timestamp
import spray.json.*
import spray.json.DefaultJsonProtocol.*

import scala.util.{Try, Success, Failure}

case class Player(
                       id: String = "",
                       username: String = "",
                       bio: String = "",
                       created_at: String = Timestamp(System.currentTimeMillis()).toString,
                       updated_at: String = Timestamp(System.currentTimeMillis()).toString,
                       )

object PlayerProtocol extends DefaultJsonProtocol {
  implicit val playerFormat: RootJsonFormat[Player] = jsonFormat5(Player.apply)

  implicit object playerListFormat extends RootJsonFormat[List[Player]] {
    def write(players: List[Player]): JsValue = JsArray(players.map(_.toJson).toVector)

    def read(json: JsValue): List[Player] = json match {
      case JsArray(elements) => elements.map(_.convertTo[Player]).toList
      case _ => deserializationError("Expected JsArray for List[Player] deserialization")
    }
  }
}

implicit object PlayerHitReader extends HitReader[Player] {
  override def read(hit: Hit): Try[Player] = {
    val source = hit.sourceAsMap
    val player: Try[Player] = Try(Player(
      source("id").toString,
      source("username").toString,
      source("bio").toString,
      source("created_at").toString,
      source("updated_at").toString
    ))

    player match
      case Success(v) => player
      case Failure(e) =>
        Failure(Exception("Failed to read Player from Hit"))
  }
}