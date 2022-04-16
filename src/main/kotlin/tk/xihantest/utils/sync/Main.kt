package tk.xihantest.utils.sync


import cn.xihan.gson.factory.GsonFactory
import cn.xihan.http.EasyConfig
import cn.xihan.http.EasyHttp
import cn.xihan.http.config.IRequestInterceptor
import cn.xihan.http.listener.OnHttpListener
import cn.xihan.http.model.HttpHeaders
import cn.xihan.http.model.HttpParams
import cn.xihan.http.model.RequestHandler
import cn.xihan.http.request.HttpRequest
import com.google.gson.Gson
import io.ktor.server.application.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import tk.xihantest.dao.dao
import tk.xihantest.models.CompleteAnimeData
import tk.xihantest.models.SimpleAnimeData
import tk.xihantest.plugins.json
import tk.xihantest.utils.Utils
import tk.xihantest.utils.isUrl
import tk.xihantest.utils.sync.Config.api
import tk.xihantest.utils.sync.Config.path
import tk.xihantest.utils.sync.Config.主页列表
import tk.xihantest.utils.sync.Config.其他
import tk.xihantest.utils.sync.Config.推荐
import tk.xihantest.utils.sync.Config.目录
import tk.xihantest.utils.sync.Config.默认目录
import tk.xihantest.utils.sync.entity.AnimeInfosEntity
import tk.xihantest.utils.sync.entity.CatalogEntity
import tk.xihantest.utils.sync.entity.MainEntity
import tk.xihantest.utils.writeJsonFile
import tk.xihantest.utils.writeTxtFile
import java.io.File
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit


object Main {

    var AllCnt: String = "0"
    var URL: String = ""
    var Cs = 0
    var Error = 0
    var isFirst: Boolean = true

    var Mainstart = System.currentTimeMillis()
    var start = System.currentTimeMillis()

    //var errorList: MutableList<String> = ArrayList()
    var errorRetryCount = 0

    val gson: Gson = GsonFactory.getSingletonGson()

    fun start() {
        if (isFirst) {
            isFirst = false
            val errorTxt = File(path + "error.txt")
            errorTxt.writeText("")
            val logTxt = File(path + "log.txt")
            logTxt.writeText("")
        } else {
            cleanAll()
        }

        initEasyHttp()
        initTime()


        getAllData()


        /**
         * 主页列表
         */
        EasyHttp.get()
            .server(主页列表)
            .api("")
            .request(object : OnHttpListener<JSONObject> {
                var start: Long = 0

                override fun onStart(call: Call) {
                    start = System.currentTimeMillis()
                }

                override fun onSucceed(result: JSONObject) {
                    try {
                        result.remove("Tip")
                        result.toString().writeJsonFile("home-list")
                        val time = System.currentTimeMillis() - start
                        ("----主页列表-同步成功 用时: " + if (time > 1000) (time / 1000).toString() + "秒" else time.toString() + "毫秒").writeTxtFile()

                    } catch (e: Exception) {
                        ("----主页列表-同步失败 日志: " + e.message).writeTxtFile()
                    }
                }

                override fun onFail(e: Exception) {
                    ("----主页列表-同步失败 日志: " + e.message).writeTxtFile()
                }


            })

        /**
         * 推荐列表
         */
        EasyHttp.get()
            .server(推荐)
            .api("")
            .request(object : OnHttpListener<JSONObject> {
                var start: Long = 0

                override fun onStart(call: Call) {
                    start = System.currentTimeMillis()
                }

                override fun onSucceed(result: JSONObject) {
                    try {
                        result.remove("Tip")
                        result.toString().writeJsonFile("recommend")
                        val time = System.currentTimeMillis() - start
                        ("----推荐-同步成功 用时: " + if (time > 1000) (time / 1000).toString() + "秒" else time.toString() + "毫秒").writeTxtFile()
                    } catch (e: Exception) {
                        ("----推荐-同步失败,日志:" + e.message).writeTxtFile()
                    }

                }

                override fun onFail(e: Exception) {
                    ("----推荐-同步失败,日志:" + e.message).writeTxtFile()
                }
            })


    }

