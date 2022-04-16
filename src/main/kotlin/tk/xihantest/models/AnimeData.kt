package tk.xihantest.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import tk.xihantest.utils.sync.entity.AnimeInfosEntity

@Serializable
data class SimpleAnimeData(
    @SerialName("AID")
    var animeId: String = "1", // 20210258
    @SerialName("R其他名称")
    var r其他名称: String = "", // Platinum End / 白金终局
    @SerialName("R制作公司")
    var r制作公司: String = "", // SIGNAL.MD
    @SerialName("R剧情类型")
    var r剧情类型: List<String> = listOf(),
    @SerialName("R动画名称")
    var r动画名称: String = "", // 铂金终局
    @SerialName("R动画种类")
    var r动画种类: String = "", // TV
    @SerialName("R原作")
    var r原作: String = "", // 小畑健 / 大場つぐみ
    @SerialName("R原版名称")
    var r原版名称: String = "", // プラチナエンド
    @SerialName("R封面图小")
    var r封面图小: String = "", // //tvax3.sinaimg.cn/large/008kBpBlgy1gvvaotpm6sj304605s74c.jpg
    @SerialName("R播放状态")
    var r播放状态: String = "", // 完结
    @SerialName("R新番标题")
    var r新番标题: String = "", // 第24集(完结)
    @SerialName("R简介")
    var r简介: String = "", // 电视动画片《白金终局》改编自大场鸫原作、小畑健作画的同名漫画作品，于2020年12月19日宣布制作决定。该片由SIGNAL.MD负责制作，于2021年10月起播出 。“我可以给你‘活下去’的希望。”架桥明日因为一场意外而失去家人，在收养他的亲戚家每天都过得非常辛苦。对一切都感到绝望的少年，在国中毕业当天选择从大楼屋顶跳楼。不过，此时少年遇见了1位天使。
    @SerialName("R首播时间")
    var r首播时间: String = "", // 2021-10-07
)


object SimpleAnimeDatas : Table() {

    val animeId = varchar("AID", 99)
    val r其他名称 = varchar("R其他名称", 9999)
    val r制作公司 = varchar("R制作公司", 9999)
    val r剧情类型 = varchar("R剧情类型", 9999)

    val r动画名称 = varchar("R动画名称", 9999)
    val r动画种类 = varchar("R动画种类", 9999)
    val r原作 = varchar("R原作", 9999)
    val r原版名称 = varchar("R原版名称", 9999)
    val r封面图小 = varchar("R封面图小", 9999)
    val r播放状态 = varchar("R播放状态", 9999)
    val r新番标题 = varchar("R新番标题", 9999)
    val r简介 = varchar("R简介", 99999)
    val r首播时间 = varchar("R首播时间", 9999)

    override val primaryKey = PrimaryKey(animeId)


}


