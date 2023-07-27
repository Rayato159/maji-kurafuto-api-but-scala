package pkg.db

// Mysql
import java.sql.{Connection, DriverManager}

// Elasticsearch
import com.sksamuel.elastic4s.{ElasticClient, ElasticProperties}
import com.sksamuel.elastic4s.http.JavaClient

object Db extends App {
  def mysqlConnect(): Connection = {
    // connect to the database named "mysql" on port 3306 of localhost
    val url = "jdbc:mysql://localhost:3306/maji_kurafuto_mysql?characterEncoding=utf8"
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

  def elasticSearchConnect(): ElasticClient = {
    val props: ElasticProperties = ElasticProperties("http://localhost:9200")
    var connection: ElasticClient = null

    try {
     connection = ElasticClient(JavaClient(props))
    } catch {
      case e: Exception => println(s"Error: ${e.getMessage}")
    }

    connection
  }
}