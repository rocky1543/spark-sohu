package sohu.com.test

import org.apache.spark.sql.Row
import org.apache.spark.{SparkContext, SparkConf}

object sohuStatistics {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local").setAppName("sohuStatistics")
    val sc=new SparkContext(conf)

    val logsRDD=sc.textFile("logs.txt").map(_.split("\t"))
      .map(line=>{
//        val etlMap =scala.collection.mutable.Map[String,String]()
//        val etlMapStr = line(24)
//        val kv=etlMapStr.replaceAll("[{|}]","").split(",")
//        for ( str  <- kv){
//          etlMap.put(str.trim.split(":")(0),str.trim.split(":")(1))
//        }

        //将字符串转成map
        val map20 = line(20).substring(1, line(20).length - 1).replaceAll("\"","")
          .split(",").map(_.split(":")).map {case Array(k, v) => (k.trim,v.trim)}.toMap

        //返回一个row对象
        Row(line(0).toInt,line(1),line(2),line(3),line(4),line(5),line(6),line(7),
        line(8),line(9),line(10),line(11),line(12),line(13),line(14),line(15),
        line(16),line(17),line(18),line(19),map20)
        //,line(21),line(22),line(23),etlMap,line(25))
      }).cache()

    val result = logsRDD.map(log =>(log,1)).reduceByKey(_+_).filter(result=>result._2>0)//.take(2)

    result.foreach(x => {
      val mb = x._1.getMap(20).getOrElse("mb","")
      val mbOs = x._1.getMap(20).getOrElse("mbOs","")
      val page_type = x._1.getMap(20).getOrElse("page_type","")

      println(mb +"-"+ mbOs +"-"+ page_type )
      println(("ip："+ x._1(1) +" 出现的次数 "+x._2))
    });
  }
}
