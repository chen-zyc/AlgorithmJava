package 生命游戏;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class LifeGame extends JFrame
{
	public final int WIDTH = 10;
	public final int HEIGHT = 10;	
	// 两个数组，另一个用来记录数组的变化，当把所有的变化都记录完后，在复制到world数组中
	int[][]	world = new int[WIDTH][HEIGHT];
	int[][] world2 = new int[WIDTH][HEIGHT];
	private MyPanel panel = new MyPanel();
	private long time = 0; // 时间
	private int aliveCount = 0; // 存活数
	
	public static void main(String[] args) 
	{
		new LifeGame().init();
	}
	
	public void init()
	{
		super.setBounds(100, 100, 550, 550);
		super.add(panel);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setVisible(true);
		
		// 随机生成10个生命
		Random random = new Random(System.currentTimeMillis());
		for(int i=0; i<10; i++){
			int x = random.nextInt(WIDTH);
			int y = random.nextInt(HEIGHT);
			world[x][y] = 1;
			world2[x][y] = 1;
		}
		this.start();
	}
	
	private void start()
	{
		while(true)
		{
			aliveCount = 0;
			for (int i = 0, m = world.length; i < m; i++) {
				for (int j = 0, n = world[i].length; j < n; j++) {
					switch(this.neighbors(i, j))
					{
						case 2:
							world2[i][j] = 1;
							aliveCount++;
							break;
						case 3:
							break;
						default:
							world2[i][j] = 0;
							break;
					}
				}
			}
			this.copyWorld();
			this.refresh();
		}
	}
	
	/**
	 * 将world2中的数据复制到word中
	 */
	private void copyWorld()
	{
		for (int i = 0; i < world2.length; i++) {
			System.arraycopy(world2[i], 0, world[i], 0, world2[i].length);
		}
	}
	
	/**
	 * row行col列周围的邻居数
	 * @param row
	 * @param col
	 * @return
	 */
	private int neighbors(int row, int col)
	{
		int neighborCount = 0;
		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = col - 1; j <= col + 1; j++) {
				if (i < 0 || i >= world.length || j < 0 || j >= world[0].length) {
					continue;
				}
				if(world[i][j] == 1){
					neighborCount ++;
				}
			}
		}
		if(world[row][col] == 1){
			neighborCount --;
		}
		return neighborCount;
	}
	
	public void refresh()
	{
		try {
			Thread.sleep(1000);
			time ++;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		panel.repaint();
	}
	
	class MyPanel extends JPanel
	{

		@Override
		public void paint(Graphics g) 
		{
			Graphics2D g2 = (Graphics2D) g;
			// 背景
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, getWidth(), getHeight());
			int leftMargin = 30;
			int topMargin = 30;
			int width = 30;
			int height = 30;
			g2.setColor(Color.WHITE);
			// 时间 + 存活数
			g2.setFont(new Font("微软雅黑", Font.ITALIC|Font.BOLD, 20));
			g2.drawString("time:" + time + ", alive:" + aliveCount, 30, 30);
			// 世界
			for (int i = 0; i < world.length; i++) {
				for (int j = 0; j < world[i].length; j++) {
					int x = leftMargin + (i + 1) * (width + 10);
					int y = topMargin + (j + 1) * (height + 10);
					// 活细胞
					if (world[i][j] == 1) {
						g2.setColor(Color.GREEN);
						g2.fillOval(x, y, width, height);
						g2.setColor(Color.WHITE);
					} else {
						g2.fillOval(x, y, width, height);
					}
				}
			}
		}
		
	}
	
}
