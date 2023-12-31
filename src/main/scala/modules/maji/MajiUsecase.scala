package modules.maji

import scala.concurrent.Future
import scala.util.{Failure, Success}

case class MajiUsecase(majiRepository: MajiRepository):
  def findMaji(): List[Maji] =
    majiRepository.findMaji()

  def findOneMaji(id: Int): Option[Maji] =
    majiRepository.findOneMaji(id)
      
  def createMaji(req: Maji): Option[Maji] =
    majiRepository.createMaji(req) match
      case Some(id) => 
        majiRepository.findOneMaji(id) match
          case Some(result) => Some(result)
          case None => None
      case None => None

  def editMaji(req: Maji): Option[Maji] =
    majiRepository.editMaji(req) match
      case Some(err) => None
      case None =>
        majiRepository.findOneMaji(req.id) match
          case Some(result) => Some(result)
          case None => None

  def deleteMaji(id: Int): Option[Exception] =
    majiRepository.deleteMaji(id)