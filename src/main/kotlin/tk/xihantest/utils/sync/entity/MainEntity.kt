package tk.xihantest.utils.sync.entity


import com.google.gson.annotations.SerializedName

data class MainEntity(
    @SerializedName("detailUrl")
    var detailUrlDTO: String = "",
    @SerializedName("Main")
    var mainDTO: Main = Main(),
    @SerializedName("MyAge")
    var myAgeDTO: Boolean = false,
    @SerializedName("payCode")
    var payCodeDTO: String = "",
    @SerializedName("下载MD5")
    var 下载MD5DTO: String = "",
    @SerializedName("下载Url")
    var 下载UrlDTO: String = "",
    @SerializedName("今日密码")
    var 今日密码DTO: String = "",
    @SerializedName("免责声明Url")
    var 免责声明UrlDTO: String = "",
    @SerializedName("博客Url")
    var 博客UrlDTO: String = "",
    @SerializedName("启动图MD5")
    var 启动图MD5DTO: String = "",
    @SerializedName("启动图Url")
    var 启动图UrlDTO: String = "",
    @SerializedName("官网Url")
    var 官网UrlDTO: String = "",
    @SerializedName("应用版本号")
    var 应用版本号DTO: String = "",
    @SerializedName("应用版本名")
    var 应用版本名DTO: String = "",
    @SerializedName("强制更新")
    var 强制更新DTO: Boolean = false,
    @SerializedName("更新Url")
    var 更新UrlDTO: String = "",
    @SerializedName("更新日志")
    var 更新日志DTO: String = "",
    @SerializedName("详情Url")
    var 详情UrlDTO: String = ""
) {
    data class Main(
        @SerializedName("aidIn")
        var aidInDTO: List<String> = listOf(),
        @SerializedName("Labels")
        var labelsDTO: Labels = Labels(),
        @SerializedName("图片组")
        var 图片组DTO: List<图片组> = listOf(),
        @SerializedName("广告Url")
        var 广告UrlDTO: List<String> = listOf(),
        @SerializedName("白名单Url")
        var 白名单UrlDTO: List<String> = listOf(),
        @SerializedName("自定义API")
        var 自定义APIDTO: List<自定义API> = listOf(),
        @SerializedName("设备ID")
        var 设备IDDTO: List<String> = listOf()
    ) {
        data class Labels(
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
            var labelsYearDTO: List<String> = listOf()
        )

        data class 图片组(
            @SerializedName("md5")
            var md5DTO: String = "",
            @SerializedName("picUrl")
            var picUrlDTO: String = ""
        )

        data class 自定义API(
            @SerializedName("ApiName")
            var apiNameDTO: String = "",
            @SerializedName("ApiUrl")
            var apiUrlDTO: String = ""
        )
    }
}