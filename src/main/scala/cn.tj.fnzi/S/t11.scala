package cn.tj.fnzi.S

import java.io.File
import java.util.Scanner

import scala.collection.immutable.SortedMap

object t11 {
  def main(args: Array[String]): Unit = {
    val in = new Scanner(new File("data/file.txt"))
    var sortMap = SortedMap[String, Int]()
    var key: String = null
    while (in.hasNext()) {
      key = in.next()
      sortMap += (key -> (sortMap.getOrElse(key, 0) + 1))
    }
    sortMap.foreach(println)
  }
}
