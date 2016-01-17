package com.bit.kaoqibutaitou

/**
  * Created by root on 16-1-9.
  */
object main {
  def main(args: Array[String]) {
    val string = "a;b;c"
    val ss = string.split(";")
    for (s<-ss){
      print(s)
    }
  }
}
