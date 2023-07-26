package pkg.db

import java.sql.{Connection, DriverManager}

object Db extends App {
  def dbConnect(): Connection = {
    // connect to the database named "mysql" on port 3306 of localhost
    val url = "jdbc:mysql://localhost:3306/maji_kurafuto_db?characterEncoding=utf8"
    val driver = "com.mysql.jdbc.Driver"
    val username = "root"
    val password = "123456"
    var connection: Connection = null

    try {
      connection = DriverManager.getConnection(url, username, password)
    } catch {
      case e: Exception => println(s"Error: ${e.getMessage}")
    }

    connection
  }
}