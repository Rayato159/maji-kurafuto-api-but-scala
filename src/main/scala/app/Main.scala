package app

import pkg.db.Db

import maji.repository.MajiRepository
import maji.handler.MajiHandler

object Main:
  val majiRepository: MajiRepository = MajiRepository()
  val majiHandler: MajiHandler = MajiHandler(majiRepository)

  def main(args: Array[String]): Unit =
    println("Maji Kurafuto")
    println(Db.dbConnect())


