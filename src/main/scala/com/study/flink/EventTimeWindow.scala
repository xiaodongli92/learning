package com.study.flink

import org.apache.commons.lang.StringUtils
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

object EventTimeWindow {

    def main(args: Array[String]): Unit = {
        val split = " "

        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
        env.setParallelism(1)
        val text: DataStream[String] = env.socketTextStream("localhost", 9998, '\n')
        text.filter(line => StringUtils.isNotEmpty(line) && line.split(split, -1).length > 1)
            .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[String](Time.seconds(5)) {
            override def extractTimestamp(t: String): Long = {
                try {
                    val mills = t.split(split, -1)(0).toLong
                    mills
                } catch {
                    case _: Exception => 0
                }
            }
        }).map(line => {(getFloorTime(line.split(split, -1)(0).toLong, 6) + line.split(split, -1)(1), 1)})
            .keyBy(_._1)
            .window(TumblingEventTimeWindows.of(Time.seconds(5)))
            .sum(1)
            .setParallelism(1)
            .print()

        env.execute("EventTimeWindow")
    }

    private def getFloorTime(mills: Long, interval: Int): Long = {
        mills / (interval * 1000) * interval * 1000
    }

}
