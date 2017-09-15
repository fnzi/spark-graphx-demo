package cn.tj.fnzi.S

object t6 {
  def main (args: Array[String])= {
    val a=TimeZone()
    a.foreach(println)
  }
  def TimeZone()={
    val arr=java.util.TimeZone.getAvailableIDs()
    val tmp=for(ele <-arr if ele.startsWith("America/")) yield {
      ele.drop("America/".length)
    }
    scala.util.Sorting.quickSort(tmp)
    tmp
  }
}
