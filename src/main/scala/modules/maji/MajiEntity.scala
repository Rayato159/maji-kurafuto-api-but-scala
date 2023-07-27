package modules.maji

import spray.json.*
import spray.json.DefaultJsonProtocol.*

case class Maji(
               var id: Int = 0,
               var title: String = "",
               var description: String = "",
               var damage: Int = -1,
               )

object MajiProtocol extends DefaultJsonProtocol {
  implicit val majiFormat: RootJsonFormat[Maji] = jsonFormat4(Maji.apply)

  implicit object majiListFormat extends RootJsonFormat[List[Maji]] {
    def write(majis: List[Maji]): JsValue = JsArray(majis.map(_.toJson).toVector)

    def read(json: JsValue): List[Maji] = json match {
      case JsArray(elements) => elements.map(_.convertTo[Maji]).toList
      case _ => deserializationError("Expected JsArray for List[Maji] deserialization")
    }
  }
}
