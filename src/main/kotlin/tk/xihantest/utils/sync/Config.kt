package tk.xihantest.utils.sync

object Config {

    var 主页列表 = "https://api.agefans.app/v2/home-list?update=12&recommend=12"
    var 推荐 = "https://api.agefans.app/v2/recommend?page=1&size=100"
    var 轮播图 = "https://api.agefans.app/v2/slipic"
    var 其他 = "https://api.agefans.app/v2/catalog?genre=all&label=%E5%85%B6%E4%BB%96&letter=all&order=%E6%9B%B4%E6%96%B0%E6%97%B6%E9%97%B4&region=all&resource=all&season=all&status=all&year=all&page=1&size="
    var 目录 = "https://api.agefans.app/v2/catalog"
    var 默认目录 = "https://api.agefans.app/v2/catalog?genre=all&label=all&letter=all&order=%E6%9B%B4%E6%96%B0%E6%97%B6%E9%97%B4&region=all&resource=all&season=all&status=all&year=all&page=1&size="
    var api = "https://api.agefans.app/v2/detail/"
    //var localApi = "http://192.168.43.110/AGE-API/details/"
    const val localMain = "http://192.168.43.110/AGE-API/Main-8"

    var tmm3pPlayHead = "https%3A%2F%2Fplay.agemys.com%3A8443"
    var path = "/www/wwwroot/xihan/AGE-API/details/"
    //var path = "F:\\Java\\Projects\\ktor-anime\\files\\details\\"
    const val dq = "&region=" + "all"
    const val bb = "?genre=" + "all"
    const val szm = "&letter=" + "all"
    const val nf = "&year=" + "all"
    const val jd = "&season=" + "all"
    const val zt = "&status=" + "all"
    const val lx = "&label=" + "all"
    const val zy = "&resource=" + "all"
    const val px = "&order=" + "更新时间"

    const val defaultPortraitUrl = "https://cdn.jsdelivr.net/gh/xihan123/AGE@master/images/tx.jpg"

}