@Serializable
data class CompleteAnimeData(
    @SerialName("AID")
    var aID: String = "1", // 20210135
    @SerialName("CollectCnt")
    var collectCnt: Int = 0, // 31865
    @SerialName("CommentCnt")
    var commentCnt: Int = 0, // 310
    @SerialName("DEF_PLAYINDEX")
    var dEFPLAYINDEX: String = "", // 1
    @SerialName("FilePath")
    var filePath: String = "", // /home/wwwroot/goage_files/2021/20210135 因为不是真正的伙伴而被逐出勇者队伍，流落到边境展开慢活人生/text.txt
    @SerialName("LastModified")
    var lastModified: String = "", // Sat, 27 Mar 2032 08:15:32 GMT
    @SerialName("ModifiedTime")
    var modifiedTime: Int = 0, // 1648599332
    @SerialName("RankCnt")
    var rankCnt: Int = 0, // 1558848
    @SerialName("R其它名称")
    var r其它名称: String = "", // 我被逐出队伍后过上慢生活 / 不是真正同伴的我被逐出了勇者队伍，因此决定在边境过上慢生活 / Shin no Nakama ja Nai to Yuusha no Party wo Oidasareta node, Henkyou de Slow Life suru Koto ni Shimashita
    @SerialName("R制作公司")
    var r制作公司: String = "", // Wolfsbane / Studio Flad
    @SerialName("R剧情类型")
    var r剧情类型: String = "", // 奇幻 冒险 恋爱
    @SerialName("R剧情类型2")
    var r剧情类型2: List<String> = listOf(),
    @SerialName("R动画名称")
    var r动画名称: String = "", // 因为不是真正的伙伴而被逐出勇者队伍，流落到边境展开慢活人生
    @SerialName("R动画种类")
    var r动画种类: String = "", // TV
    @SerialName("R原作")
    var r原作: String = "", // ざっぽん
    @SerialName("R原版名称")
    var r原版名称: String = "", // 真の仲間じゃないと勇者のパーティーを追い出されたので、辺境でスローライフすることにしました
    @SerialName("R在线播放All")
    var r在线播放All: MutableList<MutableList<AnimeInfosEntity.AniInfoEntity.R在线播放AllEntity>> = mutableListOf(),
    @SerialName("R地区")
    var r地区: String = "", // 日本
    @SerialName("R备用")
    var r备用: String = "",
    @SerialName("R字母索引")
    var r字母索引: String = "", // B
    @SerialName("R官方网站")
    var r官方网站: String = "", // https://shinnonakama.com/
    @SerialName("R封面图")
    var r封面图: String = "", // //tvax3.sinaimg.cn/large/008kBpBlgy1gu392agtugj607409wmxu02.jpg
    @SerialName("R封面图小")
    var r封面图小: String = "", // //tvax3.sinaimg.cn/large/008kBpBlgy1gu392abiu1j604605sjrh02.jpg
    @SerialName("R推荐星级")
    var r推荐星级: Int = 0, // 2
    @SerialName("R播放状态")
    var r播放状态: String = "", // 完结
    @SerialName("R新番标题")
    var r新番标题: String = "", // 第13集(完结)
    @SerialName("R更新时间")
    var r更新时间: String = "", // 20211229224449
    @SerialName("R更新时间str")
    var r更新时间str: String = "", // 2021-12-29 22:44:49
    @SerialName("R更新时间str2")
    var r更新时间str2: String = "", // 2021-12-29
    @SerialName("R更新时间unix")
    var r更新时间unix: Int = 0, // 1640789089
    @SerialName("R标签")
    var r标签: String = "", // 奇幻 冒险 恋爱
    @SerialName("R标签2")
    var r标签2: List<String> = listOf(),
    @SerialName("R标题V2")
    var r标题V2: String = "", // 1
    @SerialName("R简介")
    var r简介: String = "", // 主要剧情围绕着基甸的慢生活，基甸在边境城市隐姓埋名开了一家药店，遇到了曾经帮助过的亡国王女，现在已经是冒险者英雄莉特，两个人抛开了其它牵挂在这里隐居。基甸每天负责做药和外出采药，而莉特则会帮忙看店以及偶尔出去一起采药，有着过人实力的他们想在这个小城轻松的生活并不是什么难事。
    @SerialName("R简介_br")
    var r简介Br: String = "", // 主要剧情围绕着基甸的慢生活，基甸在边境城市隐姓埋名开了一家药店，遇到了曾经帮助过的亡国王女，现在已经是冒险者英雄莉特，两个人抛开了其它牵挂在这里隐居。基甸每天负责做药和外出采药，而莉特则会帮忙看店以及偶尔出去一起采药，有着过人实力的他们想在这个小城轻松的生活并不是什么难事。
    @SerialName("R系列")
    var r系列: String = "", // 不是真正同伴的我被逐出了勇者队伍
    @SerialName("R网盘资源")
    var r网盘资源: String = "",
    @SerialName("R网盘资源2")
    var r网盘资源2: List<AnimeInfosEntity.AniInfoEntity.R网盘资源2Entity> = listOf(),
    @SerialName("R视频尺寸")
    var r视频尺寸: String = "", // 720P/1080P
    @SerialName("R资源类型")
    var r资源类型: String = "",
    @SerialName("R首播季度")
    var r首播季度: String = "", // 10
    @SerialName("R首播年份")
    var r首播年份: String = "", // 2021
    @SerialName("R首播时间")
    var r首播时间: String = "", // 2021-10-06
    var r相关动画: List<AnimeInfosEntity.AniPreRelEntity> = mutableListOf(),
    var r猜你喜欢: List<AnimeInfosEntity.AniPreSimEntity> = mutableListOf(),
)

object CompleteAnimeDatas : Table() {

