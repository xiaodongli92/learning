/**
 * 不可变对象模式
 * 1、使用场景
 *  被建模对象的状态变化不频繁
 *  同时对一组相关数据进行写操作，因此需要保证元自行
 *  使用某个对象作为安全的HashMap的key
 * 2、注意问题
 *  被建模对象的状态变更比较频繁不适用
 *  使用等效或者近似的不可变对象
 *  防御性复制
 */
package com.study.thread.immutable;