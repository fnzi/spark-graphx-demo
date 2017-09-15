package cn.tj.fnzi.S

object t16 {
  def main(args: Array[String]): Unit = {
    val array = Array(1, 1, 2, 2, 3, 3, 4, 5, 6)
    val tuple: (Int, Int, Int) = iteqgt(array, 3)
    println("大于3的数量:" + tuple._1)
    println("等于3的数量:" + tuple._2)
    println("小于3的数量:" + tuple._3)

  }

  def iteqgt(arr: Array[Int], v: Int) = {
    val buf = arr.toBuffer
    (buf.count(_ < v), buf.count(_ == v), buf.count(_ >= v))
  }
}
