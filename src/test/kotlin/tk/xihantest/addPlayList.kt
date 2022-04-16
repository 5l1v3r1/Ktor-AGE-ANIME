package tk.xihantest

import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.junit.Test
import tk.xihantest.dao.dao
import tk.xihantest.plugins.json
import tk.xihantest.utils.sync.entity.AnimeInfosEntity

class addPlayList {


    @Test
    fun addPlayList() = testApplication{
        val playLsits: MutableList<MutableList<AnimeInfosEntity.AniInfoEntity.R在线播放AllEntity>> = mutableListOf()

        val playList: MutableList<AnimeInfosEntity.AniInfoEntity.R在线播放AllEntity> = mutableListOf()
        // for循环 1到26
//        for (i in 1..26) {
//            playList.add(AnimeInfosEntity.AniInfoEntity.R在线播放AllEntity(
//                title = "第$i" +"集",
//                titleL = "第$i" +"集",
//                playId = "<play>web_mp4</play>",
//                playVid = "https://lingshulian.com/s/t/",
//                epName = "$i",
//            ))
//        }
//        playLsits.add(playList)
        println()



    }
}