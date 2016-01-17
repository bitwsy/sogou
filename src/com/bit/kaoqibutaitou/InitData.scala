package com.bit.kaoqibutaitou

import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by root on 16-1-11.
  */
object InitData {
  val None:String = "None"
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("SogouFormatData").setMaster("local")
    val sc = new SparkContext(conf)
    val metaDataFile = sc.textFile("hdfs://localhost:9000/input/sogou").filter(_.length > 0)
//    val metaDataFile = sc.textFile("file:///mnt/share/AdvancedSoftEngineer/data/Meta_Data.txt").filter(_.length > 0)
    case class pic(alterText: String, surText1: String, surText2: String, pageTitle: String, contentTitle: String)
    var curTagOver = true;
    var curTagString: String = ""
    var curTagIndex: Int = -1 //0表示<PIC>,1表示</PIC>,2表示其他标签

    var picUrl: String = ""
    var alterText: String = ""
    var surText1: String = ""
    var surText2: String = ""
    var pageTitle: String = ""
    var contentTitle: String = ""

    //1.先解析出所有的Meta_Data标签里面的信息

    val picUrlToInfoMaps = metaDataFile.map(line => {
      curTagIndex = 2
      if (line.matches("^<PIC>$")) {
        picUrl = "";
        alterText = "";
        surText1 = "";
        surText2 = "";
        pageTitle = "";
        contentTitle = ""
        curTagIndex = 0
      } else if (line.matches("^<\\/PIC>$")) {
        curTagIndex = 1
      } else {
        curTagIndex = 2
      }

      curTagIndex match {
        case 0 => (None, null)
        case 1 => (picUrl, pic(alterText, surText1, surText2, pageTitle, contentTitle))
        case 2 => {
          if (line.matches("^<.+>.*<\\/.+>$")) curTagOver = true
          else if (line.matches(".*<\\/.+>$")) curTagOver = true
          else curTagOver = false

          curTagString += line;
          if (curTagOver) {
            val startIndex = curTagString.indexOf('>')
            val endIndex = curTagString.lastIndexOf('<')
            val text = curTagString.substring(startIndex + 1, endIndex)
            val tagName = curTagString.substring(1, startIndex)
            tagName match {
              case "PIC_URL" => picUrl = text; //println(picUrl)
              case "ALT_TEXT" => alterText = text; //println(alterText)
              case "SUR_TEXT1" => surText1 = text; //println(surText1)
              case "SUR_TEXT2" => surText2 = text; //println(surText2)
              case "PAGE_TITLE" => pageTitle = text; //println(pageTitle)
              case "CONTENT_TITLE" => contentTitle = text; //println(contentTitle)
              case _ => ;
            }
            curTagString = ""
          }
          (None, null)
        }
      }
    }).filter(x => (!x._1.equals(None) || (null != x._2)))

    picUrlToInfoMaps.saveAsTextFile("/output/init")
  }
}
