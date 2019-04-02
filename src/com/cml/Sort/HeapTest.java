package com.cml.Sort;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

class HeapTest {

	//验证小堆
	@Test
	void test() {
		Integer[] a= {1,3,4,5,2,6,2,7};
		Comparator<Integer> cmp=new Comparator<Integer>() {
			
			@Override
			public int compare(Integer t1, Integer t2) {
				// TODO Auto-generated method stub
				if(t1.compareTo(t2)>0) return -1;
				else if(t1.compareTo(t2)==0) return 0;
				else return 1;
			}
		};
		Heap<Integer> mh=new Heap<Integer>(cmp);
		mh.buildHeap(a);
		System.out.println(Arrays.toString(a));
		
		
		
		
	}

}
