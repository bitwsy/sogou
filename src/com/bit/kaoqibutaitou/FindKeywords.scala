package com.bit.kaoqibutaitou

import org.apache.spark.{SparkContext, SparkConf}
import org.apdplat.word.WordSegmenter

/**
  * Created by root on 16-1-13.
  */
object FindKeywords {
  def isDigital(word:String):Boolean={
    for (c<-word)
      if(('０'<=c && c<='９') || ('0' <= c && c <= '9')) return true
    return false
  }

  /**
    * com.bit.kaoqibutaitou.FindKeywords <master> <input> <output>
    * @param args
    *             master: master
    *             source: 原文件
    *             output: 输出路径
    */
  def main(args: Array[String]) {
    if (args.length < 3) {
      println("classifyByYear <master> <input> <outputPath>\n\tmaster: master\n\tinput: 文件路径\n\toutputPath: 输出路径")
      sys.exit()
    }

    val master = args(0)
    val input = if(args(1).equalsIgnoreCase("default")) "/data/output/initn/" else args(1)
    val output = if(args(2).equalsIgnoreCase("default")) "/data/output/keywords" else args(2)

    val conf = new SparkConf().setAppName("FindKeywords").setMaster(master)
    val sc = new SparkContext(conf)
    val metaFile = sc.textFile(input)

    val keywordsMap = metaFile.flatMap(line=>{
      if (null!=line){
        try {
          val words = WordSegmenter.seg(line.substring(line.indexOf(Util.SEPARATOR_CHAR)+1))
          words.toString.drop(1).dropRight(1).split(',')
        }catch {
          case e:NullPointerException => Array("None");
        }finally {
          Array("None")
        }
      }else{
        Array("None")
      }
    }).filter(word=>{
      (!word.contains('／') && (!isDigital(word)) && !word.contains('－') && !word.contains('/') && !word.contains(Util.SEPARATOR_CHAR))
    }).map(x=>(x,1)).reduceByKey(_+_).filter(_._2 > 1).map(x=>(x._2,x._1)).sortByKey(false).map(x=>(x._2,x._1)).repartition(1)

    keywordsMap.saveAsTextFile(output)
  }
}
