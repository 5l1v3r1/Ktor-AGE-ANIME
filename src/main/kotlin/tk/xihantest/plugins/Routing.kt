package tk.xihantest.plugins


import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.css.i
import kotlinx.serialization.decodeFromString
import tk.xihantest.dao.dao
import tk.xihantest.models.*
import tk.xihantest.utils.*
import tk.xihantest.utils.sync.Config.defaultPortraitUrl
import tk.xihantest.utils.sync.Config.path
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.ceil


fun Application.configureRouting() {

    routing {
        get("/") {
            val localHost = call.request.local.remoteHost
            val localPort = call.request.local.port
            val remoteHost = call.request.origin.remoteHost
            val remotePort = call.request.origin.port
            call.respondText("Proxy request host/port: $localHost, $localPort\n" +
                    "Original request host/port: $remoteHost, $remotePort"
            )
            //call.respondText("HTTP version is ${call.request.httpVersion}")
        }

    }

    routing {
        static("/v1/details") {
            staticRootFolder = File("files/details/")
            files(".")
        }
    }

    routing {
        usersRouting()
        favouriteRouting()
        historyRouting()
        chaseAnimeRouting()
        detailsRouting()
        searchRouting()
        catalogRouting()
        logInfoRouting()
    }


}

/**
 * 详细信息路由
 */
fun Route.detailsRouting() {
    route("/v1/details/{aid}") {
        get {
            val aid = call.parameters["aid"]
            val file = File("files/details/$aid")
            if (file.exists()) {
                call.sendHttpBinResponse {
                    data = file.readText()
                }
            }

        }
    }
}

/**
 * 日志
 */
fun Route.logInfoRouting() {
    route("/v1/info") {
        get {
            call.respond(JsonResult(File(path + "info.txt").readText()))
        }
    }


}

/**
 * 收藏路由
 */
