
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class testEntity(
    @SerialName("AniInfo")
    var aniInfo: AniInfoEntity = AniInfoEntity(),
    @SerialName("AniPreRel")
    var aniPreRel: List<AniPreRelEntity> = listOf(),
    @SerialName("AniPreSim")
    var aniPreSim: List<AnyEntity> = listOf(),
    @SerialName("Tip")
    var tip: String = "" // AGE动漫网 备用地址：<a rel="nofollow" target="_blank" href="http://www.age.tv" style="color: #60b8cc;text-decoration:  none;">www.age.tv</a> 欢迎大家分享给身边朋友！为确保正常观看，请使用谷歌浏览器。
) {
    @Serializable
    data class AniInfoEntity(
        @SerialName("AID")
        var aID: String = "", // 20210258
        @SerialName("CollectCnt")
        var collectCnt: Int = 0, // 14881
        @SerialName("CommentCnt")
        var commentCnt: Int = 0, // 158
        @SerialName("DEF_PLAYINDEX")
        var dEFPLAYINDEX: String = "", // 2
        @SerialName("FilePath")
        var filePath: String = "", // /home/wwwroot/goage_files/2021/20210258 铂金终局/text.txt
        @SerialName("LastModified")
        var lastModified: String = "", // Thu, 8 Apr 2032 18:29:11 GMT
        @SerialName("ModifiedTime")
        var modifiedTime: Int = 0, // 1649672951
        @SerialName("RankCnt")
        var rankCnt: Int = 0, // 613848
        @SerialName("R其它名称")
        var r其它名称: String = "", // Platinum End / 白金终局
        @SerialName("R制作公司")
        var r制作公司: String = "", // SIGNAL.MD
        @SerialName("R剧情类型")
        var r剧情类型: String = "", // 奇幻
        @SerialName("R剧情类型2")
        var r剧情类型2: List<String> = listOf(),
        @SerialName("R动画名称")
        var r动画名称: String = "", // 铂金终局
        @SerialName("R动画种类")
        var r动画种类: String = "", // TV
        @SerialName("R原作")
        var r原作: String = "", // 小畑健 / 大場つぐみ
        @SerialName("R原版名称")
        var r原版名称: String = "", // プラチナエンド
        @SerialName("R在线播放")
        var r在线播放: String = "", // 先导PV <play>爱奇艺接口</play>//www.iqiyi.com/v_r9jlhjakwg.html正式PV <play>PC-WEIBO</play>wbfid-1034:4678432827834371正式PV2 <play>爱奇艺接口</play>//www.iqiyi.com/v_1jjylte1lz8.html
        @SerialName("R在线播放2")
        var r在线播放2: String = "", // 第1集 <play>web_mp4</play>https%3a%2f%2fali%2dad%2ea%2eyximgs%2ecom%2fbs2%2fad%2dcreative%2dcenter%2dtemp%2fafe88eff55474eafab43dce0d03119ea%2emp4第2集 <play>web_mp4</play>https%3a%2f%2fali%2dad%2ea%2eyximgs%2ecom%2fbs2%2fad%2dcreative%2dcenter%2dtemp%2f0b4bd55c37fd46f180c69bd963055237%2emp4第3集 <play>web_mp4</play>https%3a%2f%2ftx%2dad%2ea%2eyximgs%2ecom%2fbs2%2fad%2dcreative%2dcenter%2dtemp%2f7115cf0d0aa04defab83810e240f80e4%2emp4第4集 <play>web_mp4</play>https%3a%2f%2ftx%2dad%2ea%2eyximgs%2ecom%2fbs2%2fad%2dcreative%2dcenter%2dtemp%2f80c39eeaf8654a90bb679a1f114c6813%2emp4第5集 <play>web_mp4</play>https%3a%2f%2ftx%2dad%2ea%2eyximgs%2ecom%2fbs2%2fad%2dcreative%2dcenter%2dtemp%2f145229c2c3e64688baba62253df7c995%2emp4第6集 <play>web_mp4</play>https%3a%2f%2fali%2dad%2ea%2eyximgs%2ecom%2fbs2%2fad%2dcreative%2dcenter%2dtemp%2f1958995b8bf54e7b8f8bd02e702a5543%2emp4第7集 <play>web_mp4</play>https%3a%2f%2fjs%2dad%2ea%2eyximgs%2ecom%2fbs2%2fad%2dcreative%2dcenter%2dtemp%2f82a327de07304ea590f74d7c19119eb7%2emp4第8集 <play>web_mp4</play>https%3a%2f%2ftx%2dad%2ea%2eyximgs%2ecom%2fbs2%2fad%2dcreative%2dcenter%2dtemp%2f8d5309529533425e9edc40f1cf5fe88d%2emp4第9集 <play>web_mp4</play>https%3a%2f%2fali%2dad%2ea%2eyximgs%2ecom%2fbs2%2fad%2dcreative%2dcenter%2dtemp%2ff6ef10e4469c4514a5b77f780072eb8b%2emp4第10集 <play>web_mp4</play>https%3a%2f%2fali%2dad%2ea%2eyximgs%2ecom%2fbs2%2fad%2dcreative%2dcenter%2dtemp%2f2cc25cd286c14b96b59c7bb1bc26c8b7%2emp4第11集 <play>web_mp4</play>https%3a%2f%2ftx%2dad%2ea%2eyximgs%2ecom%2fbs2%2fad%2dcreative%2dcenter%2dtemp%2f3cae250cdec44f1997117726216b20a2%2emp4第12集 <play>web_mp4</play>https%3a%2f%2ftx%2dad%2ea%2eyximgs%2ecom%2fbs2%2fad%2dcreative%2dcenter%2dtemp%2fea1896a11a414c5a8c53073606e07fd5%2emp4第13集 <play>web_mp4</play>https%3a%2f%2fali%2dad%2ea%2eyximgs%2ecom%2fbs2%2fad%2dcreative%2dcenter%2dtemp%2f96a4c85331e842259b02a6fee12d2bdf%2emp4第14集 <play>web_mp4</play>https%3a%2f%2fjs%2dad%2ea%2eyximgs%2ecom%2fbs2%2fad%2dcreative%2dcenter%2dtemp%2ffb279d66591b493a8c995b7ac7ca59ba%2emp4第15集 <play>web_mp4</play>https%3a%2f%2fjs%2dad%2ea%2eyximgs%2ecom%2fbs2%2fad%2dcreative%2dcenter%2dtemp%2f050fcfdd14d241f58367b8688656c8b3%2emp4第16集 <play>web_mp4</play>https%3a%2f%2ftx%2dad%2ea%2eyximgs%2ecom%2fbs2%2fad%2dcreative%2dcenter%2dtemp%2f44b5478d2a6a457e99b62027d0111046%2emp4第17集 <play>web_mp4</play>https%3a%2f%2fali%2dad%2ea%2eyximgs%2ecom%2fbs2%2fad%2dcreative%2dcenter%2dtemp%2f04159bba818d4c61b07faeb0abf42c35%2emp4第18集 <play>web_mp4</play>https%3a%2f%2fali%2dad%2ea%2eyximgs%2ecom%2fbs2%2fad%2dcreative%2dcenter%2dtemp%2f132729f54993477784ddbea7f26c7449%2emp4第19集 <play>web_mp4</play>https%3a%2f%2fali%2dad%2ea%2eyximgs%2ecom%2fbs2%2fad%2dcreative%2dcenter%2dtemp%2fae66b0212b6f4ced87b09a53433c68d5%2emp4第20集 <play>ttm3p</play>%2ftmm3p%2findex%2em3u8%3fpath%3d%252fttm3p%252fttm3p%252dtsdm%252d%25e7%2599%25bd%25e9%2587%2591%25e7%25bb%2588%25e5%25b1%2580%255f%25e9%2593%2582%25e9%2587%2591%25e7%25bb%2588%25e5%25b1%2580%252d%25e7%25ac%25ac20%25e9%259b%2586%2em3u8第21集 <play>ttm3p</play>%2ftmm3p%2findex%2em3u8%3fpath%3d%252fttm3p%252fttm3p%252dtsdm%252d%25e7%2599%25bd%25e9%2587%2591%25e7%25bb%2588%25e5%25b1%2580%255f%25e9%2593%2582%25e9%2587%2591%25e7%25bb%2588%25e5%25b1%2580%252d%25e7%25ac%25ac21%25e9%259b%2586%2em3u8第22集 <play>ttm3p</play>%2ftmm3p%2findex%2em3u8%3fpath%3d%252fttm3p%252fttm3p%252dtsdm%252d%25e7%2599%25bd%25e9%2587%2591%25e7%25bb%2588%25e5%25b1%2580%255f%25e9%2593%2582%25e9%2587%2591%25e7%25bb%2588%25e5%25b1%2580%252d%25e7%25ac%25ac22%25e9%259b%2586%2em3u8第23集 <play>ttm3p</play>%2ftmm3p%2findex%2em3u8%3fpath%3d%252fttm3p%252fttm3p%252dtsdm%252d%25e7%2599%25bd%25e9%2587%2591%25e7%25bb%2588%25e5%25b1%2580%255f%25e9%2593%2582%25e9%2587%2591%25e7%25bb%2588%25e5%25b1%2580%252d%25e7%25ac%25ac23%25e9%259b%2586%2em3u8第24集 <play>ttm3p</play>%2ftmm3p%2findex%2em3u8%3fpath%3d%252fttm3p%252fttm3p%252dtsdm%252d%25e7%2599%25bd%25e9%2587%2591%25e7%25bb%2588%25e5%25b1%2580%255f%25e9%2593%2582%25e9%2587%2591%25e7%25bb%2588%25e5%25b1%2580%252d%25e7%25ac%25ac24%25e9%259b%2586%2em3u8
        @SerialName("R在线播放3")
        var r在线播放3: String = "", // 第1集 <play>web_mp4</play>https%3A%2F%2Fbbx-video.gtimg.com%2Fyinghua_0bcaaeaaiaaaxqagi3wonjqxkaodaqaqabca.f0.mp4%3Fdis_k%3D055feffd30868f675d56b63b55dbf491%26dis_t%3D1642575015第2集 <play>web_mp4</play>https%3A%2F%2Fbbx-video.gtimg.com%2Fyinghua_0bca3uaacaaahyagdy6osrqxlxodahoqaaka.f0.mp4%3Fdis_k%3Ddace0c3b396538c584413729ad68b28a%26dis_t%3D1642574611第3集 <play>web_mp4</play>https%3A%2F%2Fbbx-video.gtimg.com%2Fyinghua_0bcazqaacaaayyagbdoosrqxltgdahgaaaka.f0.mp4%3Fdis_k%3D9c1d39a5ebeedd24d6f195800451ec37%26dis_t%3D1642574757第4集 <play>web_mp4</play>https%3A%2F%2Fbbx-video.gtimg.com%2Fyinghua_0bca3qaacaaatuagbh6osrqxlxgdahoaaaka.f0.mp4%3Fdis_k%3Dcdf5202ca41ddcbc06dfa1f0c834bd53%26dis_t%3D1642574552第5集 <play>web_mp4</play>https%3A%2F%2Fbbx-video.gtimg.com%2Fyinghua_0bcagaaakaaaluagipgosnqxkmgdauyaabka.f0.mp4%3Fdis_k%3Dcb77e81cd8ecedad7aa8f50c41e03032%26dis_t%3D1642574681第6集 <play>web_mp4</play>https%3A%2F%2Fbbx-video.gtimg.com%2Fyinghua_0bcaaaaagaaatiage2gorvqxkagdamaaaa2a.f0.mp4%3Fdis_k%3D12e652bb8ef3822a9ea54f6103dd5c7d%26dis_t%3D1642575060第7集 <play>web_mp4</play>https%3A%2F%2Fbbx-video.gtimg.com%2Fyinghua_0bcazaaacaaao4agbngor5qxlsgdaheaaaka.f0.mp4%3Fdis_k%3Dd1b97b4aac59eade5e4e01b08039b1d7%26dis_t%3D1642575069第8集 <play>web_mp4</play>https%3A%2F%2Fbbx-video.gtimg.com%2Fyinghua_0bcapuaaeaaasuaggb6oqzqxk7odaj6qaasa.f0.mp4%3Fdis_k%3D207bda9f17eb44b06ce21b67088e61da%26dis_t%3D1642575078第9集 <play>web_mp4</play>https%3A%2F%2Fbbx-video.gtimg.com%2Fyinghua_0bcae4aakaaag4agoegonnqxkj6dautqabka.f0.mp4%3Fdis_k%3D2c87341c411b1776ac5b4602eb251673%26dis_t%3D1642575088第10集 <play>web_mp4</play>https%3A%2F%2Fbbx-video.gtimg.com%2Fyinghua_0bcas4aaeaaaumagbcwoqnqxlf6daklqaasa.f0.mp4%3Fdis_k%3Deb8f2ce5962677f963822d0cf0e93f3c%26dis_t%3D1642575097第11集 <play>web_mp4</play>https%3A%2F%2Fbbx-video.gtimg.com%2Fyinghua_0bcahiaaeaaamyagajgoqjqxkowdai5aaasa.f0.mp4%3Fdis_k%3D8f684ba2ebe9ed8c45d45b1d97f70697%26dis_t%3D1642575106第12集 <play>web_mp4</play>https%3A%2F%2Fbbx-video.gtimg.com%2Fyinghua_0bcadyaakaaatiago66onbqxkhwdaupaabka.f0.mp4%3Fdis_k%3D5e870545a75ac1fd0ff817a8fea75fcc%26dis_t%3D1642575002第13集 <play>web_mp4</play>https%3A%2F%2Fbbx-video.gtimg.com%2Fyinghua_0bcavmaaqaaa5mamlupcc5qxlk6dbcvqacca.f0.mp4%3Fdis_k%3D3d6b52cc6baef641f22020c8f4ce3b9e%26dis_t%3D1642575124第14集 <play>web_mp4</play>https%3A%2F%2Fbbx-video.gtimg.com%2Fyinghua_0bca6aaaoaaa3maanzxixbqxl4gda7yaab2a.f0.mp4%3Fdis_k%3D82d30ebb1ed306f4c9dc512664e87f5b%26dis_t%3D1642611598第15集 <play>web_mp4</play>https%3A%2F%2Fbbx-video.gtimg.com%2Fyinghua_0bcanaaacaaa5mae26hpvvqxk2gdafuaaaka.f0.mp4%3Fdis_k%3D092f262ada50a34068356f7d42d3feb1%26dis_t%3D1643079104第16集 <play>PC-emodm2</play>yj3456__v7293266492767695199727716221072428_7057180865083825188_baijinzj16第17集 <play>PC-emodm2</play>yj3456__v3186239132926766547527716221072428_7059057795252935693_baijinzj017第18集 <play>PC-emodm2</play>yj3456__v5213274738618534759227716221072428_7062691492762115079_baijinzj018第19集 <play>PC-emodm2</play>yj3456__v2278146221575613641127716221072428_7066458121882983461_baijinzj019第20集 <play>PC-emodm2</play>yj3456__v1448857354347299722527716221072428_7066458121882983461_baijinzj020第21集 <play>PC-emodm2</play>yj3456__v8591336373582237133827716221072428_7070935811117587470_bojinzj021第22集 <play>PC-emodm2</play>yj3456__v2971661886781554287927716221072428_7073892313428270087_baijinzj22第23集 <play>PC-emodm2</play>yj3456__v7315311564747933226627716221072428_7076618728762367013_baijinzj23第24集 <play>PC-emodm2</play>yj3456__v9974918921254227377727716221072428_7079218684824230926_baijinzj24
        @SerialName("R在线播放4")
        var r在线播放4: String = "", // 第1集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季01.m3u8第2集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季02.m3u8第3集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季03.m3u8第4集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季04.m3u8第5集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季05.m3u8第6集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季06.m3u8第7集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季07.m3u8第8集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季08.m3u8第9集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季09.m3u8第10集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季10.m3u8第11集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季11.m3u8第12集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季12.m3u8第13集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季13.m3u8第14集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季14.m3u8第15集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季15.m3u8第16集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季16.m3u8第17集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季17.m3u8第18集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季18.m3u8第19集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季19.m3u8第20集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季20.m3u8第21集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季21.m3u8第22集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季22.m3u8第23集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季23.m3u8第24集 <play>zjm3u8</play>https://dy.jx1024.com:8443/uploads/铂金终局第一季24.m3u8
        @SerialName("R在线播放All")
        var r在线播放All: List<List<R在线播放AllEntity>> = listOf(),
        @SerialName("R地区")
        var r地区: String = "", // 日本
        @SerialName("R备用")
        var r备用: String = "",
        @SerialName("R字母索引")
        var r字母索引: String = "", // B
        @SerialName("R官方网站")
        var r官方网站: String = "", // https://anime-platinumend.com
        @SerialName("R封面图")
        var r封面图: String = "", // //tvax3.sinaimg.cn/large/008kBpBlgy1gvvaotw0b0j307409waan.jpg
        @SerialName("R封面图小")
        var r封面图小: String = "", // //tvax3.sinaimg.cn/large/008kBpBlgy1gvvaotpm6sj304605s74c.jpg
        @SerialName("R推荐星级")
        var r推荐星级: Int = 0, // 2
        @SerialName("R播放状态")
        var r播放状态: String = "", // 完结
        @SerialName("R新番标题")
        var r新番标题: String = "", // 第24集(完结)
        @SerialName("R更新时间")
        var r更新时间: String = "", // 20220411182911
        @SerialName("R更新时间str")
        var r更新时间str: String = "", // 2022-04-11 18:29:11
        @SerialName("R更新时间str2")
        var r更新时间str2: String = "", // 2022-04-11
        @SerialName("R更新时间unix")
        var r更新时间unix: Int = 0, // 1649672951
        @SerialName("R标签")
        var r标签: String = "", // 奇幻
        @SerialName("R标签2")
        var r标签2: List<String> = listOf(),
        @SerialName("R标题V2")
        var r标题V2: String = "", // 1
        @SerialName("R简介")
        var r简介: String = "", // 电视动画片《白金终局》改编自大场鸫原作、小畑健作画的同名漫画作品，于2020年12月19日宣布制作决定。该片由SIGNAL.MD负责制作，于2021年10月起播出 。“我可以给你‘活下去’的希望。”架桥明日因为一场意外而失去家人，在收养他的亲戚家每天都过得非常辛苦。对一切都感到绝望的少年，在国中毕业当天选择从大楼屋顶跳楼。不过，此时少年遇见了1位天使。
        @SerialName("R简介_br")
        var r简介Br: String = "", // 电视动画片《白金终局》改编自大场鸫原作、小畑健作画的同名漫画作品，于2020年12月19日宣布制作决定。该片由SIGNAL.MD负责制作，于2021年10月起播出 。<br/>“我可以给你‘活下去’的希望。”<br/>架桥明日因为一场意外而失去家人，在收养他的亲戚家每天都过得非常辛苦。对一切都感到绝望的少年，在国中毕业当天选择从大楼屋顶跳楼。不过，此时少年遇见了1位天使。
        @SerialName("R系列")
        var r系列: String = "", // 铂金终局
        @SerialName("R网盘资源")
        var r网盘资源: String = "", // (暂缺)
        @SerialName("R网盘资源2")
        var r网盘资源2: List<AnyEntity> = listOf(),
        @SerialName("R视频尺寸")
        var r视频尺寸: String = "", // 720P/1080P
        @SerialName("R资源类型")
        var r资源类型: String = "",
        @SerialName("R首播季度")
        var r首播季度: String = "", // 10
        @SerialName("R首播年份")
        var r首播年份: String = "", // 2021
        @SerialName("R首播时间")
        var r首播时间: String = "" // 2021-10-07
    ) {
        @Serializable
        data class R在线播放AllEntity(
            @SerialName("EpName")
            var epName: String = "", // PV
            @SerialName("EpPic")
            var epPic: String = "",
            @SerialName("Ex")
            var ex: String = "",
            @SerialName("PlayId")
            var playId: String = "", // <play>爱奇艺接口</play>
            @SerialName("PlayVid")
            var playVid: String = "", // //www.iqiyi.com/v_r9jlhjakwg.html
            @SerialName("Title")
            var title: String = "", // 先导PV
            @SerialName("Title_l")
            var titleL: String = "" // 先导PV
        )
    }

    @Serializable
    data class AniPreRelEntity(
        @SerialName("AID")
        var aID: String = "", // 20210258
        @SerialName("Href")
        var href: String = "", // /detail/20210258
        @SerialName("NewTitle")
        var newTitle: String = "", // 第24集(完结)
        @SerialName("PicSmall")
        var picSmall: String = "", // //tvax3.sinaimg.cn/large/008kBpBlgy1gvvaotpm6sj304605s74c.jpg
        @SerialName("Title")
        var title: String = "" // 铂金终局
    )
}