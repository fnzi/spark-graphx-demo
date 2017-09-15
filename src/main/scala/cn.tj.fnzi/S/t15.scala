package cn.tj.fnzi.S

object t15 {
  def main(args: Array[String]): Unit = {
    val array = Array(1, 2, 3, 4, 5)
    var tuple: (Int, Int) = minmax(array)
    println(tuple._1 + " " + tuple._2)
    println(tuple)
  }

  def minmax(arr: Array[Int]) = {
    (arr.min, arr.max)
  }
}
