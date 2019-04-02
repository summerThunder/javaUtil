package com.cml.RBTree;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RBTree3Test {

	@Test
	void test() {
		RBTree3<Integer,String> rbt=new RBTree3<>();
		rbt.put(1, "one");
		rbt.put(2, "two");
		rbt.put(3, "three");
		rbt.put(4, "four");
		rbt.preOrder();
		rbt.delete(2);
		System.out.println();
		rbt.preOrder();
		
	}

}
