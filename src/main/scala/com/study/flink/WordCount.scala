package com.study.flink

import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.scala._

/**
  * Created by xiaodong36 on 2018/2/11.
  * word count example
  * nc -lk 9998
  */
object WordCount {

    case class WordWithCount(word: String, count: Long)

    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment

        val text: DataStream[String] = env.socketTextStream("localhost", 9998, '\n')
        val windowCounts = text.flatMap{s => s.split("\\s")}.map(WordWithCount(_, 1L))
            .keyBy("word").timeWindow(Time.seconds(5)).sum("count")
        windowCounts.print().setParallelism(1)
        env.execute("Socket window WordCount")
    }
}
