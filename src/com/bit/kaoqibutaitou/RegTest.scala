package com.bit.kaoqibutaitou

import java.util.regex.{Matcher, Pattern}

import scala.util.matching.Regex
import scala.util.matching.Regex.Match


/**
  * Created by root on 16-1-13.
  */
object RegTest {
  def main(args: Array[String]) {
    //number: ０１２３４５６７８９
//    val date = "今天２００５年９月２０日天气晴 2010/10/20"
//    val reg = "[０-９|0-9]{4}(\\s*)?[年|／|/|－|-](/s)?[０-９|0-9]{1,2}(\\s*)?[月|／|/|－|-](\\s*)?[０-９|0-9]{1,2}(\\s*)?(日)?".r
//    val date = "２００５ 年  ９ 月 ２０ 日天气晴 2005     年10月20日天气晴"
//    val date = "2010/ 2/2 ２００５ －  １１ 月 ２０ 日天气晴 2005     年10月20日天气晴"

    val date = "日期：２０１１－０７－２０　作者：　来源：新华网\u0005刘嘉玲范冰冰　女星成名前后玉照＿新华家电＿新华网　＜！－－　．ｈｅｉ１４　｛ｆｏｎｔ－ｓｉｚｅ：　１４ｐｘ；　ｃｏｌｏｒ：　＃００００００；　ｔｅｘｔ－ｄｅｃｏｒａｔｉｏｎ：　ｎｏｎｅ｝　．ｈｏｎｇ１６＿ｘ　｛ｆｏｎｔ\u0005刘嘉玲范冰冰　女星成名前后玉照"

//    val date = "今天 2006 / 10  / 20 天气晴"
//    val reg = "\\d{4}年\\d{1,2}月\\d{1,2}日.*"
//    val regYear = "\\d{4}年.*"
//
//    println(date)
//    show(reg.findAllIn(date))
//    val matcher = reg.findAllIn(date)
//    while (matcher.hasNext) {
//      println(matcher.next())
//    }

    println(Util.getDate(date))

    def show(matcher:Regex.MatchIterator): Unit ={
      while (matcher.hasNext) {
        println(matcher.next())
      }
    }
//
//    val str = "     dd"
//    val strReg = "\\sdd".r
//    show(strReg.findAllIn(str))

//    println()

//    println(Util.getTime(date))
//    var reg = "/.*ss(\\d+)rr.*/";
//    var str = "ss12345rr3432re232";
//    println(str.replace(reg, "$1"))


//    val p = Pattern.compile(reg)
//    val m = p.matcher(date)
//    while (m.find()) {
//      println(m.groupCount())
//      for(i <- 0 to m.groupCount())
//        println(m.group(i))
//    }

//    val regEx = "<a>([\\s\\S]*?)</a>";
//    val s = "<a>123</a><a>456</a><a>789</a>";
//    val pat = Pattern.compile(regEx);
//    val mat = pat.matcher(s);
//    while (mat.find()) {
////      println(mat.groupCount())
//      for(i <- 0 to mat.groupCount())
//        println(mat.group(i))
//    }

//    val str = "我是中国人"
//    println(str.toLowerCase)

  }
}