    private fun cleanAll(aid: String = "") {
        Cs = 0
        //errorList.clear()
        EasyHttp.cancel()
        if (!Utils.isNull(aid)) {
            val aidTxt = File("$path$aid.txt")
            aidTxt.writeText("")
        } else {
            val infoTxt = File(path + "info.txt")
            infoTxt.writeText("")
            val errorTxt = File(path + "error.txt")
            errorTxt.writeText("")
            val logTxt = File(path + "log.txt")
            errorTxt.writeText("")
        }

    }


    private fun getAllData() {
        EasyHttp.get()
            .server(目录)
            .api("")
            .tag("")
            .request(object : OnHttpListener<CatalogEntity> {
                override fun onSucceed(result: CatalogEntity) {
                    AllCnt = result.allCntDTO.toString()
                    "番剧总数:$AllCnt".writeTxtFile()

                    URL = "$默认目录$AllCnt"

                    getData(URL)

                    val 地区arrList: MutableList<String> = result.labelsRegionDTO.toMutableList()
                    地区arrList.remove("region")
                    地区arrList.remove("地区")

                    val 版本arrList: MutableList<String> = result.labelsGenreDTO.toMutableList()
                    版本arrList.remove("genre")
                    版本arrList.remove("版本")

                    val 首字母arrList: MutableList<String> = result.labelsLetterDTO.toMutableList()
                    首字母arrList.remove("letter")
                    首字母arrList.remove("首字母")

                    val 年份arrList: MutableList<String> = result.labelsYearDTO.toMutableList()
                    年份arrList.remove("year")
                    年份arrList.remove("年份")
                    年份arrList.add("2022")

                    val 季度arrList: MutableList<String> = result.labelsSeasonDTO.toMutableList()
                    季度arrList.remove("season")
                    季度arrList.remove("季度")

                    val 状态arrList: MutableList<String> = result.labelsStatusDTO.toMutableList()
                    状态arrList.remove("status")
                    状态arrList.remove("状态")

                    val 类型arrList: MutableList<String> = result.labelsLabelDTO.toMutableList()
                    类型arrList.remove("label")
                    类型arrList.remove("类型")

                    val 资源arrList: MutableList<String> = result.labelsResourceDTO.toMutableList()
                    资源arrList.remove("resource")
                    资源arrList.remove("资源")

                    val 排序arrList: MutableList<String> = result.labelsOrderDTO.toMutableList()
                    排序arrList.remove("order")
                    排序arrList.remove("排序")

                    val catalogEntity2 = CatalogEntity()
                    catalogEntity2.labelsRegionDTO = 地区arrList
                    catalogEntity2.labelsGenreDTO = 版本arrList
                    catalogEntity2.labelsLetterDTO = 首字母arrList
                    catalogEntity2.labelsYearDTO = 年份arrList
                    catalogEntity2.labelsSeasonDTO = 季度arrList
                    catalogEntity2.labelsStatusDTO = 状态arrList
                    catalogEntity2.labelsLabelDTO = 类型arrList
                    catalogEntity2.labelsResourceDTO = 资源arrList
                    catalogEntity2.labelsOrderDTO = 排序arrList

                    setMainEntity(catalogEntity2)

                }

                override fun onFail(e: Exception?) {
                    ("----同步失败-将在半小时后重试").writeTxtFile()
                    EasyHttp.cancel()
                }

            })

    }

