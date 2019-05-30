/**
 * Created by xiaodong on 2017/6/22.
 *
 * aviator
 *
 * Aviator是一个高性能、轻量级的 java 语言实现的表达式求值引擎, 主要用于各种表达式的动态求值。
 * 现在已经有很多开源可用的 java 表达式求值引擎,为什么还需要 Avaitor 呢?
 * Aviator的设计目标是轻量级和高性能,相比于Groovy、JRuby的笨重, Aviator非常小,
 * 加上依赖包也才450K,不算依赖包的话只有 70K; 当然, Aviator的语法是受限的, 它不是一门完整的语言,
 * 而只是语言的一小部分集合。
 * 其次, Aviator的实现思路与其他轻量级的求值器很不相同, 其他求值器一般都是通过解释的方式运行,
 * 而Aviator则是直接将表达式编译成Java 字节码, 交给JVM去执行。简单来说,
 * Aviator的定位是介于Groovy这样的重量级脚本语言和IKExpression这样的轻量级表达式引擎 之间。
 * Aviator支持大部分运算操作符, 包括算术操作符、关系运算符、逻辑操作符、位运算符、正则匹配操作符(=~)、三元表达式(?:),
 * 并且支持操作符的优先级和括号强制优先级, 具体请看后面的操作符列表, 支持自定义函数.
 *
 * 内置函数
 * sysdate()	返回当前日期对象 java.util.Date
 * rand()	返回一个介于 0-1 的随机数,double 类型
 * print([out],obj)	打印对象,如果指定 out,向 out 打印, 否则输出到控制台
 * println([out],obj)	与 print 类似,但是在输出后换行
 * now()	返回 System.currentTimeMillis
 * long(v)	将值的类型转为 long
 * double(v)	将值的类型转为 double
 * str(v)	将值的类型转为 string
 * date_to_string(date,format)	将 Date 对象转化化特定格式的字符串,2.1.1 新增
 * string_to_date(source,format)	将特定格式的字符串转化为 Date 对 象,2.1.1 新增
 * string.contains(s1,s2)	判断 s1 是否包含 s2,返回 Boolean
 * string.length(s)	求字符串长度,返回 Long
 * string.startsWith(s1,s2)	s1 是否以 s2 开始,返回 Boolean
 * string.endsWith(s1,s2)	s1 是否以 s2 结尾,返回 Boolean
 * string.substring(s,begin[,end])	截取字符串 s,从 begin 到 end,如果忽略 end 的话,将从 begin 到结尾,与 java.util.String.substring 一样。
 * string.indexOf(s1,s2)	java 中的 s1.indexOf(s2),求 s2 在 s1 中 的起始索引位置,如果不存在为-1
 * string.split(target,regex,[limit])	Java 里的 String.split 方法一致,2.1.1 新增函数
 * string.join(seq,seperator)	将集合 seq 里的元素以 seperator 为间隔 连接起来形成字符串,2.1.1 新增函数
 * string.replace_first(s,regex,replacement)	Java 里的 String.replaceFirst 方法, 2.1.1 新增
 * string.replace_all(s,regex,replacement)	Java 里的 String.replaceAll 方法 , 2.1.1 新增
 * math.abs(d)	求 d 的绝对值
 * math.sqrt(d)	求 d 的平方根
 * math.pow(d1,d2)	求 d1 的 d2 次方
 * math.log(d)	求 d 的自然对数
 * math.log10(d)	求 d 以 10 为底的对数
 * math.sin(d)	正弦函数
 * math.cos(d)	余弦函数
 * math.tan(d)	正切函数
 * map(seq,fun)	将函数 fun 作用到集合 seq 每个元素上, 返回新元素组成的集合
 * filter(seq,predicate)	将谓词 predicate 作用在集合的每个元素 上,返回谓词为 true 的元素组成的集合
 * count(seq)	返回集合大小
 * include(seq,element)	判断 element 是否在集合 seq 中,返回 boolean 值
 * sort(seq)	排序集合,仅对数组和 List 有效,返回排 序后的新集合
 * reduce(seq,fun,init)	fun 接收两个参数,第一个是集合元素, 第二个是累积的函数,本函数用于将 fun 作用在集合每个元素和初始值上面,返回 最终的 init 值
 * seq.eq(value)	返回一个谓词,用来判断传入的参数是否跟 value 相等,用于 filter 函数,如filter(seq,seq.eq(3)) 过滤返回等于3 的元素组成的集合
 * seq.neq(value)	与 seq.eq 类似,返回判断不等于的谓词
 * seq.gt(value)	返回判断大于 value 的谓词
 * seq.ge(value)	返回判断大于等于 value 的谓词
 * seq.lt(value)	返回判断小于 value 的谓词
 * seq.le(value)	返回判断小于等于 value 的谓词
 * seq.nil()	返回判断是否为 nil 的谓词
 * seq.exists()	返回判断不为 nil 的谓词
 */
package com.study.aviator;