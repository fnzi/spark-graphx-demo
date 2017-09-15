package cn.tj.fnzi.S

import scala.collection.JavaConversions.propertiesAsScalaMap

object t14 {
  def main(args: Array[String]): Unit = {

    val props: scala.collection.Map[String, String] = System.getProperties()
    val keys = props.keySet
    val keylens = for (i <- keys)
      yield
        i.length()

    val keymaxlen = keylens.max
    for (key <- keys) {
      print(key)
      print(" " * (keymaxlen - key.length()))
      print("|")
      println(props(key))
    }
  }
}
