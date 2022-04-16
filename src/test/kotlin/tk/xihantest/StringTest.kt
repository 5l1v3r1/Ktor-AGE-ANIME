package tk.xihantest

import io.ktor.server.testing.*
import org.junit.Test
import tk.xihantest.utils.decryptAES
import tk.xihantest.utils.encryptAES
import tk.xihantest.utils.toDateMills


class StringTest {

    @Test
    fun test() = testApplication {
        val str = "Hello World"
        val encryptStr = str.encryptAES()

        val dataTime = "2022-04-10 22:22:44"
        println("dataTime: $dataTime" + "\n1:" + dataTime.toDateMills())


        val list = listOf(1, 2, 3, 4, 5,6,7,8,9,10)
        val list2 = listOf(1, 2, 3, 4, 5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20)
        list2.forEach { i ->
            list.find { it == i }?: println("not found $i")
        }




//        println(str)
//        println("Base64 加密: " + str.encryptAES())
//        println("Base64 解密: " + encryptStr.decryptAES())
    }

}