    private fun setMainEntity(catalogEntity: CatalogEntity) {
        EasyHttp.get()
            .server("http://192.168.43.110/AGE-API/Main-8")
            .api("")
            .request(object : OnHttpListener<MainEntity> {
                override fun onSucceed(result: MainEntity) {
                    val labelsDTO = result.mainDTO.labelsDTO
                    labelsDTO.labelsRegionDTO = catalogEntity.labelsRegionDTO
                    labelsDTO.labelsGenreDTO = catalogEntity.labelsGenreDTO
                    labelsDTO.labelsLetterDTO = catalogEntity.labelsLetterDTO
                    labelsDTO.labelsYearDTO = catalogEntity.labelsYearDTO
                    labelsDTO.labelsSeasonDTO = catalogEntity.labelsSeasonDTO
                    labelsDTO.labelsStatusDTO = catalogEntity.labelsStatusDTO
                    labelsDTO.labelsLabelDTO = catalogEntity.labelsLabelDTO
                    labelsDTO.labelsResourceDTO = catalogEntity.labelsResourceDTO
                    labelsDTO.labelsOrderDTO = catalogEntity.labelsOrderDTO

                    result.mainDTO.labelsDTO = labelsDTO

                    EasyHttp.get()
                        .server(其他)
                        .api("")
                        .request(object : OnHttpListener<JSONObject> {
                            var start: Long = 0

                            override fun onStart(call: Call) {
                                start = System.currentTimeMillis()
                            }

                            override fun onSucceed(json: JSONObject) {
                                val allCat: String = json.optString("AllCnt")
                                if (allCat.isNotEmpty()) {
                                    getAllAid(allCat, start, result)
                                }
                            }

                            override fun onFail(e: Exception) {

                            }

                        })

                }

                override fun onFail(e: Exception) {

                }
            })
    }

    private fun getAllAid(allCat: String, start: Long, mainEntity: MainEntity) {
        EasyHttp.get()
            .server(其他 + allCat)
            .api("")
            .request(object : OnHttpListener<JSONObject> {
                override fun onSucceed(result: JSONObject) {
                    try {
                        val AniPreLs = result.optString("AniPreL")
                        val sb = StringBuilder()
                        val AniPreLArray = JSONArray(AniPreLs)
                        val aidInfo: MutableList<String> = ArrayList()
                        for (i in 0 until AniPreLArray.length()) {
                            val AniPreLArrayJb = AniPreLArray.optJSONObject(i)
                            aidInfo.add(AniPreLArrayJb.optString("AID"))
                            //System.out.println("AniPreLArrayJb:" + AniPreLArrayJb.optString("AID") + "\ni:" + i);
                        }
                        if (aidInfo.isNotEmpty()) {
                            mainEntity.mainDTO.aidInDTO = aidInfo
                            gson.toJson(mainEntity).writeJsonFile("Main-8")

                            var time = System.currentTimeMillis() - start
                            if (time > 1000) {
                                time /= 1000
                            }
                            ("----配置-同步成功 用时: " + if (time < 1000) time.toString() + "秒" else time.toString() + "毫秒").writeTxtFile()
                        }
                    } catch (e: Exception) {
                        ("-----配置-同步失败-30分钟后重试---- 日志:" + e.message).writeTxtFile()
                    }


                }

                override fun onFail(e: Exception) {

                }

            })

    }

