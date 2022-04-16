package tk.xihantest.utils.sync.entity


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

data class AnimeInfoEntity(
    @SerializedName("AniInfo")
    var aniInfoDTO: AniInfo = AniInfo(),
    @SerializedName("AniPreRel")
    var aniPreRelDTO: List<AniPreRel> = listOf(),
    @SerializedName("AniPreSim")
    var aniPreSimDTO: List<AniPreSim> = listOf(),
    @SerializedName("Tip")
    var tipDTO: String = ""
) {
    data class AniInfo(
        @SerializedName("AID")
        var aIDDTO: String = "",
        @SerializedName("CollectCnt")
        var collectCntDTO: Int = 0,
        @SerializedName("CommentCnt")
        var commentCntDTO: Int = 0,
        @SerializedName("DEF_PLAYINDEX")
        var dEFPLAYINDEXDTO: String = "",
        @SerializedName("FilePath")
        var filePathDTO: String = "",
        @SerializedName("LastModified")
        var lastModifiedDTO: String = "",
        @SerializedName("ModifiedTime")
        var modifiedTimeDTO: Int = 0,
        @SerializedName("RankCnt")
        var rankCntDTO: Int = 0,
        @SerializedName("R其它名称")
        var r其它名称DTO: String = "",
        @SerializedName("R制作公司")
        var r制作公司DTO: String = "",
        @SerializedName("R剧情类型")
        var r剧情类型DTO: String = "",
        @SerializedName("R剧情类型2")
        var r剧情类型2DTO: List<String> = listOf(),
        @SerializedName("R动画名称")
        var r动画名称DTO: String = "",
        @SerializedName("R动画种类")
        var r动画种类DTO: String = "",
        @SerializedName("R原作")
        var r原作DTO: String = "",
        @SerializedName("R原版名称")
        var r原版名称DTO: String = "",
        @SerializedName("R在线播放")
        var r在线播放DTO: String = "",
        @SerializedName("R在线播放2")
        var r在线播放2DTO: String = "",
        @SerializedName("R在线播放3")
        var r在线播放3DTO: String = "",
        @SerializedName("R在线播放4")
        var r在线播放4DTO: String = "",
        @SerializedName("R在线播放All")
        var r在线播放AllDTO: List<List<R在线播放All>> = listOf(),
        @SerializedName("R地区")
        var r地区DTO: String = "",
        @SerializedName("R备用")
        var r备用DTO: String = "",
        @SerializedName("R字母索引")
        var r字母索引DTO: String = "",
        @SerializedName("R官方网站")
        var r官方网站DTO: String = "",
        @SerializedName("R封面图")
        var r封面图DTO: String = "",
        @SerializedName("R封面图小")
        var r封面图小DTO: String = "",
        @SerializedName("R推荐星级")
        var r推荐星级DTO: Int = 0,
        @SerializedName("R播放状态")
        var r播放状态DTO: String = "",
        @SerializedName("R新番标题")
        var r新番标题DTO: String = "",
        @SerializedName("R更新时间")
        var r更新时间DTO: String = "",
        @SerializedName("R更新时间str")
        var r更新时间strDTO: String = "",
        @SerializedName("R更新时间str2")
        var r更新时间str2DTO: String = "",
        @SerializedName("R更新时间unix")
        var r更新时间unixDTO: Int = 0,
        @SerializedName("R标签")
        var r标签DTO: String = "",
        @SerializedName("R标签2")
        var r标签2DTO: List<String> = listOf(),
        @SerializedName("R标题V2")
        var r标题V2DTO: String = "",
        @SerializedName("R简介")
        var r简介DTO: String = "",
        @SerializedName("R简介_br")
        var r简介BrDTO: String = "",
        @SerializedName("R系列")
        var r系列DTO: String = "",
        @SerializedName("R网盘资源")
        var r网盘资源DTO: String = "",
        @SerializedName("R网盘资源2")
        var r网盘资源2DTO: List<Any> = listOf(),
        @SerializedName("R视频尺寸")
        var r视频尺寸DTO: String = "",
        @SerializedName("R资源类型")
        var r资源类型DTO: String = "",
        @SerializedName("R首播季度")
        var r首播季度DTO: String = "",
        @SerializedName("R首播年份")
        var r首播年份DTO: String = "",
        @SerializedName("R首播时间")
        var r首播时间DTO: String = ""
    ) {
        @kotlinx.serialization.Serializable
        data class R在线播放All(
            @SerializedName("EpName")
            var epNameDTO: String = "",
            @SerializedName("EpPic")
            var epPicDTO: String = "",
            @SerializedName("Ex")
            var exDTO: String = "",
            @SerializedName("PlayId")
            var playIdDTO: String = "",
            @SerializedName("PlayVid")
            var playVidDTO: String = "",
            @SerializedName("Title")
            var titleDTO: String = "",
            @SerializedName("Title_l")
            var titleLDTO: String = ""
        )
    }

    data class AniPreRel(
        @SerializedName("AID")
        var aIDDTO: String = "",
        @SerializedName("Href")
        var hrefDTO: String = "",
        @SerializedName("NewTitle")
        var newTitleDTO: String = "",
        @SerializedName("PicSmall")
        var picSmallDTO: String = "",
        @SerializedName("Title")
        var titleDTO: String = ""
    )

    data class AniPreSim(
        @SerializedName("AID")
        var aIDDTO: String = "",
        @SerializedName("Href")
        var hrefDTO: String = "",
        @SerializedName("NewTitle")
        var newTitleDTO: String = "",
        @SerializedName("PicSmall")
        var picSmallDTO: String = "",
        @SerializedName("Title")
        var titleDTO: String = ""
    )
}