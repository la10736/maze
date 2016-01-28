/**
 * Modulo di utility
 *
 * Created by michele on 30/12/15.
 */

package utils

operator fun String.times(times:Int): String {
    val sb: StringBuilder = StringBuilder()
    for (i in 1..times){
        sb.append(this)
    }
    return sb.toString()
}

public inline fun <reified INNER> array2d(sizeOuter: Int, sizeInner: Int, init: (Int, Int)->INNER):
        Array<Array<INNER>> = Array(sizeOuter) { outer -> Array<INNER>(sizeInner, {inner -> init(outer, inner)} ) }
public fun array2dOfInt(sizeOuter: Int, sizeInner: Int): Array<IntArray> = Array(sizeOuter) { IntArray(sizeInner) }
public fun array2dOfLong(sizeOuter: Int, sizeInner: Int): Array<LongArray> = Array(sizeOuter) { LongArray(sizeInner) }
public fun array2dOfByte(sizeOuter: Int, sizeInner: Int): Array<ByteArray> = Array(sizeOuter) { ByteArray(sizeInner) }
public fun array2dOfChar(sizeOuter: Int, sizeInner: Int): Array<CharArray> = Array(sizeOuter) { CharArray(sizeInner) }