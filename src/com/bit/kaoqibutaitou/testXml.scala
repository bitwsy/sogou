package com.bit.kaoqibutaitou

import scala.collection.immutable.HashMap
import scala.xml._

/**
  * Created by root on 16-1-9.
  */
object testXml {
  def main(args: Array[String]) {
//    val xmlFile = XML.load("weather.xml")
//    var weatherMap = (Map[String,Array[String]] /: (xmlFile \ "type")){
//      (map,typeNode)=>{
//        val typeName:String = (typeNode \ "name").text.toString
//        val keywords = (typeNode \ "keywords").text.toString.split(";")
//
////        map += (typeName -> keywords)
//        map += (typeName -> keywords)
//      }
//    }
//    var map = new HashMap[String,String]()
//    map += ("1"->"a")
//    map += ("2"->"b")
//    map.+("1","a")

//    map.foreach(println)


    def loadWeatherKeywords(filePath:String): HashMap[String, Array[String]] = {
      try {
        val xmlFile = XML.loadFile(filePath)
        val typeNodes = xmlFile \ "type";
        var map = new HashMap[String, Array[String]]
        for (typeNode <- typeNodes) {
          val typeName: String = (typeNode \ "name").text.toString
          val keywords = (typeNode \ "keywords").text.toString.split(";")
          println(keywords.getClass)
          map += (typeName -> keywords)
        }
        map
      }catch {
        case e:Exception => println(e.getStackTrace); null
      }
    }

    val weatherMap = loadWeatherKeywords("weather.xml")
    if (null!=weatherMap) {
      println(weatherMap.getClass)
      for (w <- weatherMap) {
        println("**" + w._1 + "**")
        w._2.foreach(x => {
          println("\t**" + x + "**")
        })
      }
    }
  }
}
