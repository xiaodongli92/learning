package com.study.flink

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.{ProcessingTimeSessionWindows, SlidingProcessingTimeWindows, TumblingProcessingTimeWindows}
import org.apache.flink.streaming.api.windowing.time.Time

object ProcessTimeWindow {

    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
        env.setParallelism(1)
        val text: DataStream[String] = env.socketTextStream("localhost", 9998, '\n')
        text
            .flatMap(_.split(" ", -1))
            .map((_, 1))
            .keyBy(0)
//            .timeWindow(Time.seconds(10), Time.seconds(5))
//            .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
//            .window(SlidingProcessingTimeWindows.of(Time.seconds(10), Time.seconds(5)))
            .window(ProcessingTimeSessionWindows.withGap(Time.seconds(10)))
            .reduce((m1, m2) => {(m1._1, m1._2 + m2._2)})
            .print()
        env.execute("TumblingEventTimeWindow")
    }

}
