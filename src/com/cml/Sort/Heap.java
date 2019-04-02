package com.cml.Sort;

import java.util.Comparator;

//这个类下只有方法,默认大堆
 public class Heap<T extends Comparable<T>>  {

     private Comparator<T> cmp;
    
     
	 public Heap() {
		super();
		cmp=new Comparator<T>() {

			@Override
			public int compare(T t1, T t2) {
				// TODO Auto-generated method stub
				if(t1.compareTo(t2)>0) return 1;
				else if(t1.compareTo(t2)==0) return 0;
				else return -1;
			}
		};
	}

	public Heap(Comparator<T> cmp) {
		super();
		this.cmp = cmp;
	}

	public Comparator<T> getCmp() {
		return cmp;
	}

	public void setCmp(Comparator<T> cmp) {
		this.cmp = cmp;
	}

	public T[] buildHeap(T[] array){
		        //从最后一个节点array.length-1的父节点（array.length-1-1）/2开始，直到根节点0，反复调整堆
		          for(int i=(array.length-2)/2;i>=0;i--){ 
		              sink(array, i,array.length);
		          }
		          return array;
		      }
	 
	 //array是整个数组，k是下沉起始点，length是数组有效长度
	 public void sink(T[] array,int k,int length){
	        T temp = array[k];   
	        for(int i=2*k+1; i<length-1; i=2*i+1){    //i为初始化为节点k的左孩子，沿节点较大的子节点向下调整
	            if(i<length && cmp.compare(array[i],array[i+1])<0){  //取节点较大的子节点的下标
	                i++;   //如果节点的右孩子>左孩子，则取右孩子节点的下标
	            }
	            if(cmp.compare(temp,array[i])>0){  //根节点 >=左右子女中关键字较大者，调整结束
	                break;
	            }else{   //根节点 <左右子女中关键字较大者
	                array[k] = array[i];  //将左右子结点中较大值array[i]调整到双亲节点上
	                k = i; //【关键】修改k值，以便继续向下调整
	            }
	        }
	        array[k] = temp;  //被调整的结点的值放人最终位置
	    }
	 
	 public void swim(T[] array,int k) {
	        // k = 1说明当前元素浮到了根结点，它没有父结点可以比较，也不能上浮了，所以k <= 1时候推出循环
	        while (k > 0 && cmp.compare(array[k],array[(k-2)/2])<0) {
	            T temp=array[k];
	            // 上浮后，成为父结点，所以下标变成原父结点
	            array[(k-2)/2]=temp;
	            array[k]=array[(k-2)/2];
	        }
	    } 
	 
	 
	 
	
}
