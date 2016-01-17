package com.bit.kaoqibutaitou

import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by root on 16-1-12.
  */
object InitDataN {

  /**
    * com.bit.kaoqibutaitou.InitDataN <master> <source> <output>
    * @param args
    *             master: master
    *             source: 原文件
    *             output: 输出路径
    */

  def main(args: Array[String]) {

    if(args.length<3){
      println("com.bit.kaoqibutaitou.InitDataN <master> <source> <output>\n\tmaster: master\n\tsource: 原文件\n\toutput: 输出路径")
      sys.exit()
    }

    val master = args(0)
    val source = if(args(1).equalsIgnoreCase("default")) "hdfs://localhost:9000/input/sogou" else args(1)
    val output = if(args(2).equalsIgnoreCase("default")) "/data/output/initn" else args(2)

    val conf = new SparkConf().setAppName("SogouFormatData").setMaster(master)
    val sc = new SparkContext(conf)
    val metaDataFile = sc.textFile(source).filter(_.length > 0)
    var curTagOver = true;
    var curTagString: String = ""
    var curTagIndex: Int = -1 //0表示<PIC>,1表示</PIC>,2表示其他标签

    var picUrl: String = ""
    var alterText: String = ""
    var surText1: String = ""
    var surText2: String = ""
    var pageTitle: String = ""
    var contentTitle: String = ""
    var containYear = false

    //1.先解析出所有的Meta_Data标签里面的信息
    val picUrlToInfoMaps = metaDataFile.map((line:String)=>{
      curTagIndex = 2
      if (line.matches("^<PIC>$")) {
        picUrl = "";
        alterText = "";
        surText1 = "";
        surText2 = "";
        pageTitle = "";
        contentTitle = ""
        curTagIndex = 0

        containYear = false
      } else if (line.matches("^<\\/PIC>$")) {
        curTagIndex = 1
      } else {
        curTagIndex = 2
      }

      curTagIndex match {
        case 0 => null
        case 1 => {
          if(containYear) new SPicInfo(picUrl,alterText, surText1, surText2, pageTitle, contentTitle)
          else null
        }
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

            containYear |= Util.containYear(text)

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
          null
        }
      }
    }).filter((_!=null))
    picUrlToInfoMaps.saveAsTextFile(output)
  }
}
