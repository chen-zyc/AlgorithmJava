package 骑士走棋盘;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Knight extends JFrame
{
	public static void main(String[] args) 
	{
		new Knight().init();;
	}
	
	private MyPanel panel = new MyPanel();
	public void init()
	{
		super.setBounds(100, 100, 500, 500);
		super.add(panel);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setVisible(true);
		if (travel(0, 0)) {
			System.out.println("遍历成功!");
		} else {
			System.out.println("遍历失败!");
		}
	}
	
	
	private int SIZE = 8;
	private int[][] board = new int[SIZE][SIZE];
	/**
	 * 记录步骤的，key为第几步，value存储x,y信息
	 */
//	private Map<Integer, Integer[]> path = new HashMap<Integer, Integer[]>(); // 不用Map，因为keySet不能按顺序保存
	private List<Integer[]> path = new ArrayList<Integer[]>();
	/**
	 *  下一跳可能的八个方向(x坐标y坐标)
	 *  mvi[0]和mvj[0]对应，[-2,1]表示x向左2格，y向上1格
	 */
	final int mvi[]	= { -2, -1, 1, 2, 2, 1, -1, -2 };
	final int	mvj[]	= { 1, 2, 2, 1, -1, -2, -2, -1 };
	
	private boolean travel(int x, int y)
	{
		// 当前位置为第1次走的位置
		this.board[x][y] = 1; 
		this.path.add(new Integer[]{x, y});
		// 下一跳位置，共8个方向
		int nexti[] = new int[8];
		int nextj[] = new int[8];
		
		int i = x;
		int j = y;
		int MAX = SIZE * SIZE;
		int count = 0;
		
		// 遍历棋盘，m标记还有几步才能走满棋盘
		for (int m = 2; m <= MAX; m++)
		{
			// 得到可能的下一跳数目
			count = possible(nexti, nextj, i, j);
			// 无可走方向,遍历失败
			if (count == 0) return false; 
			// 在多个可能方向中查找下一跳方向最少的方向
			int min_Direction = min(nexti, nextj, count);
			// 下一跳位置坐标
			i = nexti[min_Direction]; 
			j = nextj[min_Direction];
			// 当前位置为第m次走的位置
			board[i][j] = m;
			this.path.add(new Integer[]{i, j});
			refresh();
		}
		return true;
	}
	
	/**
	 * 找到当前位置[x,y]的下一跳的可能位置
	 * @param nexti 存储能够进行下一跳的位置（棋盘的位置）
	 * @param nextj 存储能够进行下一跳的位置（棋盘的位置）
	 * @param x 当前位置坐标
	 * @param y 当前位置坐标
	 * @return 能够跳的方向数
	 */
	private int possible(int[] nexti, int[] nextj, int x, int y)
	{
		int count = 0;
		for (int i = 0; i < 8; ++i) {
			// 下一跳的位置
			int tmpx = x + mvi[i];
			int tmpy = y + mvj[i];
			// 越界,不是可行方向
			if (tmpx < 0 || tmpy < 0 || tmpx > SIZE - 1 || tmpy > SIZE - 1){
				continue;
			}
			// 未走过,找到一个可能方向
			if (board[tmpx][tmpy] == 0) {
				nexti[count] = tmpx;
				nextj[count] = tmpy;
				count++;
			}
		}
		// 返回可能的方向数
		return count;                             
	}
	
	/**
	 * 在nexti,nextj中找出最小跳数的下一跳位置
	 * @param nexti 下一跳的位置
	 * @param nextj 下一跳的位置
	 * @param count 上面两个数组的有效元素的长度
	 * @return 最小跳数的数组索引
	 */
	private int min(int[] nexti, int[] nextj, int count)
	{
		// 记录该方向的下一跳方向的数目
		// exist[0]表示nexti[0],nextj[0]在8个方向中可行的方向数
		int exist[] = new int[8];
		// 初始化最小方向数的方向
		int min_direction = -1;
		// 只有一个可行方向，最小方向数就是[nexti[0],nextj[0]]
		if (count == 1){
			min_direction = 0;
		} else {
			// 在所有可行方向中遍历
			for (int i = 0; i < count; ++i) {
				// 计算每一个方向下一跳的数目
				// 这里计算了一遍，在possible中又计算了一遍，有优化的可能
				for (int j = 0; j < 8; ++j) {
					int tmpx = nexti[i] + mvi[j];
					int tmpy = nextj[i] + mvj[j];
					if (tmpx < 0 || tmpy < 0 || tmpx > SIZE - 1 || tmpy > SIZE - 1) {
						continue;
					}
					if (board[tmpx][tmpy] == 0) {
						exist[i]++;
					}
				}
			}
			// 取其中最小数目的方向
			int min = exist[0];
			min_direction = 0;
			for (int i = 1; i < count; ++i) {
				// exist数组只有count位不为0
				if (exist[i] < min) {
					min = exist[i];
					min_direction = i;
				}
			}
		}
		return min_direction;
	}
	
	private void refresh()
	{
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		panel.repaint();
	}
	
	class MyPanel extends JPanel
	{

		@Override
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			// 背景
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, getWidth(), getHeight());
			// 绘制棋盘
			g2.setFont(new Font("微软雅黑", Font.BOLD, 18));
			int leftMargin = 50;
			int topMargin = 50;
			int width = 30;
			int height = 30;
			g2.setColor(Color.WHITE);
			for (int i = 0; i < SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					int x = leftMargin + (i + 1) * (width + 10);
					int y = topMargin + (j + 1) * (height + 10);
					g2.drawRect(x, y, width, height);
				}
			}
			// 画出按步骤的路线
			// 前一个走过的格子的坐标位置
			int prevX = -1;
			int prevY = -1;
			for (int i = 0; i < path.size(); i++) {
				Integer[] xy = path.get(i);
				int x = leftMargin + (xy[0] + 1) * (width + 10);
				int y = topMargin + (xy[1] + 1) * (height + 10);
				g2.setColor(Color.GREEN);
				g2.fillRect(x, y, width, height);
				g2.setColor(Color.WHITE);
				g2.drawString("" + (i+1), x + 5, y + 20);
				// 从前一个格子到这个格子之间画一条箭头线
				g2.setColor(Color.RED);
				if(prevX > 0 && prevY > 0){
					int startX = prevX + width / 2;
					int startY = prevY + height / 2;
					int endX = x + width / 2;
					int endY = y + height / 2;
					g2.drawLine(startX, startY, endX, endY);
				}
				prevX = x;
				prevY = y;
			}
		}
		
	}
}
