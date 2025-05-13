package wth.nacos.dataStruct;

/**
 * 整数集合 ： 整数数组 + 长度可变 + 元素有序（默认升序）
 * 1. 支持类型扩展如从short到long : copy on write扩容（原来元素倒序进行元素的拷贝）
 * 2. 底层进行二分进行查找。
 * 3. 数据量多时，其实可分配连续的位置也会少
 * 4. 数据量多位置后移也有效率问题
 * 5.不如跳表等B+树|链表等结构
 * */
public class IntSet {
    /**
     * typedef struct inset {
     *     uint32_t length;
     *     uint32_t encoding; // 编码方式：支持16位和32位和64位整数
     *     int8_t contents[]; // 整数数组 : 存储指针
     * } inset;
     * }
     * */
}
