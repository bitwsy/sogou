package com.bit.kaoqibutaitou

import java.io._

import org.apache.spark.{SparkContext, SparkConf}

import scala.collection.immutable.HashMap
import scala.xml._

/**
  * Created by root on 16-1-10.
  */
object formateData {
  val None:String = "None"
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("SogouFormatData").setMaster("local")
    val sc = new SparkContext(conf)
    val metaDataFile = sc.textFile("hdfs://localhost:9000/input/sogou").filter(_.length>0)

    var metaDataMap = new HashMap[String,Array[String]]()
    var curTagOver = true;
    var curTagString:String = ""
    var curTagIndex:Int = -1 //0表示<PIC>,1表示</PIC>,2表示其他标签
    var picUrl:String = ""
    var alterText:String = ""
    var surText1:String = ""
    var surText2:String = ""
    var pageTitle:String = ""
    var contentTitle:String = ""

    case class PIC(AlterText:String,SurText1:String,SurText2:String,PageTitle:String,ContentTitle:String)

    //1.先解析出所有的Meta_Data标签里面的信息
//    metaDataFile

    val picUrlToInfoMaps = metaDataFile.map(line=>{
      curTagIndex = 2
      if(line.matches("^<PIC>$")) {
        picUrl = ""; alterText = ""; surText1 = ""; surText2 = ""; pageTitle = ""; contentTitle = ""
        curTagIndex = 0
      }else if(line.matches("^<\\/PIC>$")) {
        curTagIndex = 1
      }else{
        curTagIndex = 2
      }

      curTagIndex match {
        case 0 => (None,null)
        case 1 => (picUrl,PIC(alterText,surText1,surText2,pageTitle,contentTitle))
        case 2 => {
          if (line.matches("^<.+>.*<\\/.+>$")) curTagOver = true
          else if (line.matches(".*<\\/.+>$")) curTagOver = true
          else curTagOver = false

          curTagString += line;
          if(curTagOver) {
            var startIndex = curTagString.indexOf('>')
            var endIndex = curTagString.lastIndexOf('<')
            var text = curTagString.substring(startIndex+1,endIndex)
            var tagName = curTagString.substring(1,startIndex)
            tagName match {
              case "PIC_URL" => picUrl = text;                      //println(picUrl)
              case "ALT_TEXT" => alterText = text;                  //println(alterText)
              case "SUR_TEXT1" => surText1 = text;                  //println(surText1)
              case "SUR_TEXT2" => surText2 = text;                  //println(surText2)
              case "PAGE_TITLE" => pageTitle = text;                //println(pageTitle)
              case "CONTENT_TITLE" => contentTitle = text;          //println(contentTitle)
              case _ => ;
            }
            curTagString = ""
          }
          (None,null)
        }
      }
    }).filter(x=> (!x._1.equals(None)) || x._2!=null)

    picUrlToInfoMaps.saveAsTextFile("/output/init/")

    /*.reduceByKey((x,y)=>{
      if(null==x&&null==y) null
      else if(null==x) y
      else if(null==y) x
      else if(x.size>y.size) x
      else y
    })*/

//    FileOutputStream originData = new FileOutputStream()

//    val originFile = new File("sogouData.txt");
//    var pw:PrintWriter = null
//    val picUrlList = picUrlToInfoMaps.collect()

//    var fos:FileOutputStream = null
//    try {
//      pw = new PrintWriter(new File("/data/sogouData.txt"))
//      fos = new FileOutputStream(new File("/data/sogouData.txt"))
//      for (i <- 1 to 100) pw.println("hello,world!")
//      picUrlToInfoMaps.foreach(x=>{
//        println(x._1)
//        for (s<-x._2) println("\t"+s)
//        saveOne(pw,x._1,x._2)
//        pw.print(x._1)
//        for (s <- x._2) pw.print("\t" + s)
//        pw.println()


//        fos.write(x._1.toByte)
//        for(s <- x._2)
//          fos.write(("\t"+s).toByte)
//        fos.write("\n".toByte)
//      })

//    }catch {
//      case e:FileNotFoundException=>println("can't open the file!")
//    }finally {
////      if(null!=pw) pw.close()
//      if(null!=fos) fos.close()
//    }

//    def saveOne(pw:PrintWriter,picUrl:String,properties:Array[String])={
////      if(!picUrl.equals(None)) {
//        pw.print(picUrl)
//        for (s <- properties) pw.print("\t" + s)
//        pw.println()
//      }
//    }

//    picUrlToInfoMaps.foreach((ipicUrl,properties)={
//      for (p<-proper)
//    })

    //2.将数据按年来分组
//    val yearToInfo = picUrlToInfoMaps.map((curPicUrl,info)=>{
//      var curSurText1 = info(1)
//      var curSurText2 = info(2)
//
//      (None,null)
//    })


//    for (curPic<-picUrlToInfoMaps){//} yield {
//      val info = curPic._2
//      val curSurText1 = info(1)
//      val curSurText2 = info(2)
//      val year = Util.AnalizeYear(curSurText1+curSurText2)
//      println("Year:"+year+"--Res:"+curSurText1+curSurText2)
//      if(year > 0) (year,curPic)
//      else (-1,null)
//    }
//    for (info<-r){
//      println("Year:"+info._1)
//      if(null!=info._2) info._2.getClass
//    }

//    println(r.count())
//    metaDataMaps.saveAsTextFile("hdfs://localhost:9000/output/init")

    /*
    ** 可用代码，备份
    metaDataFile.take(104).foreach(line=>{
      curTagIndex = 2
      if(line.matches("^<PIC>$")) {
        picUrl = ""; alterText = ""; surText1 = ""; surText2 = ""; pageTitle = ""; contentTitle = ""
        curTagIndex = 0
      }else if(line.matches("^<\\/PIC>$")) {
        metaDataMap+=(picUrl->Array[String](alterText,surText1,surText2,pageTitle,contentTitle))
        println("url:"+picUrl+"--alter:"+alterText+"--surText1:"+surText1+"--surText2"+surText2+"--pageTitle:"+pageTitle+"--contentTitle:"+contentTitle)
        curTagIndex = 1
      }else{
        curTagIndex = 2
      }

      curTagIndex match {
        case 0 => (None,null)
        case 1 => (picUrl,Array[String](alterText,surText1,surText2,pageTitle,contentTitle))
        case 2 => {
          if (line.matches("^<.+>.*<\\/.+>$")) curTagOver = true
          else if (line.matches(".*<\\/.+>$")) curTagOver = true
          else curTagOver = false

          curTagString += line;
          if(curTagOver) {

            val ele = XML.loadString(curTagString)
            ele.label match {
              case "PIC_URL" => picUrl = ele.text;                      //println(picUrl)
              case "ALT_TEXT" => alterText = ele.text;                  //println(alterText)
              case "SUR_TEXT1" => surText1 = ele.text;                  //println(surText1)
              case "SUR_TEXT2" => surText2 = ele.text;                  //println(surText2)
              case "PAGE_TITLE" => pageTitle = ele.text;                //println(pageTitle)
              case "CONTENT_TITLE" => contentTitle = ele.text;          //println(contentTitle)
              case _ => ;
            }
            curTagString = ""
          }
          (None,null)
        }
      }
    })
    */
//    metaDataMap.saveAsTextFile("hdfs://localhost:9000/ouput/metaData.map/")
//    println(metaDataMap.size)
//    for (pic<-metaDataMap){
//      println(pic._1)
//      for (t<-pic._2)
//        println("**\t"+t)
//    }

    //val mapResults = sc.parallelize(metaDataMap);
    sc.stop()
  }
}