    private fun getData(url: String) {
        EasyHttp.get()
            .server(url)
            .api("")
            .request(object : OnHttpListener<CatalogEntity> {
                override fun onSucceed(result: CatalogEntity) {
                    val aniPreLDTOList: List<CatalogEntity.AniPreL> = result.aniPreLDTO
                    gson.toJson(result).writeJsonFile("allAnimeData")

                    runBlocking {
                        val animeDataList = dao.allSimpleAnimeData()
                        if (aniPreLDTOList.isNotEmpty() && animeDataList.isNotEmpty()) {
                            aniPreLDTOList.forEach { aniPreL ->
                                animeDataList.find { it.animeId == aniPreL.aIDDTO }?.let{ simpleAnimeData ->
                                    dao.updateSimpleAnimeData(aniPreL.aIDDTO,simpleAnimeData)
                                } ?: run {
                                    try {
                                        val animeInfo = gson.toJson(aniPreL)
                                        val result = JSONObject(animeInfo)
                                        dao.addNewSimpleAnimeData(SimpleAnimeData(aniPreL.aIDDTO,
                                            result.optString("R其他名称") ?: result.optString("R其它名称"),
                                            result.optString("R制作公司"),
                                            json.decodeFromString(result.optString("R剧情类型")),
                                            result.optString("R动画名称"),
                                            result.optString("R动画种类"),
                                            result.optString("R原作"),
                                            result.optString("R原版名称"),
                                            result.optString("R封面图小"),
                                            result.optString("R播放状态"),
                                            result.optString("R新番标题"),
                                            result.optString("R简介"),
                                            result.optString("R首播时间")
                                        ))
                                    } catch (e: Exception) {
                                        ("发生错误的AID:${aniPreL.aIDDTO}").writeTxtFile(fileName = "error.txt")
                                        ("发生错误的AID:${aniPreL.aIDDTO}" + "\n错误日志:" + e.message).writeTxtFile(fileName = "log.txt")
                                    }

                                }
                            }
                        }
                    }

                    if (aniPreLDTOList.isNotEmpty()) {
                        val stringBuffer = StringBuilder()
                        start = System.currentTimeMillis()
                        //getAniInfos("20100015")
                        aniPreLDTOList.forEach { it2 ->
                            val aid: String = it2.aIDDTO
                            stringBuffer.append(aid).append("\n")
                            getAniInfos(aid)
                        }
                        if (stringBuffer.isNotEmpty()) {
                            stringBuffer.toString().writeJsonFile("aids")
                        }
                    }


                }

                override fun onFail(e: Exception) {
                    //println("错误日志:" + e.message)

                }

                override fun onEnd(call: Call) {
                    /*
                    if (errorRetryCount >= 2) {
                        if (errorList.isNotEmpty()) {
                            errorRetryCount++
                            ("----出现: " + errorList.size + " 个校验失败----").writeTxtFile()
                            errorList.forEach { aid ->
                                getAniInfos(aid)
                            }
                        }
                    }

                     */
                }

            })

    }

