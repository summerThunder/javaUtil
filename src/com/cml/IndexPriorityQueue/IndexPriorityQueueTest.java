package com.cml.IndexPriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IndexPriorityQueueTest {

	@Test
	void test() {
		IndexPriorityQueue<String> ipq=new IndexPriorityQueue<>(10);
		ipq.insert(5, "E");
        ipq.insert(7, "G");
        ipq.insert(2, "B");
        ipq.insert(1, "A");
        if (ipq.contains(7)) {
            ipq.replace(7, "Z");
        }

        System.out.println(ipq.min()); // A
        System.out.println(ipq.delMin()); // 1
        System.out.println(ipq.delMin());// 2
        System.out.println(ipq.minIndex()); // 5
        System.out.println(ipq.keyOf(7)); // Z
        ipq.delete(7);

	}

}
