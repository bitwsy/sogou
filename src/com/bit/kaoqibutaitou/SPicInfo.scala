package com.bit.kaoqibutaitou

/**
  * Created by root on 16-1-12.
  */
class SPicInfo(picUrl:String,alterText:String,surText1:String,surText2:String,pageTitle:String,contentTitle:String){
  //ASCII值小于0x20和大于0x7f的都是不可见字符。所以分隔符可以被定义在此范围内。
//  val SEPARATOR:Char = 0x07 //#7=> BEL
  var m_picUrl:String = picUrl
  var m_alterText:String = alterText
  var m_surText1:String = surText1
  var m_surText2:String = surText2
  var m_pageTitle:String = pageTitle
  var m_contentTitle:String = contentTitle

  override def toString():String={
//    m_picUrl      +Util.SEPARATOR_CHAR+
    m_alterText   +Util.SEPARATOR_CHAR+
    m_surText1    +Util.SEPARATOR_CHAR+
    m_surText2    +Util.SEPARATOR_CHAR+
    m_pageTitle   +Util.SEPARATOR_CHAR+
    m_contentTitle
  }

}
