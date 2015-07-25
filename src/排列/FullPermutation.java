package 排列;


/**
 * 全排列
 * 
 * @author zhangyuchen
 * 
 */
public class FullPermutation
{
	int	count	= 0;	// 统计字符串的全排列数目

	/**
	 * 字符串的全排列算法。
	 * 
	 * @param c字符串对应的字符数组
	 * @param start 起始位置
	 */
	void fullPermutation(char[] c, int start)
	{
		if (start == c.length - 1)
		{
			count++;
			System.out.println(new String(c));// 打印当前排列
		} else
		{
			char temp = ' ';
			boolean bool = false;
			for (int i = start; i < c.length; i++)
			{
				bool = (i != start); // i与start相等时不交换。
				// 为避免生成重复排列，当不同位置的字符相同时不再交换
				if (bool && c[i] == c[start])
				{
					continue;
				}
				if (bool)
				{
					// 交换
					temp = c[start];
					c[start] = c[i];
					c[i] = temp;
				}
				fullPermutation(c, start + 1);// 递归
				if (bool)
				{
					// 回溯
					c[i] = c[start];
					c[start] = temp;
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		FullPermutation f = new FullPermutation();
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++)
		{
			f.count = 0;
			char[] c = {'a', 'b', 'c', 'd'};
			f.fullPermutation(c, 0);
			System.out.println("------------" + f.count + "--------------------");
		}
		long end = System.currentTimeMillis();
		System.out.println("use " + (end - start) + "ms");
	}
}
