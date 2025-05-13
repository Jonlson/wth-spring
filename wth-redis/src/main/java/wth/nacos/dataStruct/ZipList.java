package wth.nacos.dataStruct;

/**
 * 双端链表 ：连续内存，任意一端弹入弹出
 *  数据存储 ： 小端存储：
 * 位信息： zbyte(总的字节数 ) ztial（尾节点的偏移量） zllen（entry元素的个数） entry entry ... entry zlend(1个字节（标记末端）)
 * entry ： prevlen（便于从后往前遍历）（前一个节点的长度，1个或者5（1（固定0xfe） +4（真正存储真实长度数据））个字节） encoding（编码方式 (数据类型 + data的长度)） data（数据）
 * encoding ： 前两位表示数据类型，后6位表示编码长度(和data有关 1 / 4/  5字节 )
 *  对于string就是data( 数据 )
 *
 * 缺点：查询方式不不普遍
 *
 * */
public class ZipList {

    /** 11 11 0011 -》 小端存储
     * 对于数字部分 ：conding前两位为11 +后面固定编码表示基本数据类型
     * zbyte ztial zllen (prevlen encoding data) zlend
     *
     *
     * */







    /**
     * ZipList的连锁更新问题： 新增和修改操作
     * 当数据的前一个节点的数据的长度变大或者变小时，后面的节点的prevlen要维护前一个节点的长度，如果切好自己的总长度恰好要大于255就又要改变
     *
     *
     *
     *
     * */











}
