package com.cml.UnionFind;

abstract public class UnionFind{
	 /**
     * 数组，表示并查集所有元素
     */
    protected int[] id;
	//private HashMap<T> id;
 
    /**
     * 并查集的元素个数
     */
    private int size;
 
    /**
     * 构造一个新的并查集
     *
     * @param size 初始大小
     */
    public UnionFind(int size) {
        //初始化个数
        this.size = size;
        //初始化数组，每个并查集都指向自己
        id = new int[size];
        for (int i = 0; i < size; i++) {
            id[i] = i;
        }
    }
    
//   abstract public boolean shouldUnion(int i1,int i2);
 
    /**
     * 查看元素所属于哪个集合
     *
     * @param element 要查看的元素
     * @return element元素所在的集合
     */
    //快union,慢find,即路径压缩
    private int findFather(int element) {
//        return id[element];
       //这里的element只是原始id
    	int a=element;
    	//找element根节点,即为循环后的element
    	while(element!=id[element]) {
    		element=id[element];
    	}
    	
    	//路径压缩把沿途的路径的ID都设为根节点
    	while(a!=id[a]) {
    		int z=a;
    		a=id[a];
    		id[z]=element;
    	}
    	
    	return element;
    	
    }
 
    /**
     * 判断两个元素是否同属于一个集合
     *
     * @param firstElement  第一个元素
     * @param secondElement 第二个元素
     * @return <code>boolean</code> 如果是则返回true。
     */
    public boolean isConnected(int firstElement, int secondElement) {
        return findFather(firstElement) == findFather(secondElement);
    }
 
    /**
     * 合并两个元素所在的集合，也就是连接两个元素
     *
     * @param firstElement  第一个元素
     * @param secondElement 第二个元素
     */
    
    //合并后留下来的
    abstract public int unionElements(int firstElement, int secondElement);
        //找出firstElement所在的集合
//        int firstUnion = findFather(firstElement);
//        //找出secondElement所在的集合
//        int secondUnion = findFather(secondElement);
// 
//        //如果这两个不是同一个集合，那么合并。
//        if (firstUnion != secondUnion) {
//            //遍历数组，使原来的firstUnion、secondUnion合并为secondUnion
//            for (int i = 0; i < this.size; i++) {
//                if (id[i] == firstUnion) {
//                    id[i] = secondUnion;
//                }
//            }
//        }
    
 
    /**
     * 本并查集使用数组实现，为了更直观地看清内部数据，采用打印数组
     */
    private void printArr() {
        for (int id : this.id) {
            System.out.print(id + "\t");
        }
        System.out.println();
    }

	public int[] getId() {
		return id;
	}

	public void setId(int[] id) {
		this.id = id;
	}
    
    
}
