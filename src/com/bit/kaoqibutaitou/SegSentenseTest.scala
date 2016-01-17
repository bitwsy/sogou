package com.bit.kaoqibutaitou

import java.io.File

import org.apdplat.word.WordSegmenter

/**
  * Created by root on 16-1-13.
  */
object SegSentenseTest {
  def main(args: Array[String]) {
    val line = "http://www.comicyu.com/UploadFiles/ZX/2010/3/2010329152323.jpg\u0005\u0005\u0005\u0005苍焰参上！ｆｉｇｍａ　ＢＲＳ绝美手办官图放出＿国际＿漫域＿中国动漫综合门户\u0005苍焰参上！ｆｉｇｍａ　ＢＲＳ绝美手办官图放出\nhttp://upload.kaoyansky.cn/atta5/day_100521/1005211921385bcdb4f26b9694.jpg\u0005\u0005　２４楼位粉丝　　\u0005２０１０－１２－２５　１４：３７　　\u0005【°猫武士＿送图】圣诞快乐＿猫武士吧＿贴吧\u0005【°猫武士＿送图】圣诞快乐\nhttp://yc.wiiyi.com.cn/bbs/attachments/ext_jpg/20080827_3b20c75333f9bc98fa445rKwwMZu72vA.jpg\u0005８．ｊｐｇ\u0005\u0005\u0005新撰组异闻录　可爱的总司大人＾＿＾＜１１Ｐ＞　－　ＡＣＧ图库　－　唯易动漫　动漫中国，雪铃动漫，西安动漫社团联盟，在线漫画，动漫资源，社区论坛　－　Ｐｏｗｅｒｅｄ　ｂｙ　Ｄｉｓｃｕｚ\u0005新撰组异闻录　可爱的总司大人＾＿＾＜１１Ｐ＞"
//    val words = WordSegmenter.seg(new File("/data/output/initn/part-00001"),new File("/data/output/segRes1.txt"))


    val words = WordSegmenter.seg(line)
    val wordString = words.toString
    println(wordString.drop(1).dropRight(1))
//    println(wordString.substring(1,-1))
//    val ws = wordString.replaceFirst("^\\[.*\\]$","$1").split(',')
//    ws.foreach(println(_))
//    WordSegmenter.
//    for(i <- 0 until words.size()){
//      val word = words.get(i)
//      println(word.getText)
//    }

    //正则表达式很有用
    val str = "sss123456rr3432re232"
    println(str.replaceAll(".*rr([0-9]+)re.*","$1"))
  }
}
