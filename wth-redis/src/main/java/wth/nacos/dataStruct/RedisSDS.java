package wth.nacos.dataStruct;

/**
 * 简单动态字符串：
 * 1. len + alloc(申请的总内存) + flags不同SDS头类型(5种类型控制len的数据类型) + buf[]
 * 2. alloc的动态分配(针对是扩 展后的2倍)： 0~1M :扩容到两倍  1M之后为 + 1M
 * 3. 总长度易于计算
 * 4. 减少重新分配次数
 * */
public class RedisSDS {

}
