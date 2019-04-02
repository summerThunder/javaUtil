package com.cml.RBTree;

import java.util.NoSuchElementException;

import com.cml.RBTree.RBTree.RBTNode;

//算法第四版递归实现红黑树
//三板斧：flipColors,rotate
public class RBTree3<T extends Comparable<T>,V> {
	
	private static final boolean RED = true;//定义RED为true
    private static final boolean BLACK = false;
    private Node<T,V> mRoot; 
    
	private class Node<T,V> {
        private int size;
        private T key;
        private V value;
        private Node<T,V> left;
        private Node<T,V> right;
        private boolean color;
        
        public Node(T key, V value, boolean color, int size) {
            this.color = color;
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }
	private boolean isRed(Node<T,V> x){
        if (x==null) return false;
        return x.color == RED;
    }
	
	private int size(Node<T,V> x) {
		if(x==null) {
			return 0;
		}
		else {
			return 1+size(x.left)+size(x.right);
		}
		
	}
	public int size() {
		return size(mRoot);
	}
	
	//什么时候左旋，红边出现在右边
	private Node<T,V> rotateLeft(Node<T,V> h) {
		//三步走
		Node<T,V> x=h.right;
		h.right=x.left;
		x.left=h;
		
		//维护颜色
		//新根继承颜色
		x.color=h.color;
		//什么时候左旋，红边出现在右边，所以把新左子变红
		h.color=RED;
		x.size=h.size;
		//维护size
		h.size=1+size(h.left)+size(h.right);
		
		return x;
		
	}
	
	//右旋可能造成红子在右
	//只有在RL情况采用
	private Node<T,V> rotateRight(Node<T,V> h) {
		//三步走
		Node<T,V> x=h.left;
		h.left=x.right;
		x.right=h;	
		
		//维护颜色
		//新根继承颜色
		x.color=h.color;
		//什么时候左旋，红边出现在右边，所以把新左子变红
		h.color=RED;
		x.size=h.size;
		//维护size
		h.size=1+size(h.left)+size(h.right);
		
		return x;
		
	}
	 // flip the colors of a node and its two children
    private void flipColors(Node<T,V> h) {
        // h must have opposite color of its two children
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
        //    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }
    
    public void put(T key, V value) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
//        if (value == null) {
//            delete(key);
//        }
        mRoot = put(mRoot, key, value);
        mRoot.color = BLACK;  //根节点为黑
    }

    private Node<T,V> put(Node<T,V> h,T key, V value){
    	//新插入的点是红色,所以递归后需要三连
        if (h==null) return new Node<>(key, value, RED,1);
        int cmp = key.compareTo(h.key);
        if   (cmp<0) h.left = put(h.left, key, value);
       //递归查找
        else if   (cmp>0) h.right = put(h.right, key, value);
        else     h.value = value;
        //插入后再递归更新，类比二三树
        //三板斧对应条件是有先后顺序的！！ 顺许lr情况（rl操作），压平
        //右红左黑，左旋
        if (isRed(h.right) && !isRed(h.left)) h=rotateLeft(h);
        //左连续红，右旋，可能出现双红
        if (isRed(h.left) && isRed(h.left.left)) h=rotateRight(h);
        //双红
        if (isRed(h.left) && isRed(h.right))    flipColors(h);

        h.size = size(h.left)+size(h.right)+1;
      //更新走过的节点的N值
	        return h;

}
    public boolean isEmpty() {
    	return mRoot==null;
    }
    //删点时三种情况（2-3-4树）(先自顶而下，再自下而上)最多三子看，压平时会出现4子
    /* （1）   x
     *     y  z
     *    两子为黑（ if (!isRed(x.left) && !isRed(x.left.left))），
     *    需要压平后（moveRedLeft），再删，即两子变红
     * （2）父亲借子
     * （3）兄弟借子
     */
    
    public void deleteMin() {
        if(isEmpty() ) throw new NoSuchElementException("the tree is empty");
        if (!isRed(mRoot.left) && !isRed(mRoot.right))
            mRoot.color = RED;
        mRoot = deleteMin(mRoot);
        if (!isEmpty()) mRoot.color = BLACK; //根节点恢复为黑
    }
    
   
    
    //本质上是一个压平后，删点再凸起的递归过程，哪怕是进入分支也会压平
    private Node<T,V> deleteMin(Node<T,V> x) {
        if (x.left == null)
            return null;

        
        //这个判断其实是两种情况，根据右子的左子分，可能是3子压平（根在中间），也可能是4子压平（根在第三子）
        if (!isRed(x.left) && !isRed(x.left.left))
            x = moveRedLeft(x);
        x.left = deleteMin(x.left);

     
  
//balance三板斧顺序一样
//为什么要balance，因为
/*
 * 这样删完可能出现双红，四节点删左子，需要flip
 *    右红，三节点删除左子,需要左旋，
 *    左连续红，4节点不删，需要先左子左旋，再根右旋（三个操作顺序记住的方法）
 */
        return balance(x);
    }
 

    
//压平（flip）和兄弟借子（RL）再凸起（flip）
    private Node<T,V> moveRedLeft(Node<T,V> x) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);
        flipColors(x);
        // flipColors后x为黑，左右子节点为红，若x的右节点的左节点也为红，
        // 则需进行平衡操作
        /*    a
         *  b  cd    
         *  对应这种情况删b,要借兄弟借子，再从红黑二叉分析，右子右旋（兄弟借父），根左旋（父借子）
         *  变成
         *   c
         *  ba d
         */
        if(isRed(x.right.left)) {
            x.right = rotateRight(x.right);
            x = rotateLeft(x);
            //
            flipColors(x);
        }
        return x; //注意这里返回值，若进行过平衡操作则不再是原先的节点。
    }
 
