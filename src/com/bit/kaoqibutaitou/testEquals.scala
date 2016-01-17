package com.bit.kaoqibutaitou

/**
  * Created by root on 16-1-12.
  */
object testEquals {
  def main(args: Array[String]) {
    val s1 = new SClazz(2010,"good",1)
    val s2 = new SClazz(2010,"good",0)
    val s3 = new SClazz(2010,"bad",1)


    if(s1==s2)
      println("s1==s2")
    else println("s1!=s2")

    if(s1==s3)
      println("s1==s3")
    else println("s1!=s3")

    println(s1+"=="+s2+"="+s1==s2)
    println(s1+"=="+s3+"="+s1==s3)
  }
}
