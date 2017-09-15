package cn.tj.fnzi.S

import java.io.File
import java.util.Scanner

import scala.collection.JavaConversions.mapAsScalaMap// ???


object t12 {
  def main(args: Array[String]): Unit = {
    val in = new Scanner(new File("data/file.txt"))
    val map: scala.collection.mutable.Map[String, Int] = new java.util.TreeMap[String, Int]
    var key: String = null
    while (in.hasNext()) {
      key = in.next()
      map(key) = map.getOrElse(key, 0) + 1
      //map += (key -> (map.getOrElse(key, 0) + 1))
    }
    map.foreach(println)
  }
}
