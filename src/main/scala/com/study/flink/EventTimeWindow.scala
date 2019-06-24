package com.study.flink

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

object EventTimeWindow {

    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
        env.setParallelism(1)
        val text: DataStream[String] = env.socketTextStream("localhost", 9998, '\n')
        text.assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[String](Time.seconds(15)) {
            override def extractTimestamp(t: String): Long = {
                t.split("\t", -1)(0).toLong
            }
        } ).keyBy(_.split("\t")(1))
            .window(TumblingEventTimeWindows.of(Time.seconds(5)))

    }

}
