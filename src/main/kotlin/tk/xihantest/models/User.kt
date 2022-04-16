package tk.xihantest.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import tk.xihantest.utils.sync.Config.defaultPortraitUrl
import tk.xihantest.utils.sync.entity.AnimeInfosEntity
import kotlinx.serialization.SerialName



@Serializable
data class User(
    val id: Int = 0,
    val name: String,
    var pass: String,
    val email: String,
    val portraitUrl: String = defaultPortraitUrl,
    val favoriteData: FavoriteDataDTO = FavoriteDataDTO(),
    val historyData: HistoryDataDTO = HistoryDataDTO(),
    val chaseAnimeData: ChaseAnimeDataDTO = ChaseAnimeDataDTO(),
    val created_at: String = ""){

    @Serializable
    data class FavoriteDataDTO(
        var list: MutableList<DataListDTO> = mutableListOf(),
        var allSize: Int = 0,
    ){
        @Serializable
        data class DataListDTO(
            var f_STATE: Int = 0,
            var f_AID: String = "1",
            var f_TITLE: String = "",
            var f_IMG_URL: String = "",
            val f_CREATED_AT: String = ""
        )
    }

    @Serializable
    data class HistoryDataDTO(
        var list: MutableList<DataListDTO> = mutableListOf(),
        var allSize: Int = 0,
    ){

        @Serializable
        data class DataListDTO(
            var f_AID: String = "1",
            var f_TITLE: String = "",
            var f_IMG_URL: String = "",
            var f_PLAY_URL: String = "",
            var f_PLAY_NUMBER: String = "",
            var f_UPDATE_TIME: String = "",
            var f_PLAYER_NUMBER: Int = 0,
            var f_PROGRESS: Long = 0,
            var f_DURATION: Long = 0,
            var f_PLAYER_LIST: List<AnimeInfosEntity.AniInfoEntity.R在线播放AllEntity> = mutableListOf()
        )

    }

    @Serializable
    data class ChaseAnimeDataDTO(
        var list: MutableList<DataListDTO> = mutableListOf(),
        var allSize: Int = 0,
    ){

        @Serializable
        data class DataListDTO(
            var f_STATE: Int = 0,
            var f_AID: String = "1",
            var f_TITLE: String = "",
            var f_IMG_URL: String = ""
//            var f_PLAY_TIME: String = "",
//            var f_UPDATE_TIME: String = "",
//            var f_PLAYER_NUMBER: String = "",
//            var f_UPDATE_NUMBER: String = ""
        )
    }

}

object Users: Table(){

    val id = integer("id").autoIncrement()

    val name = varchar("name", 16)
    val pass = varchar("pass", 32)
    val email = varchar("email", 64)
    val portraitUrl = varchar("portraitUrl", 999)
    val favoriteData = varchar("favoriteData", 1048576)
    val historyData = varchar("historyData", 1048576)
    val chaseAnimeData = varchar("chaseAnimeData", 1048576)
    val created_at = varchar("created_at", 99)


    override val primaryKey = PrimaryKey(id)

}

val usersStorage = mutableListOf<User>()
