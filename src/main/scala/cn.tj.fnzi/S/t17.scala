package cn.tj.fnzi.S

object t17 {
  def main(args: Array[String]): Unit = {
    val tuple = "Hello".zip("World90")
    println(tuple)
    tuple.toMap
    tuple.foreach(println)


    val t = List(1,2,3,2).zip(List(4,5,6))
    println(t)
  }
}
