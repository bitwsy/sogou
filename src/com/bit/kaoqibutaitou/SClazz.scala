package com.bit.kaoqibutaitou

import scala.collection.mutable.ArrayBuffer

/**
  * Created by root on 16-1-9.
  */
class SClazz(year:Int, month:Int = -1, clazzName:String, cnt:Int) extends Serializable with Comparable[SClazz]{
  var m_year:Int = year
  var m_month:Int = month
  var m_clazzName:String = clazzName
  var m_cnt:Int = cnt

  def this()={
    this(-1,-1,"",0)
  }

  def this(year:Int, month:Int, clazzName:String)={
    this(year, month, clazzName, 1)
  }

  def this(year:Int, clazzName:String)={
    this(year, -1, clazzName, 1)
  }

  def this(year:Int, clazzName:String, cnt:Int)={
    this(year, -1, clazzName, cnt)
  }

  override def toString={
    var str = ""

    str += m_year
    if(m_month > 0) str += (Util.SEPARATOR_STRING + m_month)
    str += (Util.SEPARATOR_STRING + m_clazzName)
    str += (Util.SEPARATOR_STRING + m_cnt)

    str
  }

  override def compareTo(sClazz: SClazz):Int={
    if(this == sClazz) 1
    else 0
  }

  def == (clazz: SClazz):Boolean={
    var res = false
    if(this.m_year==clazz.m_year && this.m_clazzName.equals(clazz.m_clazzName))
      res = true
    res
  }
}
