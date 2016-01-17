package com.bit.kaoqibutaitou

/**
  * Created by root on 16-1-10.
  */
object testDate {
  def main(args: Array[String]) {
//    val date = " ２００５  年 ６ 月 ２ ５ 日"
//    val date = " ２００５  - ６ - ２ ５  19:35"
    //    val date = "2005年6月25日"
//    val year = date.substring(0,date.indexOf("年")).toInt
//    println("year:"+year)
//    println("**"+date(1).getClass+"**")
//    if (date.matches("^.*[0-9]{4,8}年[0-9]{1,4}月[0-9]{1,4}日.*$")||date.matches("^.*.{4}年.{1,2}月.{1,2}日.*$"))
//      println("success")
//    else
//      println("failed")

    val date = "２０１０－１１－１３　１８：１０传统男人，不堪束缚"
    println("**"+Util.AnalizeYear(date))
  }
}
