package cn.itcast.d_dbUtils;

import org.junit.Test;

public class Demo {

	public void sum(int... num) {

		int total = 0;
		for (int i=0;i <num.length;i++) {
			total += num[i];
		}
		System.out.println("�ͣ� " + total);
	}

	@Test
	public void test() {
		int[] n = {1,2,3};
		//sum(n);
		sum(1,2,5,5);
	}

}
