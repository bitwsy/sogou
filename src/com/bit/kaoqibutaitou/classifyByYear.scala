package com.bit.kaoqibutaitou

import org.apache.spark.{SparkContext, SparkConf}

import scala.collection.immutable.HashMap
import scala.xml.XML

/**
  * Created by root on 16-1-11.
  */
object classifyByYear {

  def loadClazzifyKeywords(filePath:String): HashMap[String, Array[String]] = {
    var map:HashMap[String, Array[String]] = null
    try {
      val xmlFile = XML.loadFile(filePath)
      val typeNodes = xmlFile \ "type";
      map = new HashMap[String, Array[String]]
      for (typeNode <- typeNodes) {
        val typeName: String = (typeNode \ "name").text.toString
        val keywords = (typeNode \ "keywords").text.toString.split(";")
        map += (typeName -> keywords)
      }
    }catch {
      case e:Exception => println(e.getStackTrace); null
    }
    map
  }

  def containKeyword(keywords:HashMap[String, Array[String]],line:String): String ={
    for(wtype <- keywords) {
      val typeName = wtype._1
      for (s <- wtype._2)
        if (line.toLowerCase.contains(s.toLowerCase)){
//          println("match-key:"+s+"match-line:"+line)
          return typeName
        }
    }
    return null
  }

  /**
    * com.bit.kaoqibutaitou.classifyByYear <master> <keywordFilePath> <keywordFileName> <toMonth> <input> <outputPath>
    * @param args
    *            master: master
    *            keywordFilePath: 文件路径
    *            keywordFileName: 文件名
    *            toMonth: 精确到月
    *            input: 输入路径
    *            outputPath: 输出路径
    */
  def main(args: Array[String]) {

    if (args.length < 6) {
      println("classifyByYear <master> <keywordFilePath> <keywordFileName> <toMonth> <outputPath>\n\tmaster: master\n\tkeywordFilePath: 文件路径\n\tkeywordFileName: 文件名\n\ttoMonth: 精确到月\n\tinput: 输入路径\n\toutputPath: 输出路径")
      sys.exit()
    }
    val master = args(0)
    val keywordsFilePath = if(args(1).equalsIgnoreCase("default")) "" else args(1)
    val keywordsFileName = args(2)
    val toMonth = if(args(3).equalsIgnoreCase("default")) true else false
    val input = if(args(4).equalsIgnoreCase("default")) "/data/output/initn/" else args(4)
    val outputPath = if(args(5).equalsIgnoreCase("default")) "/data/output/final-"+keywordsFileName.substring(0,keywordsFileName.indexOf(".")) else args(5)

    val conf = new SparkConf().setAppName("ClassifyByYear").setMaster(master)
    val sc = new SparkContext(conf)

    val keywords = loadClazzifyKeywords(keywordsFilePath+keywordsFileName)
    if(null == keywords) {
      println("文件不存在")
      sys.exit()
    }

    val picInfo = sc.textFile(input)
    val picInfoToYearMap = picInfo.map((line:String)=>{
      val date = Util.getDate(line.substring(line.indexOf(Util.SEPARATOR_CHAR)))

//      val m = date.getOrElse("month",-1)
//      if( m > 12 ) println("month:"+m+"line:"+line)

      (date.getOrElse("year",-1),(date.getOrElse("month",-1),line))
    }).filter( x=> (2000<=x._1 && x._1<=2011 && 1 <= x._2._1 && x._2._1 <= 12))

    val midRes = picInfoToYearMap.map(line=>{
      val year = line._1
      val month = line._2._1

//      if(month<1 || month>12) null
//      else {
        val clazz = containKeyword(keywords, line._2._2)
        if (null != clazz) {
          if (toMonth) {
            (year + "+" + month + "+" + clazz, 1)
          } else {
            (year + "+" + clazz, 1)
          }
        } else null
//      }
    }).filter((_!=null)).reduceByKey(_+_).sortByKey().repartition(1)

    val res = for (r <- midRes) yield {
      val yearKeyword = r._1.split('+')
      if(toMonth) {
        new SClazz(yearKeyword(0).toInt, yearKeyword(1).toInt, yearKeyword(2), r._2)
      }else{
        new SClazz(yearKeyword(0).toInt, yearKeyword(1), r._2)
      }
    }
    res.saveAsTextFile(outputPath)
  }
}
