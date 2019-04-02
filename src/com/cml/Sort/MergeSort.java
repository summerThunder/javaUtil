package com.cml.Sort;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

public class MergeSort<T extends Comparable<T>> {
	private Comparator<T> cmp;
    
    
	 public MergeSort() {
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

	public MergeSort(Comparator<T> cmp) {
		super();
		this.cmp = cmp;
	}
	
    public void merge(T[] a,int left,int mid,int right){
    	//不能创建泛型数组
    	
        ArrayList<T> tmp=new ArrayList<>(a.length);//辅助数组
        int p1=left,p2=mid+1,k=left;//p1、p2是检测指针，k是存放指针

        while(p1<=mid && p2<=right){
            if(cmp.compare(a[p1],a[p2])<=0)
                //tmp[k++]=a[p1++];
                tmp.add(k++, a[p1++]);
            else
                //tmp[k++]=a[p2++];
            	tmp.add(k++, a[p2++]);
        }

        while(p1<=mid) tmp.add(k++, a[p1++]);//如果第一个序列未检测完，直接将后面所有元素加到合并的序列中
        while(p2<=right) tmp.add(k++, a[p2++]);//同上

        //复制回原素组
        for (int i = left; i <=right; i++) 
            a[i]=tmp.get(i);
    }

    public void mergeSort(T [] a,int start,int end){
        if(start<end){//当子序列中只有一个元素时结束递归
            int mid=(start+end)/2;//划分子序列
            mergeSort(a, start, mid);//对左侧子序列进行递归排序
            mergeSort(a, mid+1, end);//对右侧子序列进行递归排序
            merge(a, start, mid, end);//合并
        }
    }

}
