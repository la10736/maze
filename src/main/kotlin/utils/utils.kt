/**
 * Package di utilities
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


public fun <T : Comparable<T>> max(first : T, vararg elements:T) : T{
    val other = elements.max() ?: return first
    return if (first > other) {first} else {other}
}

public fun <T : Comparable<T>> min(first : T, vararg elements:T) : T{
    val other = elements.min() ?: return first
    return if (first < other) {first} else {other}
}

public fun <E> List<E>.subListSlice(fromIndex: Int=0, toIndex: Int=0): List<E> {
    val f = max(0, if (fromIndex >= 0) { fromIndex } else {size + fromIndex })
    val t = min(size, if (toIndex > 0) { toIndex } else {size + toIndex })

    if (f > t){
        return emptyList()
    }
    return subList(f, t)
}
