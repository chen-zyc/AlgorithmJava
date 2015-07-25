package 八枚银币;

import java.util.Arrays;
import java.util.Random;

/**
 * 现有八枚银币a b c d e f g h，
 * 已知其中一枚是假币，其重量不同于真币，但不知是较轻或较重，
 * 如何使用天平以最少的比较次数，决定出哪枚是假币，并得知假币比真币较轻或较重
 * @author zhangyuchen
 *
 */
public class EightCoins 
{
	public static void main(String[] args) {
		new EightCoins().init();
	}
	
	private int[] coins = new int[8];
	private int count = 0; // 比较次数
	
	public void init()
	{
		Arrays.fill(coins, 10); // 默认重量
		// 假币重量
		Random random = new Random(System.currentTimeMillis());
		if(random.nextBoolean()){
			// 假币重
			coins[random.nextInt(8)] = 11;
		}else{
			coins[random.nextInt(8)] = 9;
		}
		
		eightCoins();
		System.out.println("比较了" + count + "次");
		System.out.println(Arrays.toString(coins));
	}
	
	private void eightCoins()
	{
		// 先分成三堆，[0,1,2], [3,4,5], [6,7]
		count++;
		if (coins[0] + coins[1] + coins[2] == coins[3] + coins[4] + coins[5]) {
			// 假币在[6,7]中
			count++;
			if (coins[6] > coins[7]) {
				compare(6, 7, 0);
			} else {
				compare(7, 6, 0);
			}
		}
		// 将[0,1,2,3,4,5]分成[0,3],[1,4],[2,5]
		else if (coins[0] + coins[1] + coins[2] > coins[3] + coins[4] + coins[5]) {
			// 假币在2,5中，且2>5
			count++;
			if (coins[0] + coins[3] == coins[1] + coins[4]) {
				compare(2, 5, 0);
			} else if (coins[0] + coins[3] > coins[1] + coins[4]) {
				// 2,5是真的，且0+1>3+4, 0+3>1+4,也就是说调换1,3不影响结果，1,3是真的
				compare(0, 4, 1);
			} else {
				// 2,5是真的，且0+1>3+4, 0+3 < 1+4,也就是说调换1,3影响了结果，1,3是假的
				compare(1, 3, 0);
			}
		}
		// 0 + 1 + 2 < 3 + 4 + 5
		else {
			count++;
			if (coins[0] + coins[3] == coins[1] + coins[4]) {
				compare(5, 2, 0);
			} else if (coins[0] + coins[3] > coins[1] + coins[4]) {
				// 2,5是真的，且0+1<3+4, 0+3>1+4,也就是说调换1,3影响了结果，1,3是假的
				compare(3, 1, 0);
			} else {
				// 2,5是真的，且0+1<3+4, 0+3 < 1+4,也就是说调换1,3不影响结果，1,3是真的
				compare(4, 0, 1);
			}
		}
	}
	
	/**
	 * i,j中有一个假的，k是真的
	 * i比j重，如果i也比k重，则i是假币，且比真币重
	 * 			如果i不比k重，由于i比j重，所以只能i==k,j是假币
	 * @param i
	 * @param j
	 * @param k
	 */
	private void compare(int i, int j, int k)
	{
		count ++;
		if(coins[i] > coins[k]){
			System.out.println("假币" + i + "比真币重");
		}else{
			System.out.println("假币" + j + "比真币轻");
		}
	}
}