    val animeId = varchar("AID", 999)
    val r地区 = varchar("R地区", 999)
    val r动画种类 = varchar("R动画种类", 999)
    val r动画名称 = varchar("R动画名称", 999)
    val r原版名称 = varchar("R原版名称", 99999)
    val r其它名称 = varchar("R其它名称", 99999)
    val r字母索引 = varchar("R字母索引", 999)
    val r原作 = varchar("R原作", 9999)
    val r制作公司 = varchar("R制作公司", 9999)
    val r首播时间 = varchar("R首播时间", 9999)
    val r播放状态 = varchar("R播放状态", 9999)
    val r剧情类型 = varchar("R剧情类型", 9999)
    val r新番标题 = varchar("R新番标题", 9999)
    val r网盘资源 = varchar("R网盘资源", 9999)
    val r网盘资源2 = varchar("R网盘资源2", 9999)
    val r视频尺寸 = varchar("R视频尺寸", 9999)
    val r资源类型 = varchar("R资源类型", 9999)
    val r备用 = varchar("R备用", 9999)
    val r系列 = varchar("R系列", 99999)
    val r官方网站 = varchar("R官方网站", 9999)
    val r标签 = varchar("R标签", 9999)
    val r更新时间 = varchar("R更新时间", 9999)
    val r标题V2 = varchar("R标题V2", 9999)
    val r推荐星级 = varchar("R推荐星级", 9999)
    val r封面图 = varchar("R封面图", 9999)
    val r封面图小 = varchar("R封面图小", 9999)
    val r简介 = varchar("R简介", 999999)
    val dEFPLAYINDEX = varchar("DEF_PLAYINDEX", 9999)
    val r更新时间unix = varchar("R更新时间unix", 9999)
    val r更新时间str = varchar("R更新时间str", 9999)
    val r更新时间str2 = varchar("R更新时间str2", 9999)
    val r简介_br = varchar("R简介_br", 999999)
    val r标签2 = varchar("R标签2", 9999)
    val r剧情类型2 = varchar("R剧情类型2", 9999)
    val r在线播放All = varchar("R在线播放All", 1048576)
    val r首播年份 = varchar("R首播年份", 9999)
    val r首播季度 = varchar("R首播季度", 9999)
    val rankCnt = varchar("RankCnt", 9999)
    val commentCnt = varchar("CommentCnt", 9999)
    val collectCnt = varchar("CollectCnt", 9999)
    val modifiedTime = varchar("ModifiedTime", 9999)
    val lastModified = varchar("LastModified", 9999)
    val filePath = varchar("FilePath", 9999)
    val r相关动画 = varchar("R相关动画", 1048576)
    val r猜你喜欢 = varchar("R猜你喜欢", 1048576)

    override val primaryKey = PrimaryKey(animeId)

}

@Serializable
data class CloudDiskLink(
    val animeId: String = "1",
    var cloudDiskLinkList: MutableList<AnimeInfosEntity.AniInfoEntity.R网盘资源2Entity> = mutableListOf(),
)

object CloudDiskLinks : Table() {
    val animeId = varchar("AID", 999)
    val cloudDiskLinkList = varchar("cloudDiskLinkList", 9999)
}

@Serializable
data class PlayerNumber(
    var aid: String = "1",
    var playList: MutableList<MutableList<AnimeInfosEntity.AniInfoEntity.R在线播放AllEntity>> = mutableListOf()
){

    var playerList: MutableList<AnimeInfosEntity.AniInfoEntity.R在线播放AllEntity> = mutableListOf()

}

object PlayerNumbers: Table() {

    val aid = varchar("aid", 99)
    var playList = varchar("playerList", 999999)

    override val primaryKey = PrimaryKey(aid)



}


@Serializable
data class SearchData(
    @SerialName("AniPreL")
    var aniPreL: List<SimpleAnimeData> = listOf(),
    @SerialName("KeyWord")
    var keyWord: String = "", // 魔法
    @SerialName("PageCount")
    var pageCount: Int = 0,
    @SerialName("SeaCnt")
    var seaCnt: Int = 0, // 95
)

@Serializable
data class CatalogData(
    @SerialName("AniPreL")
    var aniPreL: List<SimpleAnimeData> = listOf(),
    @SerialName("PageCount")
    var pageCount: Int = 0,
    @SerialName("AllCnt")
    var AllCnt: Int = 0,
)

@Serializable
data class RankData(
    @SerialName("AniPreL")
    var aniPreL: List<AnimeRankPre> = listOf(),
    @SerialName("Year")
    var year: Int = 0,
    @SerialName("AllCnt")
    var AllCnt: Int = 0,
) {
    @Serializable
    data class AnimeRankPre(
        @SerialName("AID")
        var aID: String = "", // 20180062
        @SerialName("CCnt")
        var cCnt: Int = 0, // 11478429
        @SerialName("NO")
        var nO: Int = 0, // 1
        @SerialName("NewTitle")
        var newTitle: String = "", // 第203集
        @SerialName("PicSmall")
        var picSmall: String = "", // //tvax3.sinaimg.cn/large/008kBpBlgy1h0j6yf97krj304605s0sr.jpg
        @SerialName("Title")
        var title: String = "", // 斗罗大陆
    )


}

