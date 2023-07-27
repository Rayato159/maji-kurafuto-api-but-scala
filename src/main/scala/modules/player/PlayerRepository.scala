package modules.player

import pkg.db.Db

import com.sksamuel.elastic4s.*
import com.sksamuel.elastic4s.ElasticDsl.*
import com.sksamuel.elastic4s.requests.searches.SearchResponse

case class PlayerRepository():
  def findPlayers(searchQuery: String): List[Player] =
    val db = Db.elasticSearchConnect()
    val resp = db.execute {
      search("player").query(if searchQuery == "" then "*" else "*" + searchQuery + "*")
    }.await

    val users: Seq[Player] = resp.result.to[Player]

    users match
      case users => users.toList
      case null => List.empty
  def findOnePlayer(id: String): Option[Player] =
    val db = Db.elasticSearchConnect()
    val resp = db.execute {
      search("player").matchQuery("id", id)
    }.await

    val players: Seq[Player] = resp.result.to[Player]

    players match
      case players => Some(players.head)
      case null => None
  def createPlayer(player: Player): Option[String] = ???
  def updatePlayer(player: Player): Option[Player] = ???
  def deletePlayer(id: String): Option[String] = ???
