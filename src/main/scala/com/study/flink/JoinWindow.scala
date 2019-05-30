package com.study.flink

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time

object JoinWindow {

    def main(args: Array[String]): Unit = {
        val env = StreamExecutionEnvironment.getExecutionEnvironment
        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
        env.setParallelism(1)
        val text1: DataStream[String] = env.socketTextStream("localhost", 9998, '\n')
        val text2: DataStream[String] = env.socketTextStream("localhost", 9997, '\n')
//        text1.flatMap(_.split(" ", -1)).map((_, 1))
//            .join(text2.flatMap(_.split(" ", -1)).map((_, 1)))
//            .where(_._1).equalTo(_._1)
//            .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
//            .apply((first, second) => (first._1, first._2 + second._2))
//            .print()

        text1.flatMap(_.split(" ", -1)).map((_, 1))
            .coGroup(text2.flatMap(_.split(" ", -1)).map((_, 1)))
            .where(_._1).equalTo(_._1)
            .window(TumblingProcessingTimeWindows.of(Time.seconds(10)))
            .apply((m, n) => {
                var flag = false
                for (mitem <- m) {
                    for (nitem <- n) {
                        if (!flag) {
                            flag = true
                        }
                        return nitem
                    }
                    if (flag) {
                        return mitem
                    }
                }
            })
            .print()
        env.execute("JoinWindow")
    }

}
