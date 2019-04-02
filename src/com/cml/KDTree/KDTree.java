package com.cml.KDTree;

import java.util.ArrayList;

public class KDTree {
	public class Node implements Comparable<Node>{
	    public double[] data;//树上节点的数据  是一个多维的向量
	    public double distance;//与当前查询点的距离  初始化的时候是没有的
	    public Node left,right,parent;//左右子节点  以及父节点
	    public int dim=-1;//维度  建立树的时候判断的维度
	     
	    public Node(double[] data)
	    {
	        this.data=data;
	    }
	     
	    /**
	     * 返回指定索引上的数值
	     * @param index
	     * @return
	     */
	    public double getData(int index)
	    {
	        if(data==null || data.length<=index)
	            return Integer.MIN_VALUE;
	        return data[index];
	    }

	    @Override
	    public int compareTo(Node o) {
	        if(this.distance>o.distance)
	            return 1;
	        else if(this.distance==o.distance)
	            return 0;
	        else return -1;
	    }
	     
	    /**
	     * 计算距离 这里返回欧式距离
	     * @param that
	     * @return
	     */
	    public double computeDistance(Node that)
	    {
	        if(this.data==null || that.data==null || this.data.length!=that.data.length)
	            return Double.MAX_VALUE;//出问题了  距离最远
	        double d=0;
	        for(int i=0;i<this.data.length;i++)
	        {
	            d+=Math.pow(this.data[i]-that.data[i], 2);
	        }
	         
	        return Math.sqrt(d);
	    }
	     
	    public String toString()
	    {
	        if(data==null || data.length==0)
	            return null;
	        StringBuilder sb=new StringBuilder();
	        for(int i=0;i<data.length;i++)
	            sb.append(data[i]+" ");
	        sb.append(" d:"+this.distance);
	        return sb.toString();
	    }
	}

	public Node buildKDTree(ArrayList<Node> nodeList,int index)
    {
        if(nodeList==null || nodeList.size()==0)
            return null;
        quickSortForMedian(nodeList,index,0,nodeList.size()-1);//中位数排序
        Node root=nodeList.get(nodeList.size()/2);//中位数 当做根节点
        root.dim=index;
        ArrayList<Node> leftNodeList=new ArrayList<Node>();//放入左侧区域的节点  包括包含与中位数等值的节点-_-
        ArrayList<Node> rightNodeList=new ArrayList<Node>();
         
        for(Node node:nodeList)
        {
            if(root!=node)
            {
                if(node.getData(index)<=root.getData(index))
                    leftNodeList.add(node);//左子区域 包含与中位数等值的节点
                else
                    rightNodeList.add(node);
            }
        }
         
        //计算从哪一维度切分
        int newIndex=index+1;//进入下一个维度
        if(newIndex>=root.data.length)
            newIndex=0;//从0维度开始再算
        
        
        root.left=buildKDTree(leftNodeList,newIndex);//添加左右子区域
        root.right=buildKDTree(rightNodeList,newIndex);
         
        if(root.left!=null)
            root.left.parent=root;//添加父指针  
        if(root.right!=null)
            root.right.parent=root;//添加父指针  
        return root;
    }
}