fun Route.favouriteRouting() {
    route("/v1/accounts/favourite") {
        // 查看收藏
        get("{name}{pass}{page?}") {
            val name = call.parameters["name"]
            val pass = call.parameters["pass"]
            val page = call.parameters["page"]?.toInt() ?: 0

            if (Utils.isNull(name)) {
                return@get call.respond(JsonResult("", "用户名不能为空"))
            }
            if (Utils.isNull(pass)) {
                return@get call.respond(JsonResult("", "密码不能为空"))
            }

            dao.allUsers().find { it.name == name.toString() && it.pass == pass }?.let { user ->
                if (user.favoriteData.list.isNotEmpty()) {
                    //  除以 10 取整
                    val pageCount = ceil(user.favoriteData.list.size / 10.0).toInt()
                    if (page < pageCount) {
                        val list = ArrayList(user.favoriteData.list).groupByCount(10)
                        user.favoriteData.list = list[page].toMutableList()
                        call.respond(JsonResult(user.favoriteData))
                    } else {
                        call.respond(JsonResult("", "页码超出范围"))
                    }
                } else {
                    call.respond(JsonResult("", "还未收藏任何番剧,快去收藏吧~"))
                }
            } ?: call.respond(JsonResult("", "用户名 OR 密码错误或用户不存在"))


        }

        // 添加收藏
        post("{name}{pass}{aid?}") {
            val name = call.parameters["name"]
            val pass = call.parameters["pass"]
            val aid = call.parameters["aid"]
            val aids = call.receive<String>()
            if (Utils.isNull(name)) {
                return@post call.respond(JsonResult("", "用户名不能为空"))
            }
            if (Utils.isNull(pass)) {
                return@post call.respond(JsonResult("", "密码不能为空"))
            }


            if (!Utils.isNull(aid)) {
                dao.allUsers().let { allUserList ->
                    allUserList.find { it.name == name && it.pass == pass }?.let { user ->
                        user.favoriteData.list.find { it.f_AID == aid }?.let {
                            call.respond(JsonResult("", "已经收藏过了,请勿重复收藏~"))
                        } ?: run {
                            dao.allSimpleAnimeData().find { it.animeId == aid }?.let { simpleAnimeData ->
                                if (dao.addNewFavourite(user.id, mutableListOf(User.FavoriteDataDTO.DataListDTO(
                                        0,
                                        simpleAnimeData.animeId,
                                        simpleAnimeData.r动画名称,
                                        simpleAnimeData.r封面图小,
                                        LocalDateTime.now()
                                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))))
                                ) {
                                    call.respond(JsonResult("", "收藏成功"))
                                } else {
                                    call.respond(JsonResult("", "收藏失败"))
                                }
                            } ?: call.respond(JsonResult("", "未查询到相关记录"))
                        }
                    } ?: call.respond(JsonResult("", "未查询到相关记录"))
                }
            } else if (!Utils.isNull(aids)) {
                val aidsList = json.decodeFromString<MutableList<String>>(aids)
                if (aidsList.isNotEmpty()) {
                    dao.allUsers().let { allUserList ->
                        allUserList.find { it.name == name && it.pass == pass }?.let { user ->
                            val list = mutableListOf<User.FavoriteDataDTO.DataListDTO>()
                            aidsList.removeIf { aid -> user.favoriteData.list.find { it.f_AID == aid } != null }
                            if (aidsList.isNotEmpty()) {
                                aidsList.forEach { aid ->
                                    dao.allSimpleAnimeData().find { it.animeId == aid }?.let { simpleAnimeData ->
                                        list.add(User.FavoriteDataDTO.DataListDTO(
                                            0,
                                            simpleAnimeData.animeId,
                                            simpleAnimeData.r动画名称,
                                            simpleAnimeData.r封面图小,
                                            LocalDateTime.now()
                                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
                                    }
                                }
                                if (dao.addNewFavourite(user.id, list)) {
                                    call.respond(JsonResult("", "收藏成功"))
                                } else {
                                    call.respond(JsonResult("", "收藏失败"))
                                }
                            } else {
                                call.respond(JsonResult("", "已经收藏过了,请勿重复收藏~"))
                            }
                        } ?: call.respond(JsonResult("", "未查询到相关记录"))
                    }
                }
            }


        }

        // 删除收藏
        delete("{name}{pass}{aid?}") {
            val name = call.parameters["name"]
            val pass = call.parameters["pass"]
            val aid = call.parameters["aid"]
            val aids = call.receive<String>()

            if (Utils.isNull(name)) {
                return@delete call.respond(JsonResult("", "用户名不能为空"))
            }
            if (Utils.isNull(pass)) {
                return@delete call.respond(JsonResult("", "密码不能为空"))
            }

            if (!Utils.isNull(aid)) {
                dao.allUsers().let { allUserList ->
                    allUserList.find { it.name == name && it.pass == pass }?.let { user ->
                        if (dao.removeFavourite(user.id, aid!!)) {
                            call.respond(JsonResult("", "移除收藏成功"))
                        } else {
                            call.respond(JsonResult("", "移除收藏失败"))
                        }
                    } ?: call.respond(JsonResult("", "未查询到相关记录"))
                }
            } else if (!Utils.isNull(aids)) {
                val aidsList = json.decodeFromString<List<String>>(aids)
                if (aidsList.isNotEmpty()) {
                    dao.allUsers().let { allUserList ->
                        allUserList.find { it.name == name && it.pass == pass }?.let { user ->
                            if (dao.removeFavourite(userId = user.id, animeIds = aidsList)) {
                                call.respond(JsonResult("", "移除收藏成功"))
                            } else {
                                call.respond(JsonResult("", "移除收藏失败"))
                            }
                        } ?: call.respond(JsonResult("", "未查询到相关记录"))
                    }
                }
            }

        }

    }
}

/**
 * 历史记录路由
 */
fun Route.historyRouting() {
    route("/v1/accounts/history") {
        // 查询历史记录
        get("{name}{pass}{page?}") {
            val name = call.parameters["name"]
            val pass = call.parameters["pass"]
            val page = call.parameters["page"]?.toInt() ?: 0

            if (Utils.isNull(name)) {
                return@get call.respond(JsonResult("", "用户名不能为空"))
            }
            if (Utils.isNull(pass)) {
                return@get call.respond(JsonResult("", "密码不能为空"))
            }
            dao.allUsers().let { userList ->
                userList.find { it.name == name && it.pass == pass }?.let { user ->
                    if (user.historyData.list.isNotEmpty()) {
                        //  除以 10 取整
                        val pageCount = ceil(user.favoriteData.list.size / 10.0).toInt()
                        if (page < pageCount) {
                            val list = ArrayList(user.favoriteData.list).groupByCount(10)
                            user.favoriteData.list = list[page].toMutableList()
                            call.respond(JsonResult(user.favoriteData))
                        } else {
                            call.respond(JsonResult("", "页码超出范围"))
                        }
                    } else {
                        call.respond(JsonResult("", "还没历史记录,快去观看吧~"))
                    }
                } ?: call.respond(JsonResult("", "未查询到相关记录"))
            }
        }

        // 添加历史记录
        post("{name}{pass}") {
            val name = call.parameters["name"]
            val pass = call.parameters["pass"]
            val history = call.receive<User.HistoryDataDTO>()
            val historyList = history.list
            if (Utils.isNull(name)) {
                return@post call.respond(JsonResult("", "用户名不能为空"))
            }
            if (Utils.isNull(pass)) {
                return@post call.respond(JsonResult("", "密码不能为空"))
            }
            if (historyList.isNotEmpty()) {
                dao.allUsers().let { userList ->
                    userList.find { it.name == name && it.pass == pass }?.let { user ->
                        if (dao.updateHistory(user.id, historyList)) {
                            call.respond(JsonResult("", "添加历史记录成功"))
                        } else {
                            call.respond(JsonResult("", "添加历史记录失败"))
                        }
                    } ?: call.respond(JsonResult("", "未查询到相关记录"))
                }
            }
        }

        // 删除历史记录
        delete("{name}{pass}{aid?}") {
            val name = call.parameters["name"]
            val pass = call.parameters["pass"]
            val aid = call.parameters["aid"]
            val aids = call.receive<String>()

            if (Utils.isNull(name)) {
                return@delete call.respond(JsonResult("", "用户名不能为空"))
            }
            if (Utils.isNull(pass)) {
                return@delete call.respond(JsonResult("", "密码不能为空"))
            }


            if (!Utils.isNull(aid)) {
                dao.allUsers().let { userList ->
                    userList.find { it.name == name && it.pass == pass }?.let { user ->
                        if (dao.removeHistory(userId = user.id, animeId = aid!!)) {
                            call.respond(JsonResult("", "移除历史记录成功"))
                        } else {
                            call.respond(JsonResult("", "移除历史记录失败"))
                        }
                    } ?: call.respond(JsonResult("", "未查询到相关记录"))
                }
            } else if (!Utils.isNull(aids)) {
                val aidsList = json.decodeFromString<List<String>>(aids)
                if (aidsList.isNotEmpty()) {
                    dao.allUsers().let { allUserList ->
                        allUserList.find { it.name == name && it.pass == pass }?.let { user ->
                            if (dao.removeHistory(userId = user.id, animeIds = aidsList)) {
                                call.respond(JsonResult("", "移除历史记录成功"))
                            } else {
                                call.respond(JsonResult("", "移除历史记录失败"))
                            }
                        } ?: call.respond(JsonResult("", "未查询到相关记录"))
                    }
                }
            }
        }

    }
}

/**
 * 追番记录路由
 */
fun Route.chaseAnimeRouting() {
    route("/v1/accounts/chase-anime") {
        get("{name}{pass}{page?}") {
            val name = call.parameters["name"]
            val pass = call.parameters["pass"]
            val page = call.parameters["page"]?.toInt() ?: 0

            if (Utils.isNull(name)) {
                return@get call.respond(JsonResult("", "用户名不能为空"))
            }
            if (Utils.isNull(pass)) {
                return@get call.respond(JsonResult("", "密码不能为空"))
            }

            dao.allUsers().find { it.name == name && it.pass == pass }?.let { user ->
                if (user.chaseAnimeData.list.isNotEmpty()) {
                    val pageCount = ceil(user.chaseAnimeData.list.size / 10.0).toInt()
                    if (page < pageCount) {
                        val list = ArrayList(user.chaseAnimeData.list).groupByCount(10)
                        user.chaseAnimeData.list = list[page].toMutableList()
                        call.respond(JsonResult(user.chaseAnimeData))
                    } else {
                        call.respond(JsonResult("", "页码超出范围"))
                    }
                } else {
                    call.respond(JsonResult("", "未查询到相关记录"))
                }
            } ?: call.respond(JsonResult("", "用户名 OR 密码错误或用户不存在"))


        }

        post("{name}{pass}{aid}") {
            val name = call.parameters["name"]
            val pass = call.parameters["pass"]
            val aid = call.parameters["aid"]
            val aids = call.receive<String>()

            if (Utils.isNull(name)) {
                return@post call.respond(JsonResult("", "用户名不能为空"))
            }
            if (Utils.isNull(pass)) {
                return@post call.respond(JsonResult("", "密码不能为空"))
            }
            if (!Utils.isNull(aid)) {
                dao.allUsers().let { allUserList ->
                    allUserList.find { it.name == name && it.pass == pass }?.let { user ->
                        user.chaseAnimeData.list.find { it.f_AID == aid }?.let {
                            call.respond(JsonResult("", "已经添加过了,请勿重复添加~"))
                        } ?: run {
                            dao.allSimpleAnimeData().find { it.animeId == aid }?.let { simpleAnimeData ->
                                if (dao.addNewChaseAnime(user.id, mutableListOf(User.ChaseAnimeDataDTO.DataListDTO(
                                        if (simpleAnimeData.r播放状态.contains("完结")) 1 else 0,
                                        simpleAnimeData.animeId,
                                        simpleAnimeData.r动画名称,
                                        simpleAnimeData.r封面图小)))
                                ) {
                                    call.respond(JsonResult("", "添加追番成功"))
                                } else {
                                    call.respond(JsonResult("", "添加追番失败"))
                                }
                            } ?: call.respond(JsonResult("", "未查询到相关记录"))
                        }
                    } ?: call.respond(JsonResult("", "未查询到相关记录"))
                }
            } else if (!Utils.isNull(aid)) {
                val aidsList = json.decodeFromString<MutableList<String>>(aids)
                if (aidsList.isNotEmpty()) {
                    dao.allUsers().let { allUserList ->
                        allUserList.find { it.name == name && it.pass == pass }?.let { user ->
                            val list = mutableListOf<User.ChaseAnimeDataDTO.DataListDTO>()
                            aidsList.removeIf { aid -> user.favoriteData.list.find { it.f_AID == aid } != null }
                            if (aidsList.isNotEmpty()) {
                                aidsList.forEach { aid ->
                                    dao.allSimpleAnimeData().find { it.animeId == aid }?.let { simpleAnimeData ->
                                        list.add(User.ChaseAnimeDataDTO.DataListDTO(
                                            if (simpleAnimeData.r播放状态.contains("完结")) 1 else 0,
                                            simpleAnimeData.animeId,
                                            simpleAnimeData.r动画名称,
                                            simpleAnimeData.r封面图小))
                                    }
                                }
                                if (dao.addNewChaseAnime(user.id, list)) {
                                    call.respond(JsonResult("", "添加追番成功"))
                                } else {
                                    call.respond(JsonResult("", "添加追番失败"))
                                }
                            } else {
                                call.respond(JsonResult("", "已经添加过了,请勿重复添加~"))
                            }
                        } ?: call.respond(JsonResult("", "未查询到相关记录"))
                    }
                }
            }
        }
    }
}

/**
 * 用户路由
 */
fun Route.usersRouting() {
    // 注册用户
    route("/v1/accounts/signup") {

        post {
            val formParameters = call.receive<User>()
            val name = formParameters.name
            val pass = formParameters.pass
            val email = formParameters.email
            val portraitUrl = formParameters.portraitUrl
            if (!name.isUserName()) {
                return@post call.respond(JsonResult("", "用户名只能为数字或者字母,且不能超过16个字符"))
            }
            if (!email.isEmail()) {
                return@post call.respond(JsonResult("", "邮箱格式不正确,请重新输入"))
            }
            if (!pass.isPassword()) {
                return@post call.respond(JsonResult("", "密码必须为8-32位字符"))
            }

            val userList = dao.allUsers()
            if (userList.isNotEmpty()) {
                userList.find { it.name == name || it.email == email }?.let {
                    call.respond(JsonResult("", "用户名或邮箱已存在, 请重新输入. 如忘记密码,可用邮箱找回"))
                } ?: dao.addNewUser(User(
                    0,
                    name,
                    pass,
                    email,
                    if (Utils.isNull(portraitUrl) && !portraitUrl.contains("http")) defaultPortraitUrl else portraitUrl,
                    User.FavoriteDataDTO(),
                    User.HistoryDataDTO(),
                    User.ChaseAnimeDataDTO(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ))?.let {
                    call.respond(JsonResult(it.pass, "注册成功，欢迎使用~"))
                } ?: call.respond(JsonResult("", "注册失败,请稍后再试~多次注册失败,请联系管理员"))
            }
        }
    }
    // 注销用户
    route("/v1/accounts/cancel") {
        delete {
            val name = call.request.queryParameters["name"]
            val pass = call.request.queryParameters["pass"]
            val userList = dao.allUsers()
            if (userList.isNotEmpty()) {
                userList.find { it.name == name && it.pass == pass }?.let {
                    if (dao.removeUser(it.id)) {
                        call.respond(JsonResult("", "注销成功,欢迎下次使用~"))
                    } else {
                        call.respond(JsonResult("", "注销失败,请检查网络后重试~"))
                    }
                } ?: call.respond(JsonResult("", "用户名 OR 密码输入错误或不存在,请检查后重试~"))
            }
        }
    }
    // 更新用户
    route("/v1/accounts/update") {
        post("{newPass}{newEmail}{newPortraitUrl}") {
            val newPass = call.request.queryParameters["newPass"]
            val newEmail = call.request.queryParameters["newEmail"]
            val newPortraitUrl = call.request.queryParameters["newPortraitUrl"]
            val formParameters = call.receive<User>()
            val name = formParameters.name
            val pass = formParameters.pass
            val portraitUrl = formParameters.portraitUrl

            if (newEmail?.isEmail() == false) {
                return@post call.respond(JsonResult("", "邮箱格式不正确,请重新输入"))
            }
            if (newPass?.isPassword() == false) {
                return@post call.respond(JsonResult("", "密码必须为8-32位字符"))
            }

            val userList = dao.allUsers()
            if (userList.isNotEmpty()) {
                userList.find { it.name == name && it.pass == pass }?.let { user ->
                    if (dao.updateUser(user.id, newPass, newEmail, newPortraitUrl)) {
                        call.respond(JsonResult("", "更新成功,请重新登录"))
                    } else {
                        call.respond(JsonResult("", "更新失败,请检查网络后重试~"))
                    }
                } ?: call.respond(JsonResult("", "用户名 OR 密码输入错误或不存在,请检查后重试~"))


            }

        }
    }
    // 登录用户
    route("/v1/accounts/login") {
        get {
            val name = call.request.queryParameters["name"]
            val pass = call.request.queryParameters["pass"]

            if (Utils.isNull(name)) {
                return@get call.respond(JsonResult("", "用户名不能为空"))
            }
            if (Utils.isNull(pass)) {
                return@get call.respond(JsonResult("", "密码不能为空"))
            }

            val userList = dao.allUsers()
            if (userList.isNotEmpty()) {
                userList.find { it.name == name && it.pass == pass }?.let { user ->
                    user.pass = user.pass
                    call.respond(JsonResult(user, "登录成功,欢迎使用~"))
                } ?: call.respond(JsonResult("", "用户名 OR 密码输入错误或不存在,请检查后重试~"))

            }
        }
    }

    // 忘记密码
    route("/v1/accounts/forget") {
        get {
            val name = call.request.queryParameters["name"]
            val email = call.request.queryParameters["email"]

            if (Utils.isNull(name)) {
                return@get call.respond(JsonResult("", "用户名不能为空"))
            }
            if (Utils.isNull(email)) {
                return@get call.respond(JsonResult("", "邮箱不能为空"))
            }

            val userList = dao.allUsers()
            if (userList.isNotEmpty()) {
                userList.find { it.name == name && it.email == email }?.let { user ->
                    call.respond(JsonResult("",
                        if (Utils.sendMail(user.email, user.pass)) "查询成功,请登录邮箱查看密码" else "查询失败,请稍微再试~多次出现请联系管理员"))
                } ?: call.respond(JsonResult("", "用户名 OR 邮箱输入错误或不存在,请检查后重试~"))
            }
        }
    }

}

/**
 * 搜索路由
 */
fun Route.searchRouting() {
    route("/v1/search") {
        get {
            val query = call.request.queryParameters["query"]
            val page = call.request.queryParameters["page"]?.toInt() ?: 0

            if (Utils.isNull(query)) {
                return@get call.respond(JsonResult("", "搜索内容不能为空"))
            }

            val simpleAnimeDataList = dao.allSimpleAnimeData()
            if (simpleAnimeDataList.isNotEmpty()) {
                // 列表有数据名为 query 的数据
                val simpleAnimeDataListByQuery = simpleAnimeDataList.filter {
                    it.r动画名称.contains(query!!) || it.r其他名称.contains(query) || it.r原版名称.contains(query)
                }
                if (simpleAnimeDataListByQuery.isNotEmpty()) {
                    //  除以 10 取整
                    val pageCount = ceil(simpleAnimeDataListByQuery.size / 24.0).toInt()
                    if (page < pageCount) {
                        val list = ArrayList(simpleAnimeDataListByQuery).groupByCount(24)
                        val newList = list[page].toMutableList()
                        call.respond(JsonResult(SearchData(aniPreL = newList,
                            keyWord = query!!,
                            pageCount = pageCount,
                            seaCnt = simpleAnimeDataListByQuery.size)))
                    } else {
                        call.respond(JsonResult("", "页码超出范围"))
                    }
                } else {
                    call.respond(JsonResult("", "没有搜索到相关数据"))
                }
            }


        }
    }


}

/**
 * 目录路由
 */
fun Route.catalogRouting() {

    route("/v1/catalog") {
        get {
            var region = call.request.queryParameters["region"]
            var genre = call.request.queryParameters["genre"]
            var letter = call.request.queryParameters["letter"]
            var year = call.request.queryParameters["year"]
            var season = call.request.queryParameters["season"]
            var status = call.request.queryParameters["status"]
            var label = call.request.queryParameters["label"]
            var resource = call.request.queryParameters["resource"]
            var order = call.request.queryParameters["order"]
            val page = call.request.queryParameters["page"]?.toInt() ?: 0
            val size = call.request.queryParameters["size"]?.toInt() ?: 15
            if (Utils.isNull(region)) region = "all"
            if (Utils.isNull(genre)) genre = "all"
            if (Utils.isNull(letter)) letter = "all"
            if (Utils.isNull(year)) year = "all"
            if (Utils.isNull(season)) season = "all"
            if (Utils.isNull(status)) status = "all"
            if (Utils.isNull(label)) label = "all"
            if (Utils.isNull(resource)) resource = "all"
            if (Utils.isNull(order)) order = "更新时间"
            val completeAnimeDataList = dao.allCompleteAnimeData()
            if (completeAnimeDataList.isNotEmpty()) {
                val completeAnimeDataListByRegion =
                    if (region == "all") completeAnimeDataList else completeAnimeDataList.filter { it.r地区 == region }
                val completeAnimeDataListByGenre =
                    if (genre == "all") completeAnimeDataListByRegion else completeAnimeDataListByRegion.filter { it.r动画种类 == genre }
                val completeAnimeDataListByLetter =
                    if (letter == "all") completeAnimeDataListByGenre else completeAnimeDataListByGenre.filter { it.r字母索引 == letter }
                val completeAnimeDataListByYear =
                    if (year == "all") completeAnimeDataListByLetter else completeAnimeDataListByLetter.filter { it.r首播年份 == year }
                val completeAnimeDataListBySeason =
                    if (season == "all") completeAnimeDataListByYear else completeAnimeDataListByYear.filter { it.r首播季度 == season }
                val completeAnimeDataListByStatus =
                    if (status == "all") completeAnimeDataListBySeason else completeAnimeDataListBySeason.filter { it.r播放状态 == status }
                val completeAnimeDataListByLabel =
                    if (label == "all") completeAnimeDataListByStatus else completeAnimeDataListByStatus.filter {
                        it.r剧情类型2.contains(label)
                    }
                val completeAnimeDataListByResource =
                    if (resource == "all") completeAnimeDataListByLabel else completeAnimeDataListByLabel.filter { it.r资源类型 == resource }
                val completeAnimeDataListByOrder = when (order) {
                    "更新时间" -> completeAnimeDataListByResource.sortedByDescending { it.r更新时间unix }
                    "名称" -> completeAnimeDataListByResource.sortedBy { it.r动画名称 }
                    "点击量" -> completeAnimeDataListByResource.sortedByDescending { it.rankCnt }
                    else -> {
                        completeAnimeDataListByResource.sortedByDescending { it.r更新时间unix }
                    }
                }

                val simpleAnimeDataList = completeAnimeDataListByOrder.map {
                    SimpleAnimeData(
                        it.aID,
                        it.r其它名称,
                        it.r制作公司,
                        it.r剧情类型2,
                        it.r动画名称,
                        it.r动画种类,
                        it.r原作,
                        it.r原版名称,
                        it.r封面图小,
                        it.r播放状态,
                        it.r新番标题,
                        it.r简介,
                        it.r首播时间)
                }.toMutableList()

                if (simpleAnimeDataList.isNotEmpty()) {
                    val pageCount = ceil(simpleAnimeDataList.size / size.toDouble()).toInt()
                    if (page < pageCount) {
                        val list = simpleAnimeDataList.groupByCount(size)
                        val newList = list[page].toMutableList()
                        call.respond(JsonResult(CatalogData(aniPreL = newList,
                            pageCount = pageCount,
                            AllCnt = simpleAnimeDataList.size)))
                    } else {
                        call.respond(JsonResult("", "页码超出范围"))
                    }



                }


            }
        }


    }

    route("/v1/rank{page}{size}{value?}"){
        get{
            val page = call.parameters["page"]?.toIntOrNull() ?: 0
            val size = call.parameters["size"]?.toIntOrNull() ?: 75
            val value = call.parameters["value"] ?: "0"

            val completeAnimeDataList = dao.allCompleteAnimeData()
            if (completeAnimeDataList.isNotEmpty()){
               val completeAnimeDataListByYear = if (value == "0") completeAnimeDataList else completeAnimeDataList.filter { it.r首播年份 == value }
                val completeAnimeDataListByRankCnt = completeAnimeDataListByYear.sortedByDescending { it.rankCnt }

                val rankDataList = completeAnimeDataListByRankCnt.mapIndexed { index, it ->
                    RankData.AnimeRankPre(
                        it.aID,
                        it.rankCnt,
                        (index +1),
                        it.r新番标题



                    )

                }




            }










        }

    }
}







