package com.cml.Sort;

import java.util.Comparator;

public class QuickSort<T extends Comparable<T>> {
	private Comparator<T> cmp;
    
    
	 public QuickSort() {
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

	public QuickSort(Comparator<T> cmp) {
		super();
		this.cmp = cmp;
	}
	
    public void partition(T[] a,int length) {
    	int start = 0;
        int end = length-1;
        T key = a[0];
        
        
        while(end>start){
            //从后往前比较
            while(end>start&&cmp.compare(a[end],key)>0)  //如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
                end--;
            if(cmp.compare(a[end],key)<=0){
                T temp = a[end];
                a[end] = a[start];
                a[start] = temp;
            }
            //从前往后比较
            while(end>start&&cmp.compare(a[start],key)<=0)//如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
               start++;
            if(cmp.compare(a[start],key)>=0){
                T temp = a[start];
                a[start] = a[end];
                a[end] = temp;
             }
        }
     }
}
    
