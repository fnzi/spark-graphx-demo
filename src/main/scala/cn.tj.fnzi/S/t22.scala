package cn.tj.fnzi.S

object t22 {

}

class Person(var age: Int) {
  if (age < 0) age = 0

  var name: String = "qwer"

  def getAge = age
}

object Person {
  def main(args: Array[String]): Unit = {
    val age1 = 10
    val age2 = -20

    println("将Tom的年龄初始化为:" + age1)
    val Tom = new Person(age1)
    println("Tom的实际年龄为:" + Tom.age)

    println("将Tom的年龄初始化为:" + age2)
    val Jhon = new Person(age2)
    println("Jhon的实际年龄为:" + Jhon.age)
  }
}