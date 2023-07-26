package maji

import pkg.db.Db
import java.sql.{ResultSet}
case class MajiRepository():
  def findMaji(): List[Maji] =
    val db = Db.dbConnect()

    val query: String =
      """SELECT
        | id,
        | title,
        | description,
        | damage
      | FROM maji;""".stripMargin

    var majiList: List[Maji] = List.empty

    try
      val statement = db.createStatement()
      val rs: ResultSet = statement.executeQuery(query)

      while (rs.next())
        val id = rs.getInt("id")
        val title = rs.getString("title")
        val description = rs.getString("description")
        val damage = rs.getInt("damage")

        val maji = Maji(id, title, description, damage)
        majiList = majiList :+ maji

      rs.close()
      statement.close()
    catch
      case e: Exception => println(s"Error: ${e.getMessage}")
    finally
      db.close()
      
    majiList