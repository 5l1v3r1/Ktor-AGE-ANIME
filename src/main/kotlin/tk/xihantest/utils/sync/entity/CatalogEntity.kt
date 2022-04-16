package tk.xihantest.utils.sync.entity


import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class CatalogEntity(
    @SerializedName("AllCnt")
    var allCntDTO: Int = 0,
    @SerializedName("AniPreL")
    var aniPreLDTO: List<AniPreL> = listOf(),
    @SerializedName("Labels_genre")
    var labelsGenreDTO: List<String> = listOf(),
    @SerializedName("Labels_label")
    var labelsLabelDTO: List<String> = listOf(),
    @SerializedName("Labels_letter")
    var labelsLetterDTO: List<String> = listOf(),
    @SerializedName("Labels_order")
    var labelsOrderDTO: List<String> = listOf(),
    @SerializedName("Labels_region")
    var labelsRegionDTO: List<String> = listOf(),
    @SerializedName("Labels_resource")
    var labelsResourceDTO: List<String> = listOf(),
    @SerializedName("Labels_season")
    var labelsSeasonDTO: List<String> = listOf(),
    @SerializedName("Labels_status")
    var labelsStatusDTO: List<String> = listOf(),
    @SerializedName("Labels_year")
    var labelsYearDTO: List<String> = listOf(),
//    @SerializedName("PageCtrl")
//    var pageCtrlDTO: Any? = Any(),
    @SerializedName("Tip")
    var tipDTO: String = "",
    @SerializedName("WebTitle")
    var webTitleDTO: String = ""
) {
    @kotlinx.serialization.Serializable
    data class AniPreL(
        @SerializedName("AID")
        var aIDDTO: String = "",
        @SerializedName("R其他名称")
        var r其他名称DTO: String = "",
        @SerializedName("R制作公司")
        var r制作公司DTO: String = "",
        @SerializedName("R剧情类型")
        var r剧情类型DTO: List<String> = listOf(),
        @SerializedName("R动画名称")
        var r动画名称DTO: String = "",
        @SerializedName("R动画种类")
        var r动画种类DTO: String = "",
        @SerializedName("R原作")
        var r原作DTO: String = "",
        @SerializedName("R原版名称")
        var r原版名称DTO: String = "",
        @SerializedName("R封面图小")
        var r封面图小DTO: String = "",
        @SerializedName("R播放状态")
        var r播放状态DTO: String = "",
        @SerializedName("R新番标题")
        var r新番标题DTO: String = "",
        @SerializedName("R简介")
        var r简介DTO: String = "",
        @SerializedName("R首播时间")
        var r首播时间DTO: String = ""
    )
}