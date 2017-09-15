package cn.tj.fnzi.S

import java.util.Calendar

import scala.collection.mutable

object t13 {
  def main(args: Array[String]): Unit = {
    val map = new mutable.LinkedHashMap[String, Int]
    map += ("Monday" -> Calendar.MONDAY)
    map += ("Tuesday" -> Calendar.TUESDAY)
    map += ("Thursday" -> Calendar.THURSDAY)
    map += ("Wednesday" -> Calendar.WEDNESDAY)
    map += ("Friday" -> Calendar.FRIDAY)
    map += ("Saturday" -> Calendar.SATURDAY)
    map += ("Sunday" -> Calendar.SUNDAY)
    map.foreach(println)
  }
}
