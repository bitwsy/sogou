package com.bit.kaoqibutaitou

/**
  * Created by root on 16-1-12.
  */
object testChar {
  def main(args: Array[String]) {
    var c:Char = 0x00
    for (c <- 0x00 to 0xff)
      println("#%d=>%c".format(c.toInt,c.toChar))
//    for (c <- 0x7f to 0xff)
//      println("#%d=>%c".format(c.toInt,c.toChar))
  }
}
