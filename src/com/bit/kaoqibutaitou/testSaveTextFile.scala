package com.bit.kaoqibutaitou

import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by root on 16-1-10.
  */
object testSaveTextFile {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("TestSaveFile")
    val sc = new SparkContext(conf)
    val list = Array(Array("hello","world"),Array("Nice","to"),Array("meet","you"))
//    val list = Array("hello","world","Nice","to","meet","you")

    val rdd = sc.parallelize(list)
    rdd.saveAsTextFile("hdfs://localhost:9000/output/testSaveTextFile1")
//    rdd.
  }
}
