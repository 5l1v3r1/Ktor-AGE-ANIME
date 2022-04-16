package tk.xihantest


import cn.xihan.http.*
import cn.xihan.http.annotation.HttpRename
import cn.xihan.http.config.IRequestApi
import cn.xihan.http.config.IRequestInterceptor
import cn.xihan.http.listener.OnHttpListener
import cn.xihan.http.model.HttpHeaders
import cn.xihan.http.model.HttpParams
import cn.xihan.http.model.RequestHandler
import cn.xihan.http.request.HttpRequest
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import tk.xihantest.models.User
import tk.xihantest.plugins.*
import java.lang.Exception
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.*


class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }

    @Test
    fun main() = testApplication {
        initEasyHttp()


        val user = User(0,
            "xihan",
            "12345678",
            "srxqzxs@vip.qq.com",
            "12345678",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        val json = Json.encodeToString(user)
        //println("json: $json")
        val mediaType = "application/json".toMediaType()
        val requestBody: RequestBody = json.toRequestBody(mediaType)

        /*
        EasyHttp.post()
            .api("/accounts/signup")
            //.body(requestBody)
            .json(json)
            .request(object: OnHttpListener<String> {

                override fun onSucceed(result: String?) {
                    println("r: $result")
                }

                override fun onFail(e: Exception?) {
                    println("错误日志: ${e?.toString()}")
                }

            })
*/

        EasyHttp.get()
            .api("/json/kotlinx-serialization/1")
            .request(object : OnHttpListener<String> {
                override fun onSucceed(result: String?) {
                    println("r: $result")
                }

                override fun onFail(e: Exception?) {
                    println("错误日志: ${e?.toString()}")
                }

            })


        /*
        EasyHttp.delete()
            .api(deleteAccountsRequestApi().setCancelAccounts("xihan","123456"))
            .request(object : OnHttpListener<String> {
                override fun onSucceed(result: String?) {
                    println("result: $result")
                }

                override fun onFail(e: Exception?) {
                    println("错误日志: ${e?.message}")
                }

            })
         */
//        EasyHttp.get()
//            .api("/accounts/login")
//            .request(object : OnHttpListener<String> {
//                override fun onSucceed(result: String?) {
//                    println("r: $result")
//                }
//
//                override fun onFail(e: Exception?) {
//                    println("错误日志: ${e?.message}")
//                }
//
//            })
    }

    private fun initEasyHttp() {
        EasyConfig.with(OkHttpClient())
            .setLogEnabled(true)
            .setServer { "http://127.0.0.1:8080" }
            .setHandler(RequestHandler())
            .setInterceptor(object : IRequestInterceptor {
                override fun interceptArguments(httpRequest: HttpRequest<*>?, params: HttpParams?, headers: HttpHeaders?) {
                    headers?.put("timestamp", System.currentTimeMillis().toString())
                }
            })
            .setRetryCount(2)
            .setRetryTime(2000) //.addHeader("Connection", "close")
            .into()
    }
}

class signupAccountRequestApi : IRequestApi {
    @HttpRename("name")
    var username: String = ""
    @HttpRename("pass")
    var password: String = ""
    @HttpRename("email")
    var email: String = ""

    override fun getApi(): String {
        return "/accounts/signup"
    }

    fun setSignupAccount(username: String, password: String, email: String): signupAccountRequestApi {
        this.username = username
        this.password = password
        this.email = email
        return this
    }

}

class deleteAccountsRequestApi : IRequestApi{

    override fun getApi(): String {
       return "/accounts/cancel"
    }

    @HttpRename("name")
    var accountsName = ""
    @HttpRename("pass")
    var accountsPassword = ""


    fun setCancelAccounts(accountsName: String, accountsPassword: String) : deleteAccountsRequestApi {
        this.accountsName = accountsName
        this.accountsPassword = accountsPassword
        return this
    }


}
