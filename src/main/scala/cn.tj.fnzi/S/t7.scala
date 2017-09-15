package cn.tj.fnzi.S

import java.awt.datatransfer.{DataFlavor, SystemFlavorMap}

object t7 {
  def main (args: Array[String])= {
//    val flavors = SystemFlavorMap.getDefaultFlavorMap().asInstanceOf[SystemFlavorMap]
//    println(flavors.getNativesForFlavor(DataFlavor.imageFlavor).toArray.toBuffer.mkString(" | "))


    val l = List(1,2,3,4,5,6,7,8,9)

    println(l.toString())
    println(l.mkString)
    println(l.mkString("|"))
    println(l.mkString("List(", " , ", ")"))
  }
}
