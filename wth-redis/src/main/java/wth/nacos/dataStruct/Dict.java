package wth.nacos.dataStruct;

/**
 * 由DictHashTable和DictEntry和字典组成
 * 保存键值：
 * 1. hash运算： hash(key) & sizeMask （运用到2的次幂-1的位运算）
 * 2. table为表数组，每个元素为一个DicEntry
 * 3. 解决hash冲突：链表（头插法（因为redis单线程不存在多线程头插的循环问题），维护dicEntry的顺序，） -》之前的HashMap（Jdk1.7为头插会导致多线程resize时导致循环）
 * */
public class Dict<T,V> {
    /**
     * 主要是对于rehash算法
     * 扩容：当LoadFactor * size > used 时扩容(考虑到RDB和RewriteAOF的后台进程的损耗的情况)
     * 负载因子 = 已使用的节点数 / 数组长度(used / size)
    * */
    DictType type;// dict类型，内置不同的的hash函数

    void *privateData; // 私有数据，用于实现不同类型的hash算法
    DicTht ht[2];// 扩容时使用ht[1] ，扩容完成之后交换两个表
    long rehashIndex;// 正在迁移的索引位置, -1为未进行rehash
    boolean paushed; // 是否正在迁移

    double loadFactor; // 负载因子，扩容的阈值

    /**
     * HashTable的结构
     * 数组 + 链表
     * */
    public class DicTht {
        DicEntry[] table;

        Integer size; // 一般为2的幂，用于计算索引值 :size -1  即使指定容量不是2的次幂，也会扩容到下一个满足条件的2的次幂
        Integer sizeMask; // 掩码，用于计算索引值 :size -1
        Integer used;// 已使用的节点数entry个数 ，一般大于size
    }

    public class DicEntry<T,V> {
        T Key;
        V value;
        DicEntry next;// 解决hash冲突
    }

    /**
     * size为扩容后的size, (满足2的次幂，并且大于used/loadFactor）
     *
     * rehash不是立即完成的，而是在扩容的时候逐步完成的
     * 渐进式rehash:每次在增删改查时把ht[0] 的对于的entry迁移到ht[1]
     * 注意新增时是不插入ht[0]的，而是在ht[1]新增.在查询最新的数据先查ht[0]查不到再查ht[1] .
     *
     * */
    public void reHash(Integer size) {

    }


    public void Delete(T key) {
        // 删除时

        // 判断负载因子小于0.1进行收缩


    }

    public void DicExpand(Integer size) {
        // 如果当前是在rehash，则不进行扩容

        // 找到大于等于size的第一个2的n次幂,

        // 扩容,申请空间

        // 迁移数据ht[0] -> ht[1]

        // rehashIndex = 0;表示进行rehash了.

        // 当rehash元素的个数为当前的个数表示rehash完成
        // 吧ht[0]换成ht[1], ht[1]换成空
    }

}
