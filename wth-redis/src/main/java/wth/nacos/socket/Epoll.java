package wth.nacos.socket;

/**
 * fd :文件描述符：
 *
 *
 * 优点不需要全部把红黑树进行拿出来，拷贝到用户空间
 * select、poll在就绪时全部拷贝需要进行再进行循环遍历哪些是完好的
 * select、poll在放入fd时需要全部拷贝到内核
 * select的数量受限于1024. poll不受限于1024 但是链表的查询效率低。
 *
 * 以空间换时间的策略
 * */
public class Epoll {


    /**
     * 内核空间：
     *
     * 这个EventPoll时用户空间epoll_create(size) 创建epoll实例
     *
     *
     * epoll_ctl(epfd, op, fd, callback)添加
     *
     * epoll_wait(epfd, events, timeout) 阻塞等待,检查就绪元素。把就绪的数据放到event中接收
     *
     * */
    public class EventPoll {
        // 红黑树，记录要监听的fd,所有的fd

        // 链表，记录就绪的fd
    }


    public class RedBlackTree <T, V>{
        Node root;
        Integer size;

        public class Node<T, V> {

        }
        public void add(Node node) {

        }

        public void remove(Node node) {

        }

        public Node findNode(Integer val) {

        }
    }

    public class EpollList <T, V>{
        Node Head;
        Integer size;

        public class Node<T, V> {

        }
        public void add(Node node) {

        }

        public void remove(Node node) {

        }

        public Node findNode(Integer val) {

        }
    }

    public class EpollCtl {
        Integer epfd;// epoll实例的fd的句柄
        Integer op; // 操作类型,Add,Del,Mod
        Integer fd; // 操作的fd
        EpollEvent callback; // 回调函数,会在fd对应操作完成之后触发并放到就绪链表中

        // 添加fd到epoll， 不会阻塞，立即返回
        public void add(Integer fd) {

        }
        /**
         * 要监听的类型
         *
         * */
        public class  EpollEvent {
            Integer events; // 事件类型，可读，可写等

        }

    }


}
