package com.tommy.shen.wanandroidkt

import org.junit.Test
import java.util.regex.Pattern


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        // assertEquals(4, 2 + 2)
        test("【逐梦新疆·南北疆大环游】【喀纳斯】喀纳斯+禾木+可可托海+巴音布鲁克+那拉提+吐鲁番+天山天池 双卧16日游（一价全含、入住四钻酒店、升级3晚当地五星）")
    }

    fun test(str: String) {
        val list = mutableListOf<String>()
        val regex = "【(.*?)】"
        val p = Pattern.compile(regex)
        val m = p.matcher(str)
        while (m.find()) {
            println(m.group(1))
        }
//        val l = str.replace("【","<strong>")
//            .replace("】","</strong> ")
//        print(l)
    }
}