    private fun getAniInfos(aid: String) {
        EasyHttp.get()
            .server(api + aid)
            .api("")
            .request(object : OnHttpListener<JSONObject> {

                override fun onSucceed(result: JSONObject) {
                    /*
                    if (errorList.isNotEmpty()) {
                        errorList.removeIf { it == aid }
                    }

                     */
                    try {
                        result.remove("Tip")
                        try {
                            val animeInfo = result.optJSONObject("AniInfo")
                            animeInfo.remove("R在线播放")
                            animeInfo.remove("R在线播放2")
                            animeInfo.remove("R在线播放3")
                            animeInfo.remove("R在线播放4")
                        } catch (_: JSONException) {
                        }

                        Cs++
                        "已运行:$Cs".writeTxtFile()
                        if ((Cs + 1) == AllCnt.toInt()) {
                            "----同步数据完成----".writeTxtFile()
                            errorRetryCount = 0
                            val end = Instant.now()
                            var timeElapsed = System.currentTimeMillis() - start
                            if (timeElapsed > 1000) {
                                timeElapsed /= 1000
                            }
                            ("----用时: " + if (timeElapsed < 1000) timeElapsed.toString() + "秒" else timeElapsed.toString() + "毫秒").writeTxtFile()

                            EasyHttp.cancel()

                        }
                        runBlocking {
                            val animeInfo = json.decodeFromString<AnimeInfosEntity>(result.toString())
                            val allPlayList = dao.allPlayList()
                            val allCloudDiskLink = dao.allCloudDiskLink()
                            if (allPlayList.isNotEmpty() && allCloudDiskLink.isNotEmpty()) {
                                allPlayList.find { it.aid == aid }?.let {
                                    animeInfo.aniInfo.r在线播放All = it.playList
                                }// ?: analysisPlayList(animeInfo)

                                allCloudDiskLink.find { it.animeId == aid }?.let {
                                    animeInfo.aniInfo.r网盘资源2 = it.cloudDiskLinkList
                                }

                                Cs++
                                "已运行:$Cs".writeTxtFile()
                                if ((Cs + 1) == AllCnt.toInt()) {
                                    "----同步数据完成----".writeTxtFile()
                                    errorRetryCount = 0
                                    val end = Instant.now()
                                    var timeElapsed = System.currentTimeMillis() - start
                                    if (timeElapsed > 1000) {
                                        timeElapsed /= 1000
                                    }
                                    ("----用时: " + if (timeElapsed < 1000) timeElapsed.toString() + "秒" else timeElapsed.toString() + "毫秒").writeTxtFile()
                                    EasyHttp.cancel()
                                }
                                json.encodeToString(animeInfo).writeJsonFile(aid)

                                runBlocking {
                                    val allCompleteAnimeDataList = dao.allCompleteAnimeData()
                                    if (allCompleteAnimeDataList.isNotEmpty()) {
                                        allCompleteAnimeDataList.find { it.aID == aid }?.let { completeAnimeData ->
                                            /**
                                            if (completeAnimeData.r更新时间unix != animeInfo.aniInfo.r更新时间unix) {
                                                try {
                                                    dao.updateCompleteAnimeData(aid,
                                                        CompleteAnimeData(animeInfo.aniInfo.aID,
                                                            animeInfo.aniInfo.collectCnt,
                                                            animeInfo.aniInfo.commentCnt,
                                                            animeInfo.aniInfo.dEFPLAYINDEX,
                                                            animeInfo.aniInfo.filePath,
                                                            animeInfo.aniInfo.lastModified,
                                                            animeInfo.aniInfo.modifiedTime,
                                                            animeInfo.aniInfo.rankCnt,
                                                            animeInfo.aniInfo.r其它名称,
                                                            animeInfo.aniInfo.r制作公司,
                                                            animeInfo.aniInfo.r剧情类型,
                                                            animeInfo.aniInfo.r剧情类型2,
                                                            animeInfo.aniInfo.r动画名称,
                                                            animeInfo.aniInfo.r动画种类,
                                                            animeInfo.aniInfo.r原作,
                                                            animeInfo.aniInfo.r原版名称,
                                                            animeInfo.aniInfo.r在线播放All,
                                                            animeInfo.aniInfo.r地区,
                                                            animeInfo.aniInfo.r备用,
                                                            animeInfo.aniInfo.r字母索引,
                                                            animeInfo.aniInfo.r官方网站,
                                                            animeInfo.aniInfo.r封面图,
                                                            animeInfo.aniInfo.r封面图小,
                                                            animeInfo.aniInfo.r推荐星级,
                                                            animeInfo.aniInfo.r播放状态,
                                                            animeInfo.aniInfo.r新番标题,
                                                            animeInfo.aniInfo.r更新时间,
                                                            animeInfo.aniInfo.r更新时间str,
                                                            animeInfo.aniInfo.r更新时间str2,
                                                            animeInfo.aniInfo.r更新时间unix,
                                                            animeInfo.aniInfo.r标签,
                                                            animeInfo.aniInfo.r标签2,
                                                            animeInfo.aniInfo.r标题V2,
                                                            animeInfo.aniInfo.r简介,
                                                            animeInfo.aniInfo.r简介Br,
                                                            animeInfo.aniInfo.r系列,
                                                            animeInfo.aniInfo.r网盘资源,
                                                            animeInfo.aniInfo.r网盘资源2,
                                                            animeInfo.aniInfo.r视频尺寸,
                                                            animeInfo.aniInfo.r资源类型,
                                                            animeInfo.aniInfo.r首播季度,
                                                            animeInfo.aniInfo.r首播年份,
                                                            animeInfo.aniInfo.r首播时间,
                                                            animeInfo.aniPreRel,
                                                            animeInfo.aniPreSim
                                                        ))
                                                } catch (e: Exception) {
                                                    ("发生错误的AID:${aid}").writeTxtFile(fileName = "error.txt")
                                                    ("发生错误的AID:${aid}" + "\n错误日志:" + e.message).writeTxtFile(fileName = "log.txt")
                                                }
                                            }*/

                                            try {
                                                dao.updateCompleteAnimeData(aid,
                                                    CompleteAnimeData(animeInfo.aniInfo.aID,
                                                        animeInfo.aniInfo.collectCnt,
                                                        animeInfo.aniInfo.commentCnt,
                                                        animeInfo.aniInfo.dEFPLAYINDEX,
                                                        animeInfo.aniInfo.filePath,
                                                        animeInfo.aniInfo.lastModified,
                                                        animeInfo.aniInfo.modifiedTime,
                                                        animeInfo.aniInfo.rankCnt,
                                                        animeInfo.aniInfo.r其它名称,
                                                        animeInfo.aniInfo.r制作公司,
                                                        animeInfo.aniInfo.r剧情类型,
                                                        animeInfo.aniInfo.r剧情类型2,
                                                        animeInfo.aniInfo.r动画名称,
                                                        animeInfo.aniInfo.r动画种类,
                                                        animeInfo.aniInfo.r原作,
                                                        animeInfo.aniInfo.r原版名称,
                                                        animeInfo.aniInfo.r在线播放All,
                                                        animeInfo.aniInfo.r地区,
                                                        animeInfo.aniInfo.r备用,
                                                        animeInfo.aniInfo.r字母索引,
                                                        animeInfo.aniInfo.r官方网站,
                                                        animeInfo.aniInfo.r封面图,
                                                        animeInfo.aniInfo.r封面图小,
                                                        animeInfo.aniInfo.r推荐星级,
                                                        animeInfo.aniInfo.r播放状态,
                                                        animeInfo.aniInfo.r新番标题,
                                                        animeInfo.aniInfo.r更新时间,
                                                        animeInfo.aniInfo.r更新时间str,
                                                        animeInfo.aniInfo.r更新时间str2,
                                                        animeInfo.aniInfo.r更新时间unix,
                                                        animeInfo.aniInfo.r标签,
                                                        animeInfo.aniInfo.r标签2,
                                                        animeInfo.aniInfo.r标题V2,
                                                        animeInfo.aniInfo.r简介,
                                                        animeInfo.aniInfo.r简介Br,
                                                        animeInfo.aniInfo.r系列,
                                                        animeInfo.aniInfo.r网盘资源,
                                                        animeInfo.aniInfo.r网盘资源2,
                                                        animeInfo.aniInfo.r视频尺寸,
                                                        animeInfo.aniInfo.r资源类型,
                                                        animeInfo.aniInfo.r首播季度,
                                                        animeInfo.aniInfo.r首播年份,
                                                        animeInfo.aniInfo.r首播时间,
                                                        animeInfo.aniPreRel,
                                                        animeInfo.aniPreSim
                                                    ))
                                            } catch (e: Exception) {
                                                ("发生错误的AID:${aid}").writeTxtFile(fileName = "error.txt")
                                                ("发生错误的AID:${aid}" + "\n错误日志:" + e.message).writeTxtFile(fileName = "log.txt")
                                            }

                                        } ?: run {
                                            try {
                                                dao.addNewCompleteAnimeData(CompleteAnimeData(animeInfo.aniInfo.aID,
                                                    animeInfo.aniInfo.collectCnt,
                                                    animeInfo.aniInfo.commentCnt,
                                                    animeInfo.aniInfo.dEFPLAYINDEX,
                                                    animeInfo.aniInfo.filePath,
                                                    animeInfo.aniInfo.lastModified,
                                                    animeInfo.aniInfo.modifiedTime,
                                                    animeInfo.aniInfo.rankCnt,
                                                    animeInfo.aniInfo.r其它名称,
                                                    animeInfo.aniInfo.r制作公司,
                                                    animeInfo.aniInfo.r剧情类型,
                                                    animeInfo.aniInfo.r剧情类型2,
                                                    animeInfo.aniInfo.r动画名称,
                                                    animeInfo.aniInfo.r动画种类,
                                                    animeInfo.aniInfo.r原作,
                                                    animeInfo.aniInfo.r原版名称,
                                                    animeInfo.aniInfo.r在线播放All,
                                                    animeInfo.aniInfo.r地区,
                                                    animeInfo.aniInfo.r备用,
                                                    animeInfo.aniInfo.r字母索引,
                                                    animeInfo.aniInfo.r官方网站,
                                                    animeInfo.aniInfo.r封面图,
                                                    animeInfo.aniInfo.r封面图小,
                                                    animeInfo.aniInfo.r推荐星级,
                                                    animeInfo.aniInfo.r播放状态,
                                                    animeInfo.aniInfo.r新番标题,
                                                    animeInfo.aniInfo.r更新时间,
                                                    animeInfo.aniInfo.r更新时间str,
                                                    animeInfo.aniInfo.r更新时间str2,
                                                    animeInfo.aniInfo.r更新时间unix,
                                                    animeInfo.aniInfo.r标签,
                                                    animeInfo.aniInfo.r标签2,
                                                    animeInfo.aniInfo.r标题V2,
                                                    animeInfo.aniInfo.r简介,
                                                    animeInfo.aniInfo.r简介Br,
                                                    animeInfo.aniInfo.r系列,
                                                    animeInfo.aniInfo.r网盘资源,
                                                    animeInfo.aniInfo.r网盘资源2,
                                                    animeInfo.aniInfo.r视频尺寸,
                                                    animeInfo.aniInfo.r资源类型,
                                                    animeInfo.aniInfo.r首播季度,
                                                    animeInfo.aniInfo.r首播年份,
                                                    animeInfo.aniInfo.r首播时间,
                                                    animeInfo.aniPreRel,
                                                    animeInfo.aniPreSim
                                                ))?:("无法入库发生错误的AID:${aid}").writeTxtFile(fileName = "error.txt")
                                            } catch (e: Exception) {
                                                ("发生错误的AID:${aid}").writeTxtFile(fileName = "error.txt")
                                                ("发生错误的AID:${aid}" + "\n错误日志:" + e.message).writeTxtFile(fileName = "log.txt")
                                            }

                                        }
                                    }

                                }

                            }

                        }

                    } catch (e: Exception) {
                        ("发生错误的AID:$aid").writeTxtFile(fileName = "error.txt")
                        ("发生错误的AID:$aid" + "\n错误日志:" + e.message).writeTxtFile(fileName = "log.txt")
                    }
                }

                override fun onFail(e: Exception) {
                    println("发生错误的AID:$aid")
                    println("错误日志:" + e.message)
                    //errorList.find { it == aid } ?: errorList.add(aid)

                }

                override fun onEnd(call: Call?) {
                    /*
                    if (errorRetryCount < 2) {
                        if (errorList.isNotEmpty()) {
                            errorRetryCount++
                            ("----出现: " + errorList.size + " 个校验失败----").writeTxtFile()
                            errorList.forEach { aid ->
                                getAniInfos(aid)
                            }
                        }
                    }

                     */
                }
            })

    }

