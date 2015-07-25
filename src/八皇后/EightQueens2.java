package 八皇后;

import java.util.Arrays;

public class EightQueens2 
{
	// 皇后数
	static int	N	= 8;
	int column[] = new int[N + 1]; // 同栏是否有皇后，1表示有
	// 2*N,用一维数组存储二维信息
	int rup[] = new int[2*N + 1]; // 右上至左下是否有皇后
	int lup[] = new int[2*N + 1]; // 左上至右下是否有皇后
	int queen[] = new int[N + 1];
	int num; // 解答编号
	
	public static void main(String[] args) 
	{
		long t1 = System.currentTimeMillis();
		
		new EightQueens2().init();
		
		long t2 = System.currentTimeMillis();
		// 打印花费的时间。
		System.out.println("花费：" + (t2 - t1) + "ms");
	}
	public void init()
	{
		Arrays.fill(column, 1);
		Arrays.fill(rup, 1);
		Arrays.fill(lup, 1);
		
		backtrack(1);
	}

	private void backtrack(int row) 
	{
		int j;
		if (row > N) {
			num ++;
			printQueen();
		} else {
			for (j = 1; j <= N; j++) {
				if (column[j] == 1 && rup[row + j] == 1 && lup[row - j + N] == 1) {
					queen[row] = j;
					// 设定为占用
					column[j] = rup[row + j] = lup[row - j + N] = 0;
					backtrack(row + 1);
					column[j] = rup[row + j] = lup[row - j + N] = 1;
				}
			}
		}
	}
	/**
	 * 打印皇后
	 */
	void printQueen()
	{
		System.out.println("==================第" + num
				+ "种皇后图==================");
		for (int row = 0; row < N; row++)
		{
			for (int col = 0; col < N; col++)
			{
				if (col == queen[row])
				{
					System.out.print("@ ");
				} else
				{
					System.out.print("* ");
				}
			}
			System.out.println();
		}
	}
}
