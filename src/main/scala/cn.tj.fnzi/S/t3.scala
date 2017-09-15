package cn.tj.fnzi.S

object t3 {
  def main(args: Array[String]) {
    val a = Array(1, 2, 3, 4, 5)
    val b = SwapYield(a)

    println(b)
    b.foreach(x => print(x + " "))
  }

  def SwapYield(arr: Array[Int]) = {
    for (i <- 0 until arr.length) yield {
      if (i < (arr.length - 1) && i % 2 == 0) {
        val tmp = arr(i)
        arr(i) = arr(i + 1)
        arr(i + 1) = tmp
      }
      arr(i)
    }
  }
}
