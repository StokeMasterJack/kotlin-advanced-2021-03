package kws.a_scopes

import kotlin.test.Test
import kotlin.test.assertEquals

private const val expected = "aa\nbb\ncc\n"

class StrBuilder1Test {

    @Test
    fun testStr1() {
        val str1: String = buildStr1 {
            //val b = StringBuilder()
            it.appendLine("aa")
            it.appendLine("bb")
            it.appendLine("cc")
            //return b.toString()
        }
        assertEquals(expected, str1)
    }

    @Test
    fun testStr2() {
        val str2 = buildStr2 {
            appendLine("aa")
            appendLine("bb")
            appendLine("cc")
        }
        assertEquals(expected, str2)
    }

}

fun buildStr2(action: StringBuilder.() -> Unit): String {
    val b = StringBuilder()
//    action(b)
    b.action() //b (from inside the lambda) is referenced using "this"
    return b.toString()
}

fun buildStr1(action: (StringBuilder) -> Unit): String {
    val b = StringBuilder()
    action(b)  //b (from inside the lambda) is referenced using "it"
    return b.toString()
}