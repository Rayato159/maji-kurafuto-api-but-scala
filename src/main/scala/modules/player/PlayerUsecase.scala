package modules.player

case class PlayerUsecase(palyerRepository: PlayerRepository):
  def findPlayers(searchQuery: String): List[Player] =
    palyerRepository.findPlayers(searchQuery)
  def findOnePlayer(id: String): Option[Player] = ???
  def createPlayer(player: Player): Option[Player] = ???
  def updatePlayer(player: Player): Option[Player] = ???
  def deletePlayer(id: String): Option[String] = ???
