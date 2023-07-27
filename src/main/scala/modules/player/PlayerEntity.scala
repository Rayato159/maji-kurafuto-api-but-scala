package modules.player

import java.sql.Timestamp

import spray.json._
import spray.json.DefaultJsonProtocol._

case class Player(
                       id: Int = 0,
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