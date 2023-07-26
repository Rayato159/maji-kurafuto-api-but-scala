package maji

import maji.MajiRepository

case class MajiHandler(majiRepository: MajiRepository):
  def findMaji(): List[Maji] =
    majiRepository.findMaji()
