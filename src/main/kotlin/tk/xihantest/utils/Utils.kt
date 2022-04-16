package tk.xihantest.utils


import com.sun.mail.util.MailSSLSocketFactory
import io.ktor.utils.io.charsets.*
import tk.xihantest.utils.sync.Config.path
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


object Utils {

    // 判断字符串是否为null或长度为0
    fun isNull(str: String?): Boolean {
        return str == null || str.isEmpty() || str.trim { it <= ' ' }.isEmpty()
    }

    // 读取文件内容
    fun readFile(filePath: String): String {
        val file = File(filePath)
        val reader = BufferedReader(InputStreamReader(FileInputStream(file), StandardCharsets.UTF_8))
        val sb = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            sb.appendLine(line)
        }
        reader.close()
        return sb.toString()
    }


    fun sendMail(emailName: String, forgetPass: String): Boolean {
        var isSuccess = true
        try {
            val props = Properties()
            val sf = MailSSLSocketFactory()
            sf.isTrustAllHosts = true
            props["mail.smtp.host"] = "smtp.qq.com"
            props["mail.smtp.port"] = "465"
            props["mail.smtp.auth"] = "true"
            props["mail.smtp.ssl.enable"] = "true"
            props["mail.smtp.ssl.trust"] = "smtp.qq.com"
            props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
            props["mail.smtp.socketFactory.fallback"] = "false"
            props["mail.smtp.socketFactory.port"] = "465"
            props["mail.smtp.ssl.enable"] = "true"
            props["mail.smtp.ssl.socketFactory"] = sf
            val session: Session = Session.getDefaultInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication("发送邮箱", "16位授权码")
                }
            })
            session.debug = false
            //获取连接对象
            val transport: Transport = session.transport
            //连接服务器
            transport.connect("smtp.qq.com", "发送邮箱", "16位授权码")

            val mimeMessage = MimeMessage(session)

            //邮件发送人
            mimeMessage.setFrom(InternetAddress("发送邮箱"))
            //邮件接收人
            mimeMessage.setRecipient(Message.RecipientType.TO, InternetAddress(emailName))

            //邮件标题
            mimeMessage.subject = "这是你忘记的密码┗|｀O′|┛ 欧~~";

            //邮件内容
            mimeMessage.setContent("你的密码为:$forgetPass", "text/html;charset=UTF-8")

            //发送邮件
            transport.sendMessage(mimeMessage, mimeMessage.allRecipients)

            //关闭连接
            transport.close()


        } catch (e: Exception) {
            isSuccess = false
            e.printStackTrace()
        }
        return isSuccess
    }


}

// 判断字符串用户名是否小于16
fun String.isUserName(): Boolean {
    return Regex("^[a-zA-Z\\d]{1,16}$").matches(this@isUserName)
}

// 判断所有字符串长度大于8小于32
fun String.isPassword(): Boolean {
    return Regex("^([A-Z]|[a-z]|\\d|[`~!@#\$%^&*()+=|{}':;,\\\\.<>/?！￥…（）—【】‘；：”“’。，、？]){8,32}$").matches(
        this@isPassword)
}

// 验证邮箱合法性
fun String.isEmail(): Boolean {
    return Regex("^[a-zA-Z\\d][\\w.-]*[a-zA-Z\\d]@[a-zA-Z\\d][\\w.-]*[a-zA-Z\\d]\\.[a-zA-Z][a-zA-Z.]*[a-zA-Z]$").matches(
        this@isEmail)
}

/**
 * 覆盖写入内容
 *
 * @param fileName 保存的文件名
 */
fun String.writeJsonFile(fileName: String) {
    val file = File(path + fileName)
    file.writeText(this)
}

/**
 * 追加写入内容
 * param isFirst 是否是第一次写入
 */
fun String.writeTxtFile(isFirst: Boolean = false, fileName: String = "info.txt") {
    try {
        val file = File(path + fileName)
        if (isFirst) {
            file.writeText("")
        }
        file.appendText(this@writeTxtFile + "\n")
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

// 判断字符串是否为Url
fun String.isUrl(): Boolean {
    return Regex("^(https?)://[-a-zA-Z\\d+&@#/%?=~_|!:,.;]*[-a-zA-Z\\d+&@#/%=~_|]").matches(if (hasUrlEncoded(this)) URLDecoder.decode(
        this,
        "UTF-8") else this)
}

// Url解码
fun String.UrlDecoder(): String {
    return if (hasUrlEncoded(this)) URLDecoder.decode(this, "UTF-8") else this
}

// Url编码
fun String.UrlEncoder(): String {
    return if (hasUrlEncoded(this)) URLEncoder.encode(this, "UTF-8") else this
}

private fun hasUrlEncoded(str: String): Boolean {
    var encode = false
    for (i in str.indices) {
        val c = str[i]
        if (c == '%' && i + 2 < str.length) {
            // 判断是否符合urlEncode规范
            val c1 = str[i + 1]
            val c2 = str[i + 2]
            if (isValidHexChar(c1) && isValidHexChar(c2)) {
                encode = true
            }
            break
        }
    }
    return encode
}

private fun isValidHexChar(c: Char): Boolean {
    return c in '0'..'9' || c in 'a'..'f' || c in 'A'..'F'
}




