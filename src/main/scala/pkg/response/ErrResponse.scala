package pkg.response

import spray.json.*
import spray.json.DefaultJsonProtocol.*

case class ErrResponse(message: String)

object ErrResponseProtocol extends DefaultJsonProtocol {
  implicit val errResponseFormat: RootJsonFormat[ErrResponse] = jsonFormat1(ErrResponse.apply)
}

