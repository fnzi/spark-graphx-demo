package cn.tj.fnzi.S

import java.io.File
import java.util.Scanner

object t10 {
  def main(args: Array[String]): Unit = {
    val in = new Scanner(new File("data/file.txt"))
    var maps = Map[String, Int]()

    var key: String = null
    while (in.hasNext()) {
      key = in.next()
      maps += (key -> (maps.getOrElse(key, 0) + 1))
    }
    maps.foreach(println)
  }
}
