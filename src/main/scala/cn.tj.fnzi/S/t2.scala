package cn.tj.fnzi.S

object t2 {
  def main(args: Array[String]) {
    val a = Array(1, 2, 3, 4, 5)
    Swap(a)
    a.foreach(print)

    println()
    print(0.to(3).by(2))
  }

  def Swap(arr: Array[Int]): Unit = {
    for (i <- 0 until(arr.length - 1, 2)) {
      val tmp = arr(i)
      arr(i) = arr(i + 1)
      arr(i + 1) = tmp
    }
  }
}