// 重新平衡以x为根的子树，该方法在递归回溯过程中不断自下而上修复整个树
    private Node<T,V> balance(Node<T,V> x) {
        if (isRed(x.right) &&!isRed(x.left)) x = rotateLeft(x);
        if (isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
        if (isRed(x.left) && isRed(x.right)) flipColors(x);
        
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }
    
    
 
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("tree is null");
        if (!isRed(mRoot.left) && !isRed(mRoot.right))
            mRoot.color = RED;
        mRoot = deleteMax(mRoot);
        if (!isEmpty()) mRoot.color = BLACK;
    }
    
    //和deleteMin很多地方不一样，不是简单左右反写
    private Node<T,V> deleteMax(Node<T,V> x) {
    //不同之处1，deleteMin省掉是因为右子肯定不可能红
        if (isRed(x.left))
        	//为什么要右旋，因为删较大的又不能删根
            rotateRight(x);
// 最大节点最多有一个左红子节点，这种情况该左子节点会经过上面的右旋操作，使得最后最大节点一定没有
//子节点
        if (x.right == null)
            return null;
// 不同之处2，判压平条件
        if (!isRed(x.right) && !isRed(x.right.left))
            x = moveRedRight(x);
        x.right = deleteMax(x.right);
// 回溯中自下而上修复树
        return balance(x);
    }
 

    private Node<T,V> moveRedRight(Node<T,V> h) {
 
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
 
        flipColors(h);
        
        //不同之处3 四子压平根为第二个，只需要根右旋即可,再凸起
        if (isRed(h.left.left)) { 
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    
    }
    
    public boolean contains(T key) {
    	return contains(key,mRoot);
    }
    
    private boolean contains(T key,Node<T,V> node) {
		if(node==null) return false;
		else if(node.key==key) return true;
		else return contains(key,node.left)||contains(key,node.right);
    }
    
    public void delete(T key) {
        if (key == null) throw new IllegalArgumentException("argument to delete is null");
        if (!contains(key)) return;
        if (!isRed(mRoot.left) && !isRed(mRoot.right))
            mRoot.color = RED;
        mRoot = delete(mRoot, key);
        if (!isEmpty()) mRoot.color = BLACK;
    }
    
    
    //删除某键等价于删除右子最小，然后把根替换成删除的
    private Node<T,V> delete(Node<T,V> x, T key) {
        if (key.compareTo(x.key) < 0) {
            if (!isRed(x.left) && !isRed(x.right))
                x = moveRedLeft(x);
            x.left = delete(x.left, key);
        } 

        else {
            if (isRed(x.left))
            	//如果进入右分支，红左需要右旋
                x = rotateRight(x);
            // 若找到该节点且其右子树为空（表明该节点没有子节点，因为上面的左旋操作），删除
            if (key.compareTo(x.key)==0 && x.right == null) 
                return null;
            //因为右旋了可能产生右红，如果产生右红就不用压平了
            if (!isRed(x.right) && !isRed(x.right.left)) 
                x = moveRedRight(x);
            
            //找到等于的值之后，且有右子
            if (key.compareTo(x.key) == 0) {
                Node<T,V> min = min(x.right);
                x.key =  min.key;
                x.value =  min.value;
                x.right = deleteMin(x.right);
            }
            //否则右子递归
            else x.right = delete(x.right, key);
        }
        return balance(x);
    }
 
    public Node<T,V> min(Node<T,V> x) {
        //assert x!=null
        if (x.left == null) return x;
        else return min(x.left);
    }

    private void preOrder(Node<T,V> tree) {
        if(tree != null) {
        	System.out.print(tree.key+":"+tree.value+" ");
            preOrder(tree.left);
            preOrder(tree.right);
        }
    }

    public void preOrder() {
        preOrder(mRoot);
    }

    /*
     * 中序遍历"红黑树"
     */
    private void inOrder(Node<T,V> tree) {
        if(tree != null) {
            inOrder(tree.left);
            System.out.print(tree.key+":"+tree.value+" ");
            inOrder(tree.right);
        }
    }

    public void inOrder() {
        inOrder(mRoot);
    }


    /*
     * 后序遍历"红黑树"
     */
    private void postOrder(Node<T,V> tree) {
        if(tree != null)
        {
            postOrder(tree.left);
            postOrder(tree.right);
            System.out.print(tree.key+":"+tree.value+" ");
        }
    }

    public void postOrder() {
        postOrder(mRoot);
    }
    
    
    
    
    

	

}