    private fun analysisPlayList(animeInfo: AnimeInfosEntity) {
        val lists: MutableList<MutableList<AnimeInfosEntity.AniInfoEntity.R在线播放AllEntity>> = mutableListOf()
        val list: MutableList<AnimeInfosEntity.AniInfoEntity.R在线播放AllEntity> = mutableListOf()
        val r在线播放AllList = animeInfo.aniInfo.r在线播放All
        r在线播放AllList.forEach { lists ->
            lists.forEach { it2 ->
                if (it2.playVid.isUrl()) {
                    list.find { it.title == it2.title } ?: list.add(it2)
                } else if (it2.playId == "<play>ttm3p</play>") {
                    list.find { it.title == it2.title } ?: list.add(it2)
                }
            }
        }
        if (list.isNotEmpty()) {
            lists.add(list)
            animeInfo.aniInfo.r在线播放All = lists
        }

    }

    private fun initTime() {
        val timeElapsed: Long = System.currentTimeMillis() - Mainstart
        ("初始化用时: " + if (timeElapsed > 1000) (timeElapsed / 1000).toString() + "秒" else timeElapsed.toString() + "毫秒").writeTxtFile(
            true)
        ("最近运行时间:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).writeTxtFile()

    }

    private fun initEasyHttp() {
        EasyConfig.with(OkHttpClient())
            .setLogEnabled(false)
            .setServer { "https://www.httpbin.org/" }
            .setHandler(RequestHandler())
            .setInterceptor(object : IRequestInterceptor {
                override fun interceptArguments(
                    httpRequest: HttpRequest<*>?,
                    params: HttpParams?,
                    headers: HttpHeaders,
                ) {
                    headers.put("timestamp", System.currentTimeMillis().toString())
                }
            })
            .setRetryCount(2)
            .setRetryTime(2000)
            .addHeader("Connection", "close")
            .into()

    }

    fun startSync(aid: String, applicationCall: ApplicationCall) {
        cleanAll(aid)
        initEasyHttp()
        EasyHttp.get()
            .server(api + aid)
            .api("")
            .request(object : OnHttpListener<JSONObject> {

                override fun onSucceed(result: JSONObject) {

                    try {
                        result.remove("Tip")
                        ("最近一次同步时间为:" + LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).writeJsonFile("$aid.txt")

                        /*
                        runBlocking {
                            val allTestEntityList = dao.allCompleteAnimeData()
                            if (allTestEntityList.isNotEmpty()) {
                                val completeAnimeData = CompleteAnimeData(aid,
                                    json.decodeFromString(result.optString("AniInfo")),
                                    json.decodeFromString(result.optString("AniPreRel")),
                                    json.decodeFromString(result.optString("AniPreSim")),
                                    result.optString("Tip")
                                )
                                val testEntity2 = allTestEntityList.find { it.aid == aid }
                                if (testEntity2 != null) {
                                    dao.editCompleteAnimeData(aid, completeAnimeData)
                                } else {
                                    dao.addNewCompleteAnimeData(completeAnimeData)
                                }
                            }
                        }

                        runBlocking {
                            val allAnimeDataList = dao.allAnimeData()
                            if (allAnimeDataList.isNotEmpty()) {
                                val animeData = allAnimeDataList.find { it.aID == aid }
                                val animeInfo = AnimeInfo(aid,
                                    result.optJSONObject("AniInfo").optString("R其它名称")
                                        ?: result.optJSONObject("AniInfo").optString("R其他名称"),
                                    result.optJSONObject("AniInfo").optString("R制作公司"),
                                    json.decodeFromString(result.optJSONObject("AniInfo").optString("R剧情类型2")),
                                    result.optJSONObject("AniInfo").optString("R动画名称"),
                                    result.optJSONObject("AniInfo").optString("R动画种类"),
                                    result.optJSONObject("AniInfo").optString("R原作"),
                                    result.optJSONObject("AniInfo").optString("R原版名称"),
                                    result.optJSONObject("AniInfo").optString("R封面图小"),
                                    result.optJSONObject("AniInfo").optString("R播放状态"),
                                    result.optJSONObject("AniInfo").optString("R新番标题"),
                                    result.optJSONObject("AniInfo").optString("R简介"),
                                    result.optJSONObject("AniInfo").optString("R首播时间")
                                )
                                if (animeData != null) {
                                    dao.editAnimeData(aid, animeInfo)
                                } else {
                                    dao.addNewAnimeData(animeInfo)
                                }
                            }
                        }

                         */

                    } catch (e: Exception) {
                        e.message?.writeJsonFile("$aid.txt")
                    }

                }

                override fun onFail(e: Exception) {
                    e.message?.writeJsonFile("$aid.txt")
                }

                override fun onEnd(call: Call?) {
//                    runBlocking {
//                        applicationCall.respondRedirect("$path$aid.txt")
//                    }
                }
            })

    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(500, TimeUnit.MILLISECONDS)
        .writeTimeout(500, TimeUnit.MILLISECONDS)
        .readTimeout(500, TimeUnit.MILLISECONDS)
        .callTimeout(500, TimeUnit.MILLISECONDS)
        .build()

    fun isConnected(url: String): Boolean {
        return try {
            client.newCall(Request.Builder().url(url).build()).execute().isSuccessful
        } catch (e: Exception) {
            false
        }
    }


}