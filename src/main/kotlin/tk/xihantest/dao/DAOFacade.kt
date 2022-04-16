package tk.xihantest.dao


import tk.xihantest.models.*
import tk.xihantest.utils.sync.entity.AnimeInfosEntity

interface DAOFacade {

    /**
     * 用户数据
     */
    suspend fun allUsers(): List<User>
    suspend fun user(id: Int): User?
    suspend fun userFavorite(id: Int): User.FavoriteDataDTO?
    suspend fun userHistory(id: Int): User.HistoryDataDTO?
    suspend fun userChaseAnime(id: Int): User.ChaseAnimeDataDTO?
    suspend fun addNewUser(userEntity: User): User?
    suspend fun updateUser(id: Int, newPass: String?, newEmail: String?, newPortraitUrl: String?): Boolean
    suspend fun addNewFavourite(userId: Int, newFavourite: MutableList<User.FavoriteDataDTO.DataListDTO>): Boolean
    suspend fun addNewHistory(userId: Int, newHistoryList:  MutableList<User.HistoryDataDTO.DataListDTO>): Boolean
    suspend fun addNewChaseAnime(userId: Int, newChaseAnimeList: MutableList<User.ChaseAnimeDataDTO.DataListDTO>): Boolean
    suspend fun updateChaseAnime(userId: Int, newChaseAnimeList: MutableList<User.ChaseAnimeDataDTO.DataListDTO>): Boolean
    suspend fun updateHistory(userId: Int, newHistoryList: MutableList<User.HistoryDataDTO.DataListDTO>): Boolean
    suspend fun removeChaseAnime(userId: Int, animeId: String = "",  animeIds: List<String> = listOf()): Boolean
    suspend fun removeHistory(userId: Int, animeId: String = "",  animeIds: List<String> = listOf()): Boolean
    suspend fun removeFavourite(userId: Int, animeId: String = "", animeIds: List<String> = listOf()): Boolean
    suspend fun removeUser(id: Int): Boolean

    /**
     * 简单数据
     */
    suspend fun allSimpleAnimeData(): List<SimpleAnimeData>
    suspend fun simpleAnimeData(aid: String): SimpleAnimeData?
    suspend fun addNewSimpleAnimeData(simpleAnimeData: SimpleAnimeData): SimpleAnimeData?
    suspend fun updateSimpleAnimeData(aid: String, simpleAnimeData: SimpleAnimeData): Boolean
    suspend fun removeSimpleAnimeData(aid: String): Boolean

    /**
     * 完整数据
     */
    suspend fun allCompleteAnimeData(): List<CompleteAnimeData>
    suspend fun completeAnimeData(aid: String): CompleteAnimeData?
    suspend fun addNewCompleteAnimeData(completeAnimeData: CompleteAnimeData): CompleteAnimeData?
    suspend fun updateCompleteAnimeData(aid: String, completeAnimeData: CompleteAnimeData): Boolean
    suspend fun removeCompleteAnimeData(aid: String): Boolean

    suspend fun allCloudDiskLink(): List<CloudDiskLink>
    suspend fun cloudDiskLink(aid: String): CloudDiskLink?
    suspend fun addNewCloudDiskLink(cloudDiskLink: CloudDiskLink): CloudDiskLink?
    suspend fun updateCloudDiskLink(aid: String, cloudDiskLinkList: MutableList<AnimeInfosEntity.AniInfoEntity.R网盘资源2Entity>): Boolean
    suspend fun removeCloudDiskLink(aid: String): Boolean

    /**
     * 播放地址
     */
    suspend fun allPlayList(): List<PlayerNumber>
    suspend fun playLists(aid: String): PlayerNumber?
    suspend fun addNewPlayList(aid: String,  playList: MutableList<MutableList<AnimeInfosEntity.AniInfoEntity.R在线播放AllEntity>>): PlayerNumber?
    suspend fun updatePlayList(aid: String, newPlayList: MutableList<MutableList<AnimeInfosEntity.AniInfoEntity.R在线播放AllEntity>>): Boolean
    suspend fun removePlayList(aid: String): Boolean

}