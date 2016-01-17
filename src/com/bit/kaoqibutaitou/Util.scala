package com.bit.kaoqibutaitou

import scala.collection.immutable.HashMap
import scala.swing.Separator

/**
  * Created by root on 16-1-10.
  */
object Util {
  val SEPARATOR_CHAR:Char = 0x05
  val SEPARATOR_STRING:String = ";"

  def getYear(line:String,separator: String)={
    var year = -1
    val date = line.split('－')
    if(date.size>=2) {
      val yearString = date(0).trim.takeRight(4)
      if(isInt(yearString)) {
        try {
          year = yearString.toInt
        } catch {
          case e: Exception => e.printStackTrace(); year = -1
        }
      }
    }
    year
  }

//  def AnalizeYear(line:String) = {
//    var year = -1
//    //先按照减号进行分割
//    if(year<0) year = getYear(line,"－")
//    if(year<0) year = getYear(line,"-")
//    //再按照年关键字来进行分割
//    if(year<0) year = getYear(line,"年")
//    year
//  }

  def AnalizeYear(line:String):Int = {
    if(line.contains("２０００")){
      return 2000
    }else if(line.contains("２００１")){
      return 2001
    }else if(line.contains("２００２")){
      return 2002
    }else if(line.contains("２００３")){
      return 2003
    }else if(line.contains("２００４")){
      return 2004
    }else if(line.contains("２００５")){
      return 2005
    }else if(line.contains("２００６")){
      return 2006
    }else if(line.contains("２００７")){
      return 2007
    }else if(line.contains("２００８")){
      return 2008
    }else if(line.contains("２００９")){
      return 2009
    }else if(line.contains("２０１０")){
      return 2010
    }else if(line.contains("２０１１")){
      return 2011
    }else{
      return -1
    }
  }

  def containYear(line:String):Boolean={
    if(line.contains("２００") || line.contains("２０１") || line.contains("200") || line.contains("201")) true else false
  }

  def NAnalizeYear(line:String):Int={
    var year = -1

    var reg = ".*年"

    year
  }

  def isDigital(c:Char)={
    ('0'<=c && c<='9') || ('０'<=c && c<='９') || (0 <= c.toInt&&c.toInt <= 9)
  }

  def getTime(line:String): Map[String,Int] = {
    val pattern = "(２００[０-９]|２０１[０-１])[年－]((０)?([０-９]|(１０)|(１１)|(１２)))[月－]".r
    val matchString = pattern.findFirstIn(line)
    var year:Int = -1
    var month:Int = -1
    if (matchString.isDefined) {
      val timeString = matchString.get
      println(timeString)
      if (timeString.contains('年')) {
        year = timeString.substring(0, 4).toInt
        month = timeString.substring(5, timeString.indexOf('月')).toInt
      } else if (timeString.contains('－')) {
        val arr = timeString.split('－')
        year = arr(0).toInt
        month = arr(1).toInt
      }
      //println(month)
    }
    val timeMap: Map[String, Int] = Map("year"->year,"month"->month)
    //println(timeMap)
    timeMap
  }

  def getDate(line:String)={
//    println(line)
    val date = scala.collection.mutable.Map[String,Int]("year"->(-1),"month"->(-1),"day"->(-1))
    val reg = "[０-９|0-9]{4}(\\s*)?[年|／|/|－|-](\\s*)?[０-９|0-9]{1,2}(\\s*)?[月|／|/|－|-](\\s*)?[０-９|0-9]{1,2}(\\s*)?(日)?".r
    val options = reg.findFirstIn(line)
    if(options.isDefined){
      val dateString = options.get
      if(!dateString.isEmpty) {
        var yearCharIndex = dateString.indexOf("年")
        if (yearCharIndex < 0) yearCharIndex = dateString.indexOf("／")
        if (yearCharIndex < 0) yearCharIndex = dateString.indexOf("－")
        if (yearCharIndex < 0) yearCharIndex = dateString.indexOf("/")
        if (yearCharIndex < 0) yearCharIndex = dateString.indexOf("-")
        if (yearCharIndex > 0) {
//          print("####"+dateString+":"+yearCharIndex+":"+dateString.substring(0, yearCharIndex).trim)
          var yearString = dateString.substring(0, yearCharIndex).trim
          if(yearString.length>4) {
            yearCharIndex = 4
            yearString = yearString.substring(0, yearCharIndex)
          }
          date("year") = yearString.toInt
        }
        var monthCharIndex = -1
        var monthString:String = ""
        if(yearCharIndex > 0) {
          monthString = dateString.substring(yearCharIndex+1)
          if (monthCharIndex < 0) monthCharIndex = monthString.indexOf("月")
          if (monthCharIndex < 0) monthCharIndex = monthString.indexOf("／")
          if (monthCharIndex < 0) monthCharIndex = monthString.indexOf("－")
          if (monthCharIndex < 0) monthCharIndex = monthString.indexOf("/")
          if (monthCharIndex < 0) monthCharIndex = monthString.indexOf("-")
          if (yearCharIndex > 0)
            date("month") = monthString.substring(0, monthCharIndex).trim.toInt
        }

        var dayCharIndex = -1
        if(monthCharIndex > 0) {
          if (dayCharIndex < 0) dayCharIndex = monthString.indexOf("日")
          if (dayCharIndex < 0) dayCharIndex = monthString.length

          if (dayCharIndex > 0) date("day") = monthString.substring(monthCharIndex+1, dayCharIndex).trim.toInt
          else date("day") = monthString.substring(monthCharIndex + 1).trim.toInt
        }
      }
    }
    date
  }

  def getnDate(line:String)= {
    println(line)
    val date = scala.collection.mutable.Map[String, Int]("year" -> (-1), "month" -> (-1), "day" -> (-1))
    val reg = "[０-９|0-9]{4}(\\s*)?[年|／|/|－|-](\\s*)?[０-９|0-9]{1,2}(\\s*)?[月|／|/|－|-](\\s*)?[０-９|0-9]{1,2}(\\s*)?(日)?".r
    val options = reg.findFirstIn(line)
    if (options.isDefined) {
      val dateString = options.get;
//      if()
      val dataArr = dateString.split('年')
    }
  }

  def isInt(number:String)={
    var res = true
    if(number.length > 0) {
      for (c <- number)
        if (!isDigital(c)) res = false
    }else{
      res = false
    }
    res
  }
}
