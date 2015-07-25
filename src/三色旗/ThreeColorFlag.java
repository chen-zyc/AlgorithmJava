package 三色旗;

import java.util.Arrays;

public class ThreeColorFlag
{
	public static void main(String[] args)
	{
		char[] flags = "rrbbwwbrrbw".toCharArray();
		sort(flags);
		System.out.println(Arrays.toString(flags));
		System.out.println("移动了" + moveTimes + "次。");

	}
	
	public static int moveTimes = 0; // 移动次数

	public static void sort(char[] flags)
	{
		if (flags == null || flags.length <= 1)
		{
			return;
		}
		int bFlag = 0; // 表示bFlag以前的都是b
		int current = 0; // 当前正在判断的字符
		int rFlag = flags.length - 1; // 表示rFlag以后的都是r
		while (current <= rFlag)
		{
			switch (flags[current])
			{
				// 遇到w，继续扫描，即不管w
				case 'w':
					current++;
					break;
				// 遇到b，将b放在bFlag处
				case 'b':
					swap(flags, current, bFlag);
					current++;
					bFlag++; // b总是跟在w后面
					break;
				// 遇到r，将r放在rFlag处
				case 'r':
					// 优化处理，将后面的r全部标记出来，减少移动次数
					while(current < rFlag && flags[rFlag] == 'r'){
                        rFlag--;
					}
					swap(flags, current, rFlag);
					rFlag--;
					break;
				default:
					throw new IllegalArgumentException("invalid input");
			}
		}
	}

	private static void swap(char[] array, int i, int j)
	{
		char tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
		moveTimes++;
	}

}
