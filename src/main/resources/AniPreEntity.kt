
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AniPreEntity(
    @SerialName("AID")
    var aID: String = "", // 20210258
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
    var r首播时间: String = "" // 2021-10-07
)