package tk.xihantest.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import tk.xihantest.models.JsonResult
import tk.xihantest.utils.sync.Main


fun Application.configStartSyncAnimeData(){
    install(StartSyncAnimeData.ApplicationCallPlugin){
        startSyncUrl = "/v1/startSyncAnimeData"

    }


}

public class StartSyncAnimeData(public val url: String) {

    /**
     * Does application shutdown using the specified [call]
     */
    public suspend fun startSync(call: ApplicationCall) {
        call.application.log.warn("Start Sync Anime Data")
        val application = call.application
        val environment = application.environment

        val latch = CompletableDeferred<Nothing>()
        call.application.launch {
            latch.join()

            Main.start()
        }

        try {
            //println("触发")
            call.respond(JsonResult("","200","触发成功"))
        } finally {
            //println("关闭")
            latch.cancel()
        }
    }

    /**
     * A plugin to install into engine pipeline
     */
    public object EnginePlugin : BaseApplicationPlugin<EnginePipeline, Config, StartSyncAnimeData> {
        override val key: AttributeKey<StartSyncAnimeData> = AttributeKey<StartSyncAnimeData>("startSync.url")

        override fun install(pipeline: EnginePipeline, configure: Config.() -> Unit): StartSyncAnimeData {
            val config = Config()
            configure(config)

            val plugin = StartSyncAnimeData(config.startSyncUrl)
            pipeline.intercept(EnginePipeline.Before) {
                if (call.request.uri == plugin.url) {
                    plugin.startSync(call)
                }
            }
            return plugin
        }
    }

    /**
     * Shutdown url configuration builder
     */
    @KtorDsl
    public class Config {
        /**
         * URI to handle shutdown requests
         */
        public var startSyncUrl: String = "/ktor/xihantest/startSyncAnimeData"

    }

    public companion object {

        /**
         * A plugin to install into application call pipeline
         */
        public val ApplicationCallPlugin: BaseApplicationPlugin<Application, Config, PluginInstance> =
            createApplicationPlugin("startSync.url", ::Config) {
                val plugin = StartSyncAnimeData(pluginConfig.startSyncUrl)

                onCall { call ->
                    if (call.request.uri == plugin.url) {
                        plugin.startSync(call)
                    }
                }
            }
    }

}