package cn.tj.fnzi.S

import scala.collection.mutable.ArrayBuffer

object t4 {
  def main (args: Array[String])= {
    val a=Array(5,4,3,2,1)
    val ar = a.reverse
//    resverseArr(a)
    ar.foreach(println)

    //ArrayBuffer反转
    val b=ArrayBuffer(1,2,3,4,5)
    val c=ArrayBuffer[Int]()
    c++=b.reverse
    c.foreach(println)
  }

  def resverseArr(arr:Array[Int]) {
    val len=arr.length
    for(i <- 0 until len/2){
      val tmp=arr(i)
      arr(i)=arr(len-1-i)
      arr(len-1-i)=tmp
    }
  }
}
