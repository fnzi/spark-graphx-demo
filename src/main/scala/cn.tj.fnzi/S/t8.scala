package cn.tj.fnzi.S

object t8 {
  def main(args: Array[String]): Unit = {
    val map = Map("Computer" -> 3000, "Iphone" -> 2000, "Cup" -> 10)
    val fold = for ((k, v) <- map)
      yield (k, v * 0.9)
    fold.foreach(println)

    println(fold("Iphone"))
  }
}
