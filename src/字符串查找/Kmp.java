package 字符串查找;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Kmp {
	private String	text;
	private String	pattern;

	public Kmp(){}
	
	public Kmp(String text, String pattern) {
		this.text = text;
		this.pattern = pattern;
	}
	
	public List<Integer> KMPMatcher() 
	{
		List<Integer> result = new ArrayList<Integer>();
		
		char[] t = text.toCharArray();
		char[] p = pattern.toCharArray();
		int n = t.length;
		int m = p.length;
		// 模式串比目标串长的话就不要比较了
		if (n < 1 || m < 1 || n < m) {
			return result;
		}

		int i = 0; // 目标串索引
		int j = 0; // 模式串索引
		int[] next = getNext2(p); // 计算next数组
		while (i < n) {
			if (j == -1 || t[i] == p[j]) {
				i++;
				j++;
			} else {
				j = next[j]; // 消除了指针i的回溯
			}
			// 如果目标串剩下的长度比模式串剩下的长度短，剩下的就不要匹配了
			if (n - i < m - j) {
				break;
			}
			// 模式串匹配完了，匹配位置在i之前strlen(p)的位置
			if (j == m) {
				result.add(i - m);
				// 为了匹配所有的位置
				i = i - m + 1;
				j = 0;
			}
		}
		return result;
	}

	private int[] getNext(char[] p) {
		int length = p.length;
		int[] next = new int[length];

		next[0] = -1;
		// j相当于目标串的索引，k相当于模式串的索引
		int j = 0;
		int k = -1;
		//这里注意，i==0的时候实际上求的是next[1]的值，以此类推
		while (j < length - 1) {
			// 匹配的情况下,p[j]==p[k]
			if (k == -1 || p[j] == p[k]) {
				j++;
				k++;
				next[j] = k;
				// p[j]==p[k]也就是说p[0..k]==p[j-k...j]
//				next[j+1] = k+1;
//				j++;
//				k++;
			} else {// p[j]!=p[k]
				//模式串的位置调整为next[k]
				k = next[k];
			}
		}
		return next;
	}
	
	private int[] getNext2(char[] p) {
		int length = p.length;
		int[] next = new int[length];

		next[0] = -1;
		// j相当于目标串的索引，k相当于模式串的索引
		int j = 0;
		int k = -1;
		//这里注意，i==0的时候实际上求的是next[1]的值，以此类推
		while (j < length - 1) {
			// 匹配的情况下,p[j]==p[k]
			if (k == -1 || p[j] == p[k]) {
				j++;
				k++;
				next[j] = p[j] != p[k] ? k : next[k];
			} else {// p[j]!=p[k]
				//模式串的位置调整为next[k]
				k = next[k];
			}
		}
		return next;
	}

	public static void main(String[] args) {
		Kmp kmp = new Kmp();
		kmp.text = "0000002000000200000020000002000000200000020000001";
		kmp.pattern = "0000001";
		
		int times = 99999;
		/*------------ KMP --------------*/
		List<Integer> result = null;
		long start = System.currentTimeMillis();
		for (int i = 0; i < times; i++) {
			result = kmp.KMPMatcher();
		}
		long end = System.currentTimeMillis();
		System.out.println("KMP: " + Arrays.toString(result.toArray()));
		System.out.println("KMP use:" + (end - start) + "ms");
		
		/*------------ String.indexOf --------------*/
		start = System.currentTimeMillis();
		for (int i = 0; i < times; i++) {
			result = new ArrayList<Integer>();
			int startIndex = 0;
			while (true) {
				int j = kmp.text.indexOf(kmp.pattern, startIndex);
				if (j == -1) {
					break;
				}
				result.add(j);
				startIndex = j + 1;
			}
		}
		end = System.currentTimeMillis();
		System.out.println("indexOf: " + Arrays.toString(result.toArray()));
		System.out.println("indexOf use:" + (end - start) + "ms");
	}
}
