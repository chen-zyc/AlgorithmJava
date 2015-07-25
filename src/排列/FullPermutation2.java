package 排列;

public class FullPermutation2
{
	int count = 0; // 排列数
	void perm(char[] list, int k, int m) // k表示前缀的位置,m是要排列的数目.
	{
		if (k == m - 1) // 前缀是最后一个位置,此时打印排列数.
		{
			for (int i = 0; i < m; i++)
			{
				System.out.print(list[i]);
			}
			count ++;
			System.out.println();
		} else
		{
			for (int i = k; i < m; i++)
			{
				// 交换前缀,使之产生下一个前缀.
				swap(list, k, i);
				perm(list, k + 1, m);
				// 将前缀换回来,继续做上一个的前缀排列.
				swap(list, k, i);
			}
		}
	}

	void swap(char[] list, int k, int m)
	{
		char temp = list[k];
		list[k] = list[m];
		list[m] = temp;
	}
	
	public static void main(String[] args)
	{
		FullPermutation2 f = new FullPermutation2();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++)
		{
			f.count = 0;
			char[] c = {'a', 'b', 'c', 'd'};
			f.perm(c, 0, c.length);
			System.out.println("------------" + f.count + "--------------------");
		}
		long end = System.currentTimeMillis();
		System.out.println("use " + (end - start) + "ms");
	}
	
}
