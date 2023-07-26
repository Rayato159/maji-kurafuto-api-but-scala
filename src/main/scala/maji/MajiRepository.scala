package maji

import pkg.db.Db

import java.sql.{ResultSet, PreparedStatement, Statement}
case class MajiRepository():
  def findMaji(): List[Maji] =
    val db = Db.dbConnect()

    val query: String =
      """
        |SELECT
        | id,
        | title,
        | description,
        | damage
        |FROM maji;""".stripMargin

    var majiList: List[Maji] = List.empty

    try
      val statement = db.createStatement()
      val rs = statement.executeQuery(query)

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

  def findOneMaji(id: Int): Option[Maji] =
    val db = Db.dbConnect()

    val query: String =
      s"""
        |SELECT
        | id,
        | title,
        | description,
        | damage
        |FROM maji
        |WHERE id = ?;
        |""".stripMargin

    var maji: Maji = Maji()

    try
      val preparedStatement: PreparedStatement = db.prepareStatement(query)
      preparedStatement.setInt(1, id)

      val rs: ResultSet = preparedStatement.executeQuery()

      while (rs.next())
        val id: Int = rs.getInt("id")
        val title: String = rs.getString("title")
        val description: String = rs.getString("description")
        val damage: Int = rs.getInt("damage")

        maji = Maji(id, title, description, damage)

      rs.close()
      preparedStatement.close()
    catch
      case e: Exception => println(s"Error: ${e.getMessage}")
    finally
      db.close()

    if maji.id == 0 then
      None
    else
      Some(maji)

  def createMaji(req: Maji): Option[Int] =
    val db = Db.dbConnect()

    val query: String =
      s"""
         |INSERT INTO maji (
         |  title,
         |  description,
         |  damage
         |)
         |VALUES
         |  (?, ?, ?);""".stripMargin

    var generatedId: Option[Int] = None

    try
      val preparedStatement: PreparedStatement = db.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)

      preparedStatement.setString(1, req.title)
      preparedStatement.setString(2, req.description)
      preparedStatement.setInt(3, req.damage)

      val affectedRows: Int = preparedStatement.executeUpdate()

      if affectedRows > 0 then
        val generatedKey: ResultSet = preparedStatement.getGeneratedKeys
        if generatedKey.next() then
          generatedId = Some(generatedKey.getInt(1))

        generatedKey.close()
        preparedStatement.close()
    catch
      case e: Exception => println(s"Error: ${e}")
    finally
      db.close()

    generatedId