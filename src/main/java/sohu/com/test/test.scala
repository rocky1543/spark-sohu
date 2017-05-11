package sohu.com.test

/**
  * Created by root on 2017/5/10.
  */
object test {

  def main(args: Array[String]) {
    val myString = "{\"a\":\"ab\",\"b\":\"cd\",\"c\":\"cd\",\"d\":\"de\",\"e\":\"ef\",\"f\":\"fg\"}"
    val map = myString.substring(1, myString.length - 1)
      .split(",")
      .map(_.split(":"))
      .map { case Array(k, v) => (k.substring(1, k.length-1), v.substring(1, v.length-1))}
      .toMap
    println(map("e"))
  }

}
