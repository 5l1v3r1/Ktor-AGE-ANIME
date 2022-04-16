package tk.xihantest.dao

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.jetbrains.exposed.sql.*
import tk.xihantest.dao.UsersDatabaseFactory.dbQuery
import tk.xihantest.models.*

import tk.xihantest.models.Users.chaseAnimeData
import tk.xihantest.models.Users.created_at
import tk.xihantest.models.Users.favoriteData
import tk.xihantest.models.Users.historyData
import tk.xihantest.plugins.json
import tk.xihantest.utils.Utils
import tk.xihantest.utils.sync.Config.defaultPortraitUrl
import tk.xihantest.utils.sync.entity.AnimeInfosEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DAOFacadeImpl : DAOFacade {

    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id],
        name = row[Users.name],
        pass = row[Users.pass],
        email = row[Users.email],
        portraitUrl = row[Users.portraitUrl],
        favoriteData = json.decodeFromString(row[favoriteData]),
        historyData = json.decodeFromString(row[historyData]),
        chaseAnimeData = json.decodeFromString(row[chaseAnimeData]),
        created_at = row[created_at]
    )

    override suspend fun allUsers(): List<User> = dbQuery {
        Users.selectAll().map(::resultRowToUser)
    }

    override suspend fun user(id: Int): User? = dbQuery {
        Users.select { Users.id eq id }.mapNotNull(::resultRowToUser).singleOrNull()
    }

    override suspend fun userFavorite(id: Int): User.FavoriteDataDTO? = dbQuery {
        Users.select { Users.id eq id }.mapNotNull(::resultRowToUser).singleOrNull()?.favoriteData
    }

    override suspend fun userHistory(id: Int): User.HistoryDataDTO? = dbQuery {
        Users.select { Users.id eq id }.mapNotNull(::resultRowToUser).singleOrNull()?.historyData
    }

    override suspend fun userChaseAnime(id: Int): User.ChaseAnimeDataDTO? = dbQuery {
        Users.select { Users.id eq id }.mapNotNull(::resultRowToUser).singleOrNull()?.chaseAnimeData
    }

    override suspend fun addNewUser(userEntity: User): User? = dbQuery {
        Users.insert {
            it[name] = userEntity.name
            it[pass] = userEntity.pass
            it[email] = userEntity.email
            it[portraitUrl] = userEntity.portraitUrl
            it[favoriteData] = json.encodeToString(userEntity.favoriteData)
            it[historyData] = json.encodeToString(userEntity.historyData)
            it[chaseAnimeData] = json.encodeToString(userEntity.chaseAnimeData)
            it[created_at] = userEntity.created_at
        }.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }

    override suspend fun updateUser(id: Int, newPass: String?, newEmail: String?, newPortraitUrl: String?): Boolean =
        dbQuery {
            try {
                Users.update({ Users.id eq id }) {
                    if (newPass != null) {
                        it[pass] = newPass
                    }
                    if (newEmail != null) {
                        it[email] = newEmail
                    }
                    if (newPortraitUrl != null) {
                        it[portraitUrl] = newPortraitUrl
                    }
                } > 0
            } catch (e: Exception) {
                false
            }
        }

    override suspend fun addNewFavourite(
        userId: Int,
        newFavourite: MutableList<User.FavoriteDataDTO.DataListDTO>,
    ): Boolean = dbQuery {
        try {
            userFavorite(userId)?.list?.let { favoriteList ->
                favoriteList.addAll(newFavourite)
                Users.update({ Users.id eq userId }) {
                    it[favoriteData] =
                        json.encodeToString(User.FavoriteDataDTO(list = favoriteList, allSize = favoriteList.size))
                } > 0
            } ?: run {
                Users.update({ Users.id eq userId }) {
                    it[favoriteData] =
                        json.encodeToString(User.FavoriteDataDTO(list = newFavourite, allSize = newFavourite.size))
                } > 0
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun addNewHistory(
        userId: Int,
        newHistoryList: MutableList<User.HistoryDataDTO.DataListDTO>,
    ): Boolean = dbQuery {
        try {
            userHistory(userId)?.list?.let { historyList ->
                historyList.addAll(newHistoryList)
                Users.update({ Users.id eq userId }) {
                    it[historyData] =
                        json.encodeToString(User.HistoryDataDTO(list = historyList, allSize = historyList.size))
                } > 0
            } ?: run {
                Users.update({ Users.id eq userId }) {
                    it[historyData] =
                        json.encodeToString(User.HistoryDataDTO(list = newHistoryList, allSize = newHistoryList.size))
                } > 0
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun addNewChaseAnime(
        userId: Int,
        newChaseAnimeList: MutableList<User.ChaseAnimeDataDTO.DataListDTO>,
    ): Boolean = dbQuery {
        try {
            userChaseAnime(userId)?.list?.let { chaseAnimeList ->
                chaseAnimeList.addAll(newChaseAnimeList)
                Users.update({ Users.id eq userId }) {
                    it[chaseAnimeData] = json.encodeToString(User.ChaseAnimeDataDTO(list = chaseAnimeList,
                        allSize = chaseAnimeList.size))
                } > 0
            } ?: run {
                Users.update({ Users.id eq userId }) {
                    it[chaseAnimeData] = json.encodeToString(User.ChaseAnimeDataDTO(list = newChaseAnimeList,
                        allSize = newChaseAnimeList.size))
                } > 0
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updateChaseAnime(
        userId: Int,
        newChaseAnimeList: MutableList<User.ChaseAnimeDataDTO.DataListDTO>,
    ): Boolean = dbQuery {
        try {
            Users.update({ Users.id eq userId }) {
                it[chaseAnimeData] = json.encodeToString(User.ChaseAnimeDataDTO(list = newChaseAnimeList,
                    allSize = newChaseAnimeList.size))
            } > 0
        } catch (e: Exception) {
            false
        }

    }

    override suspend fun updateHistory(
        userId: Int,
        newHistoryList: MutableList<User.HistoryDataDTO.DataListDTO>,
    ): Boolean = dbQuery {
        try {
            val userHistoryList = userHistory(userId)?.list
            newHistoryList.forEach {  newHistoryListIt ->
                userHistoryList?.find {it.f_AID == newHistoryListIt.f_AID }?.let { dataList ->
                    dataList.f_TITLE = newHistoryListIt.f_TITLE
                    dataList.f_IMG_URL = newHistoryListIt.f_IMG_URL
                    dataList.f_PLAY_URL = newHistoryListIt.f_PLAY_URL
                    dataList.f_PLAY_NUMBER = newHistoryListIt.f_PLAY_NUMBER
                    dataList.f_UPDATE_TIME = newHistoryListIt.f_UPDATE_TIME
                    dataList.f_PLAYER_NUMBER = newHistoryListIt.f_PLAYER_NUMBER
                    dataList.f_PROGRESS = newHistoryListIt.f_PROGRESS
                    dataList.f_DURATION = newHistoryListIt.f_DURATION
                    dataList.f_PLAYER_LIST = newHistoryListIt.f_PLAYER_LIST
                }?:addNewHistory(userId, newHistoryList)
            }
            if (!userHistoryList.isNullOrEmpty()){
                Users.update({ Users.id eq userId }) {
                    it[historyData] =
                        json.encodeToString(User.HistoryDataDTO(list = userHistoryList, allSize = userHistoryList.size))
                } > 0
            }else{
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun removeChaseAnime(userId: Int, animeId: String, animeIds: List<String>): Boolean = dbQuery {
        try {
            userChaseAnime(userId)?.list?.let { list ->
                if (!Utils.isNull(animeId)) {
                    list.removeAll { it.f_AID == animeId }
                } else if (animeIds.isNotEmpty()) {
                    list.removeAll { animeIds.contains(it.f_AID) }
                }
                Users.update({ Users.id eq userId }) {
                    it[chaseAnimeData] = json.encodeToString(User.ChaseAnimeDataDTO(list = list, allSize = list.size))
                } > 0
            } ?: run {
                Users.update({ Users.id eq userId }) {
                    it[chaseAnimeData] =
                        json.encodeToString(User.ChaseAnimeDataDTO(list = mutableListOf(), allSize = 0))
                } > 0
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun removeHistory(userId: Int, animeId: String, animeIds: List<String>): Boolean = dbQuery {
        try {
            userHistory(userId)?.list?.let { list ->
                if (!Utils.isNull(animeId)) {
                    list.removeAll { it.f_AID == animeId }
                } else if (animeIds.isNotEmpty()) {
                    list.removeAll { animeIds.contains(it.f_AID) }
                }
                Users.update({ Users.id eq userId }) {
                    it[historyData] = json.encodeToString(User.HistoryDataDTO(list = list, allSize = list.size))
                } > 0
            } ?: run {
                Users.update({ Users.id eq userId }) {
                    it[historyData] = json.encodeToString(User.HistoryDataDTO(list = mutableListOf(), allSize = 0))
                } > 0
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun removeFavourite(userId: Int, animeId: String, animeIds: List<String>): Boolean = dbQuery {
        try {
            userFavorite(userId)?.list?.let { favoriteList ->
                if (!Utils.isNull(animeId)) {
                    favoriteList.removeAll { it.f_AID == animeId }
                } else if (animeIds.isNotEmpty()) {
                    favoriteList.removeAll { animeIds.contains(it.f_AID) }
                }
                Users.update({ Users.id eq userId }) {
                    it[favoriteData] =
                        json.encodeToString(User.FavoriteDataDTO(list = favoriteList, allSize = favoriteList.size))
                } > 0
            } ?: run {
                Users.update({ Users.id eq userId }) {
                    it[favoriteData] = json.encodeToString(User.FavoriteDataDTO(list = mutableListOf(), allSize = 0))
                } > 0
            }
        } catch (e: Exception) {
            false
        }

    }

    override suspend fun removeUser(id: Int): Boolean = dbQuery {
        try {
            Users.deleteWhere { Users.id eq id } > 0
        } catch (e: Exception) {
            false
        }
    }

    private fun resultRowToSimpleAnimeData(row: ResultRow) = SimpleAnimeData(
        animeId = row[SimpleAnimeDatas.animeId],
        r其他名称 = row[SimpleAnimeDatas.r其他名称],
        r制作公司 = row[SimpleAnimeDatas.r制作公司],
        r剧情类型 = json.decodeFromString(row[SimpleAnimeDatas.r剧情类型]),
        r动画名称 = row[SimpleAnimeDatas.r动画名称],
        r动画种类 = row[SimpleAnimeDatas.r动画种类],
        r原作 = row[SimpleAnimeDatas.r原作],
        r原版名称 = row[SimpleAnimeDatas.r原版名称],
        r封面图小 = row[SimpleAnimeDatas.r封面图小],
        r播放状态 = row[SimpleAnimeDatas.r播放状态],
        r新番标题 = row[SimpleAnimeDatas.r新番标题],
        r简介 = row[SimpleAnimeDatas.r简介],
        r首播时间 = row[SimpleAnimeDatas.r首播时间]
    )

    override suspend fun allSimpleAnimeData(): List<SimpleAnimeData> = dbQuery {
        SimpleAnimeDatas.selectAll().map(::resultRowToSimpleAnimeData)
    }

    override suspend fun simpleAnimeData(aid: String): SimpleAnimeData? = dbQuery {
        SimpleAnimeDatas.select { SimpleAnimeDatas.animeId eq aid }.map(::resultRowToSimpleAnimeData).firstOrNull()
    }

    override suspend fun addNewSimpleAnimeData(simpleAnimeData: SimpleAnimeData): SimpleAnimeData? = dbQuery {
        SimpleAnimeDatas.insert {
            it[animeId] = simpleAnimeData.animeId
            it[r其他名称] = simpleAnimeData.r其他名称
            it[r制作公司] = simpleAnimeData.r制作公司
            it[r剧情类型] = json.encodeToString(simpleAnimeData.r剧情类型)
            it[r动画名称] = simpleAnimeData.r动画名称
            it[r动画种类] = simpleAnimeData.r动画种类
            it[r原作] = simpleAnimeData.r原作
            it[r原版名称] = simpleAnimeData.r原版名称
            it[r封面图小] = simpleAnimeData.r封面图小
            it[r播放状态] = simpleAnimeData.r播放状态
            it[r新番标题] = simpleAnimeData.r新番标题
            it[r简介] = simpleAnimeData.r简介
            it[r首播时间] = simpleAnimeData.r首播时间
        }.resultedValues?.singleOrNull()?.let(::resultRowToSimpleAnimeData)
    }

    override suspend fun updateSimpleAnimeData(
        aid: String,
        simpleAnimeData: SimpleAnimeData
    ): Boolean = dbQuery {
        try {
            SimpleAnimeDatas.update({ SimpleAnimeDatas.animeId eq aid }) {
                it[r其他名称] = simpleAnimeData.r其他名称
                it[r制作公司] = simpleAnimeData.r制作公司
                it[r剧情类型] = json.encodeToString(simpleAnimeData.r剧情类型)
                it[r动画名称] = simpleAnimeData.r动画名称
                it[r动画种类] = simpleAnimeData.r动画种类
                it[r原作] = simpleAnimeData.r原作
                it[r原版名称] = simpleAnimeData.r原版名称
                it[r封面图小] = simpleAnimeData.r封面图小
                it[r播放状态] = simpleAnimeData.r播放状态
                it[r新番标题] = simpleAnimeData.r新番标题
                it[r简介] = simpleAnimeData.r简介
                it[r首播时间] = simpleAnimeData.r首播时间
            } > 0
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun removeSimpleAnimeData(aid: String): Boolean = dbQuery {
        SimpleAnimeDatas.deleteWhere { SimpleAnimeDatas.animeId eq aid } > 0
    }

    private fun resultRowToCompleteAnimeData(row: ResultRow) = CompleteAnimeData(
        aID = row[CompleteAnimeDatas.animeId],
        collectCnt = row[CompleteAnimeDatas.collectCnt].toInt(),
        commentCnt = row[CompleteAnimeDatas.commentCnt].toInt(),
        dEFPLAYINDEX = row[CompleteAnimeDatas.dEFPLAYINDEX],
        filePath = row[CompleteAnimeDatas.filePath],
        lastModified = row[CompleteAnimeDatas.lastModified],
        modifiedTime = row[CompleteAnimeDatas.modifiedTime].toInt(),
        rankCnt = row[CompleteAnimeDatas.rankCnt].toInt(),
        r其它名称 = row[CompleteAnimeDatas.r其它名称],
        r制作公司 = row[CompleteAnimeDatas.r制作公司],
        r剧情类型 = row[CompleteAnimeDatas.r剧情类型],
        r剧情类型2 = json.decodeFromString(row[CompleteAnimeDatas.r剧情类型2]),
        r动画名称 = row[CompleteAnimeDatas.r动画名称],
        r动画种类 = row[CompleteAnimeDatas.r动画种类],
        r原作 = row[CompleteAnimeDatas.r原作],
        r原版名称 = row[CompleteAnimeDatas.r原版名称],
        r在线播放All = json.decodeFromString(row[CompleteAnimeDatas.r在线播放All]),
        r地区 = row[CompleteAnimeDatas.r地区],
        r备用 = row[CompleteAnimeDatas.r备用],
        r字母索引 = row[CompleteAnimeDatas.r字母索引],
        r官方网站 = row[CompleteAnimeDatas.r官方网站],
        r封面图 = row[CompleteAnimeDatas.r封面图],
        r封面图小 = row[CompleteAnimeDatas.r封面图小],
        r推荐星级 = row[CompleteAnimeDatas.r推荐星级].toInt(),
        r播放状态 = row[CompleteAnimeDatas.r播放状态],
        r新番标题 = row[CompleteAnimeDatas.r新番标题],
        r更新时间 = row[CompleteAnimeDatas.r更新时间],
        r更新时间str = row[CompleteAnimeDatas.r更新时间str],
        r更新时间str2 = row[CompleteAnimeDatas.r更新时间str2],
        r更新时间unix = row[CompleteAnimeDatas.r更新时间unix].toInt(),
        r标签 = row[CompleteAnimeDatas.r标签],
        r标签2 = json.decodeFromString(row[CompleteAnimeDatas.r标签2]),
        r标题V2 = row[CompleteAnimeDatas.r标题V2],
        r简介 = row[CompleteAnimeDatas.r简介],
        r简介Br = row[CompleteAnimeDatas.r简介_br],
        r系列 = row[CompleteAnimeDatas.r系列],
        r网盘资源 = row[CompleteAnimeDatas.r网盘资源],
        r网盘资源2 = json.decodeFromString(row[CompleteAnimeDatas.r网盘资源2]),
        r视频尺寸 = row[CompleteAnimeDatas.r视频尺寸],
        r资源类型 = row[CompleteAnimeDatas.r资源类型],
        r首播季度 = row[CompleteAnimeDatas.r首播季度],
        r首播年份 = row[CompleteAnimeDatas.r首播年份],
        r首播时间 = row[CompleteAnimeDatas.r首播时间],
        r相关动画 = json.decodeFromString(row[CompleteAnimeDatas.r相关动画]),
        r猜你喜欢 = json.decodeFromString(row[CompleteAnimeDatas.r猜你喜欢])
    )

    override suspend fun allCompleteAnimeData(): List<CompleteAnimeData> = dbQuery {
        CompleteAnimeDatas.selectAll().map(::resultRowToCompleteAnimeData)
    }

    override suspend fun completeAnimeData(aid: String): CompleteAnimeData? = dbQuery {
        CompleteAnimeDatas.select { CompleteAnimeDatas.animeId eq aid }.map(::resultRowToCompleteAnimeData)
            .firstOrNull()
    }

    override suspend fun addNewCompleteAnimeData(
        completeAnimeData: CompleteAnimeData
    ): CompleteAnimeData? = dbQuery {
        CompleteAnimeDatas.insert {
            it[animeId] = completeAnimeData.aID
            it[collectCnt] = completeAnimeData.collectCnt.toString()
            it[commentCnt] = completeAnimeData.commentCnt.toString()
            it[dEFPLAYINDEX] = completeAnimeData.dEFPLAYINDEX
            it[filePath] = completeAnimeData.filePath
            it[lastModified] = completeAnimeData.lastModified
            it[modifiedTime] = completeAnimeData.modifiedTime.toString()
            it[rankCnt] = completeAnimeData.rankCnt.toString()
            it[r其它名称] = completeAnimeData.r其它名称
            it[r制作公司] = completeAnimeData.r制作公司
            it[r剧情类型] = completeAnimeData.r剧情类型
            it[r剧情类型2] = json.encodeToString(completeAnimeData.r剧情类型2)
            it[r动画名称] = completeAnimeData.r动画名称
            it[r动画种类] = completeAnimeData.r动画种类
            it[r原作] = completeAnimeData.r原作
            it[r原版名称] = completeAnimeData.r原版名称
            it[r在线播放All] = json.encodeToString(completeAnimeData.r在线播放All)
            it[r地区] = completeAnimeData.r地区
            it[r备用] = completeAnimeData.r备用
            it[r字母索引] = completeAnimeData.r字母索引
            it[r官方网站] = completeAnimeData.r官方网站
            it[r封面图] = completeAnimeData.r封面图
            it[r封面图小] = completeAnimeData.r封面图小
            it[r推荐星级] = completeAnimeData.r推荐星级.toString()
            it[r播放状态] = completeAnimeData.r播放状态
            it[r新番标题] = completeAnimeData.r新番标题
            it[r更新时间] = completeAnimeData.r更新时间
            it[r更新时间str] = completeAnimeData.r更新时间str
            it[r更新时间str2] = completeAnimeData.r更新时间str2
            it[r更新时间unix] = completeAnimeData.r更新时间unix.toString()
            it[r标签] = completeAnimeData.r标签
            it[r标签2] = json.encodeToString(completeAnimeData.r标签2)
            it[r标题V2] = completeAnimeData.r标题V2
            it[r简介] = completeAnimeData.r简介
            it[r简介_br] = completeAnimeData.r简介Br
            it[r系列] = completeAnimeData.r系列
            it[r网盘资源] = completeAnimeData.r网盘资源
            it[r网盘资源2] = json.encodeToString(completeAnimeData.r网盘资源2)
            it[r视频尺寸] = completeAnimeData.r视频尺寸
            it[r资源类型] = completeAnimeData.r资源类型
            it[r首播季度] = completeAnimeData.r首播季度
            it[r首播年份] = completeAnimeData.r首播年份
            it[r首播时间] = completeAnimeData.r首播时间
            it[r相关动画] = json.encodeToString(completeAnimeData.r相关动画)
            it[r猜你喜欢] = json.encodeToString(completeAnimeData.r猜你喜欢)
        }.resultedValues?.singleOrNull()?.let(::resultRowToCompleteAnimeData)
    }

    override suspend fun updateCompleteAnimeData(
        aid: String,
        completeAnimeData: CompleteAnimeData
    ): Boolean = dbQuery {
        try {
            CompleteAnimeDatas.update {
                it[animeId] = completeAnimeData.aID
                it[collectCnt] = completeAnimeData.collectCnt.toString()
                it[commentCnt] = completeAnimeData.commentCnt.toString()
                it[dEFPLAYINDEX] = completeAnimeData.dEFPLAYINDEX
                it[filePath] = completeAnimeData.filePath
                it[lastModified] = completeAnimeData.lastModified
                it[modifiedTime] = completeAnimeData.modifiedTime.toString()
                it[rankCnt] = completeAnimeData.rankCnt.toString()
                it[r其它名称] = completeAnimeData.r其它名称
                it[r制作公司] = completeAnimeData.r制作公司
                it[r剧情类型] = completeAnimeData.r剧情类型
                it[r剧情类型2] = json.encodeToString(completeAnimeData.r剧情类型2)
                it[r动画名称] = completeAnimeData.r动画名称
                it[r动画种类] = completeAnimeData.r动画种类
                it[r原作] = completeAnimeData.r原作
                it[r原版名称] = completeAnimeData.r原版名称
                it[r在线播放All] = json.encodeToString(completeAnimeData.r在线播放All)
                it[r地区] = completeAnimeData.r地区
                it[r备用] = completeAnimeData.r备用
                it[r字母索引] = completeAnimeData.r字母索引
                it[r官方网站] = completeAnimeData.r官方网站
                it[r封面图] = completeAnimeData.r封面图
                it[r封面图小] = completeAnimeData.r封面图小
                it[r推荐星级] = completeAnimeData.r推荐星级.toString()
                it[r播放状态] = completeAnimeData.r播放状态
                it[r新番标题] = completeAnimeData.r新番标题
                it[r更新时间] = completeAnimeData.r更新时间
                it[r更新时间str] = completeAnimeData.r更新时间str
                it[r更新时间str2] = completeAnimeData.r更新时间str2
                it[r更新时间unix] = completeAnimeData.r更新时间unix.toString()
                it[r标签] = completeAnimeData.r标签
                it[r标签2] = json.encodeToString(completeAnimeData.r标签2)
                it[r标题V2] = completeAnimeData.r标题V2
                it[r简介] = completeAnimeData.r简介
                it[r简介_br] = completeAnimeData.r简介Br
                it[r系列] = completeAnimeData.r系列
                it[r网盘资源] = completeAnimeData.r网盘资源
                it[r网盘资源2] = json.encodeToString(completeAnimeData.r网盘资源2)
                it[r视频尺寸] = completeAnimeData.r视频尺寸
                it[r资源类型] = completeAnimeData.r资源类型
                it[r首播季度] = completeAnimeData.r首播季度
                it[r首播年份] = completeAnimeData.r首播年份
                it[r首播时间] = completeAnimeData.r首播时间
                it[r相关动画] = json.encodeToString(completeAnimeData.r相关动画)
                it[r猜你喜欢] = json.encodeToString(completeAnimeData.r猜你喜欢)
            } > 0
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun removeCompleteAnimeData(aid: String): Boolean = dbQuery {
        CompleteAnimeDatas.deleteWhere { CompleteAnimeDatas.animeId eq aid } > 0
    }

    private fun resultRowToCloudDiskLink(row: ResultRow) = CloudDiskLink(
        animeId = row[CloudDiskLinks.animeId],
        cloudDiskLinkList = json.decodeFromString(row[CloudDiskLinks.cloudDiskLinkList])
    )

    override suspend fun allCloudDiskLink(): List<CloudDiskLink> = dbQuery {
        CloudDiskLinks.selectAll().map(::resultRowToCloudDiskLink)
    }

    override suspend fun cloudDiskLink(aid: String): CloudDiskLink? = dbQuery {
        CloudDiskLinks.select { CloudDiskLinks.animeId eq aid }.map { resultRowToCloudDiskLink(it) }.firstOrNull()
    }

    override suspend fun addNewCloudDiskLink(cloudDiskLink: CloudDiskLink): CloudDiskLink? = dbQuery {
        CloudDiskLinks.insert {
            it[animeId] = cloudDiskLink.animeId
            it[cloudDiskLinkList] = json.encodeToString(cloudDiskLink.cloudDiskLinkList)
        }.resultedValues?.singleOrNull()?.let(::resultRowToCloudDiskLink)
    }

    override suspend fun updateCloudDiskLink(aid: String, cloudDiskLinkList: MutableList<AnimeInfosEntity.AniInfoEntity.R网盘资源2Entity>): Boolean = dbQuery {
        try {
            CloudDiskLinks.update({ CloudDiskLinks.animeId eq aid }) {
                it[CloudDiskLinks.cloudDiskLinkList] = json.encodeToString(cloudDiskLinkList)
            } > 0
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun removeCloudDiskLink(aid: String): Boolean = dbQuery {
        CloudDiskLinks.deleteWhere { CloudDiskLinks.animeId eq aid } > 0
    }

    private fun resultRowToPlayerNumber(row: ResultRow) = PlayerNumber(
        aid = row[PlayerNumbers.aid],
        playList = json.decodeFromString(row[PlayerNumbers.playList])
    )

    override suspend fun allPlayList(): List<PlayerNumber> = dbQuery {
        PlayerNumbers.selectAll().map { resultRowToPlayerNumber(it) }
    }

    override suspend fun playLists(aid: String): PlayerNumber? = dbQuery {
        PlayerNumbers.select { PlayerNumbers.aid eq aid }.map { resultRowToPlayerNumber(it) }.firstOrNull()
    }

    override suspend fun addNewPlayList(
        aid: String,
        playList: MutableList<MutableList<AnimeInfosEntity.AniInfoEntity.R在线播放AllEntity>>,
    ): PlayerNumber? = dbQuery {
        PlayerNumbers.insert {
            it[PlayerNumbers.aid] = aid
            it[PlayerNumbers.playList] = json.encodeToString(playList)
        }.resultedValues?.singleOrNull()?.let(::resultRowToPlayerNumber)
    }

    override suspend fun updatePlayList(
        aid: String,
        newPlayList: MutableList<MutableList<AnimeInfosEntity.AniInfoEntity.R在线播放AllEntity>>,
    ): Boolean = dbQuery {
        try {
            PlayerNumbers.update({ PlayerNumbers.aid eq aid }) {
                it[playList] = json.encodeToString(newPlayList)
            } > 0
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun removePlayList(aid: String): Boolean = dbQuery {
        PlayerNumbers.deleteWhere { PlayerNumbers.aid eq aid } > 0
    }

}

val dao: DAOFacade = DAOFacadeImpl().apply {
    runBlocking {
        if (allUsers().isEmpty()) {
            addNewUser(User(
                id = 0,
                name = "default",
                pass = "123456789",
                email = "",
                portraitUrl = defaultPortraitUrl,
                favoriteData = User.FavoriteDataDTO(),
                historyData = User.HistoryDataDTO(),
                chaseAnimeData = User.ChaseAnimeDataDTO(),
                created_at = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            )
        }

        if (allSimpleAnimeData().isEmpty()) {
            addNewSimpleAnimeData(SimpleAnimeData())
        }

        if (allCompleteAnimeData().isEmpty()) {
            addNewCompleteAnimeData(CompleteAnimeData())
        }

        if (allPlayList().isEmpty()) {
            addNewPlayList("1", mutableListOf(mutableListOf()))
        }

        if (allCloudDiskLink().isEmpty()) {
            addNewCloudDiskLink(CloudDiskLink("1", mutableListOf()))
        }

    }
}
