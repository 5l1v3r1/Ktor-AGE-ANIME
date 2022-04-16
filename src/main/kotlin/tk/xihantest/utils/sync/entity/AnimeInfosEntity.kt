package tk.xihantest.utils.sync.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeInfosEntity(
    @SerialName("AniInfo")
    var aniInfo: AniInfoEntity = AniInfoEntity(),
    @SerialName("AniPreRel")
    var aniPreRel: List<AniPreRelEntity> = listOf(),
    @SerialName("AniPreSim")
    var aniPreSim: List<AniPreSimEntity> = listOf(),
    @SerialName("Tip")
    var tip: String = "" // AGE动漫网 备用地址：<a rel="nofollow" target="_blank" href="http://www.age.tv" style="color: #60b8cc;text-decoration:  none;">www.age.tv</a> 欢迎大家分享给身边朋友！为确保正常观看，请使用谷歌浏览器。
) {
    @Serializable
    data class AniInfoEntity(
        @SerialName("AID")
        var aID: String = "", // 20220064
        @SerialName("CollectCnt")
        var collectCnt: Int = 0, // 26397
        @SerialName("CommentCnt")
        var commentCnt: Int = 0, // 25
        @SerialName("DEF_PLAYINDEX")
        var dEFPLAYINDEX: String = "", // 1
        @SerialName("FilePath")
        var filePath: String = "", // /home/wwwroot/goage_files/2022/20220064 勇者辞职不干了/text.txt
        @SerialName("LastModified")
        var lastModified: String = "", // Fri, 9 Apr 2032 22:49:26 GMT
        @SerialName("ModifiedTime")
        var modifiedTime: Int = 0, // 1649774966
        @SerialName("RankCnt")
        var rankCnt: Int = 0, // 220106
        @SerialName("R其它名称")
        var r其它名称: String = "", // 暂无
        @SerialName("R制作公司")
        var r制作公司: String = "", // EMT2
        @SerialName("R剧情类型")
        var r剧情类型: String = "", // 奇幻 冒险
        @SerialName("R剧情类型2")
        var r剧情类型2: List<String> = listOf(),
        @SerialName("R动画名称")
        var r动画名称: String = "", // 勇者辞职不干了
        @SerialName("R动画种类")
        var r动画种类: String = "", // TV
        @SerialName("R原作")
        var r原作: String = "", // クオンタム
        @SerialName("R原版名称")
        var r原版名称: String = "", // 勇者、辞めます
        @SerialName("R在线播放")
        var r在线播放: String = "", // PV1 <play>PC-WEIBO</play>wbfid-1034:4694361007652889PV2 <play>爱奇艺接口</play>//www.iqiyi.com/v_l9ygs6b79g.htmlPV3 <play>PC-WEIBO</play>wbfid-1034:4751706043646106
        @SerialName("R在线播放2")
        var r在线播放2: String = "", // 第1集 <play>web_m3u8</play>https%3a%2f%2fyun%2e66dm%2enet%2fSBDM%2fYuushaYamemasu01%2em3u8第2集 <play>web_m3u8</play>https%3a%2f%2fyun%2e66dm%2enet%2fSBDM%2fYuushaYamemasu02%2em3u8
        @SerialName("R在线播放3")
        var r在线播放3: String = "", // 第1集 <play>PC-emodm2</play>yj3456__v9337147787188277469727716221072428_7082929455198510117_yzczbgl01第2集 <play>PC-emodm2</play>yj3456__v2963825895248474717827716221072428_7085529142887767054_yzczbgl02
        @SerialName("R在线播放4")
        var r在线播放4: String = "",
        @SerialName("R在线播放All")
        var r在线播放All: MutableList<MutableList<R在线播放AllEntity>> = mutableListOf(),
        @SerialName("R地区")
        var r地区: String = "", // 日本
        @SerialName("R备用")
        var r备用: String = "",
        @SerialName("R字母索引")
        var r字母索引: String = "", // Y
        @SerialName("R官方网站")
        var r官方网站: String = "", // https://yuuyame.com/
        @SerialName("R封面图")
        var r封面图: String = "", // //tvax3.sinaimg.cn/large/008kBpBlgy1gzjaityajcj307409w3z7.jpg
        @SerialName("R封面图小")
        var r封面图小: String = "", // //tvax3.sinaimg.cn/large/008kBpBlgy1gzjaitsp6uj304605s3ym.jpg
        @SerialName("R推荐星级")
        var r推荐星级: Int = 0, // 2
        @SerialName("R播放状态")
        var r播放状态: String = "", // 连载
        @SerialName("R新番标题")
        var r新番标题: String = "", // 22:30 第2集
        @SerialName("R更新时间")
        var r更新时间: String = "", // 20220412224442
        @SerialName("R更新时间str")
        var r更新时间str: String = "", // 2022-04-12 22:44:42
        @SerialName("R更新时间str2")
        var r更新时间str2: String = "", // 2022-04-12
        @SerialName("R更新时间unix")
        var r更新时间unix: Int = 0, // 1649774682
        @SerialName("R标签")
        var r标签: String = "", // 奇幻 冒险
        @SerialName("R标签2")
        var r标签2: List<String> = listOf(),
        @SerialName("R标题V2")
        var r标题V2: String = "", // 1
        @SerialName("R简介")
        var r简介: String = "", // 成功拯救世界的勇者雷欧，因为他过强的能力，反而在和平的世界显得格格不入被人们所畏惧。因此从圣都被放逐的他，身份以及名誉尽失的勇者，下个职场竟然是锁定过去的死敌“魔王军”。过去的勇者如今成为魔王军的一员，不仅亲手协助重建残破不堪魔王军，甚至想要重建整个世界？！本作过去曾获“カクヨム第 2 回网络小说竞赛”的奇幻部门大奖，在 2018 年时由风都ノリ改编漫画。这次宣布改编动画的当下，官方也宣布了主要制作小组以及配音名单，并且释出了首部宣传影片。
        @SerialName("R简介_br")
        var r简介Br: String = "", // 成功拯救世界的勇者雷欧，因为他过强的能力，反而在和平的世界显得格格不入被人们所畏惧。因此从圣都被放逐的他，身份以及名誉尽失的勇者，下个职场竟然是锁定过去的死敌“魔王军”。过去的勇者如今成为魔王军的一员，不仅亲手协助重建残破不堪魔王军，甚至想要重建整个世界？！<br/>本作过去曾获“カクヨム第 2 回网络小说竞赛”的奇幻部门大奖，在 2018 年时由风都ノリ改编漫画。这次宣布改编动画的当下，官方也宣布了主要制作小组以及配音名单，并且释出了首部宣传影片。
        @SerialName("R系列")
        var r系列: String = "", // 勇者辞职不干了
        @SerialName("R网盘资源")
        var r网盘资源: String = "", // (暂缺)
        @SerialName("R网盘资源2")
        var r网盘资源2: List<R网盘资源2Entity> = listOf(),
        @SerialName("R视频尺寸")
        var r视频尺寸: String = "", // 720P/1080P
        @SerialName("R资源类型")
        var r资源类型: String = "",
        @SerialName("R首播季度")
        var r首播季度: String = "", // 4
        @SerialName("R首播年份")
        var r首播年份: String = "", // 2022
        @SerialName("R首播时间")
        var r首播时间: String = "" // 2022-04-05
    ) {
        @Serializable
        data class R在线播放AllEntity(
            @SerialName("EpName")
            var epName: String = "", // PV1
            @SerialName("EpPic")
            var epPic: String = "",
            @SerialName("Ex")
            var ex: String = "",
            @SerialName("PlayId")
            var playId: String = "", // <play>PC-WEIBO</play>
            @SerialName("PlayVid")
            var playVid: String = "", // wbfid-1034:4694361007652889
            @SerialName("Title")
            var title: String = "", // PV1
            @SerialName("Title_l")
            var titleL: String = "" // PV1
        )

        @Serializable
        data class R网盘资源2Entity(
            @SerialName("ExCode")
            var exCode: String = "", // 065c
            @SerialName("Link")
            var link: String = "", // https://pan.baidu.com/s/ROR3J9dD5p4VebL58zG4hI7
            @SerialName("Title")
            var title: String = "" // [AGE压制 TV 01-13+OVA 720P]
        )
    }

    @Serializable
    data class AniPreRelEntity(
        @SerialName("AID")
        var aID: String = "", // 20220064
        @SerialName("Href")
        var href: String = "", // /detail/20220064
        @SerialName("NewTitle")
        var newTitle: String = "", // 22:30 第2集
        @SerialName("PicSmall")
        var picSmall: String = "", // //tvax3.sinaimg.cn/large/008kBpBlgy1gzjaitsp6uj304605s3ym.jpg
        @SerialName("Title")
        var title: String = "" // 勇者辞职不干了
    )

    @Serializable
    data class AniPreSimEntity(
        @SerialName("AID")
        var aID: String = "", // 20220145
        @SerialName("Href")
        var href: String = "", // /detail/20220145
        @SerialName("NewTitle")
        var newTitle: String = "",
        @SerialName("PicSmall")
        var picSmall: String = "", // //tvax3.sinaimg.cn/large/008kBpBlgy1gy2rairmvpj304605sdfv.jpg
        @SerialName("Title")
        var title: String = "" // 打了300年的史莱姆 不知不觉就练到了满级 第二季
    )

    override fun toString(): String {
        return "AnimeInfosEntity(aniInfo=$aniInfo, aniPreRel=$aniPreRel, aniPreSim=$aniPreSim, tip='$tip')"
    }


}