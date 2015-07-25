package 老鼠走迷宫;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 找出所以路径
 * @author zhangyuchen
 *
 */
@SuppressWarnings("serial")
public class MouseMaze2 extends JFrame
{
	MyPanel panel = new MyPanel();
	
	public MouseMaze2()
	{
		super.add(panel);
		super.setBounds(100, 100, 500, 500);
		super.setVisible(true);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		visit(startI, startJ);
	}
	
	// 迷宫 2表示墙 0表示可走
	int maze[][] = {
			{2, 2, 2, 2, 2, 2, 2, 2, 2},  
            {2, 0, 0, 2, 0, 2, 0, 0, 2},//起点位置(1,1)   
            {2, 0, 2, 0, 0, 0, 0, 2, 2},  
            {2, 0, 0, 0, 2, 2, 0, 0, 2},  
            {2, 2, 2, 0, 0, 0, 2, 0, 2},  
            {2, 0, 0, 0, 2, 0, 2, 0, 2},  
            {2, 0, 2, 2, 0, 2, 0, 0, 2},  
            {2, 0, 0, 0, 0, 0, 0, 2, 2},//终点位置(7, 1)   
            {2, 2, 2, 2, 2, 2, 2, 2, 2},  
          };  
	private int startI = 1, startJ = 1; // 入口
	private int endI = 7, endJ = 1; // 出口
	int g_count = 0; // 用来统计路径的数量

	/**
	 * 打印迷宫
	 */
	public void refresh()
	{
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		panel.repaint();
	}

	/**
	 * 核心算法 递归求解
	 * 
	 * @return
	 */
	public boolean visit(int i, int j)
	{
		boolean success = false;
		// 该点已走过，设置为1
		maze[i][j] = 1;
		this.refresh();
		// 到达结尾 返回成功
		if (i == endI && j == endJ) {
			maze[i][j] = 3;
			g_count++;
			System.out.println("已经找到了第" + g_count + "条路径：");
			this.refresh();
			maze[i][j] = 0; // 如果没有这句就返回，则终点一直是3，走不通
			return true;
		}
		// 向右走
		if (maze[i][j + 1] == 0) {
			if (visit(i, j + 1)) {
				success = true;
			} else {
				maze[i][j + 1] = 2;
			}
			this.refresh();
		}
		// 向下走
		if (maze[i + 1][j] == 0) {
			if (visit(i + 1, j)) {
				success = true;
			} else {
				maze[i + 1][j] = 2;
			}
			this.refresh();
		}
		// 向左走
		if (maze[i][j - 1] == 0) {
			if (visit(i, j - 1)) {
				success = true;
			} else {
				maze[i][j - 1] = 2;
			}
			this.refresh();
		}
		// 向上走
		if (maze[i - 1][j] == 0) {
			if (visit(i - 1, j)) {
				success = true;
			} else {
				maze[i - 1][j] = 2;
			}
			this.refresh();
		}
		maze[i][j] = 0; // 恢复该点原状
		this.refresh();
		return success;
	}
	
	class MyPanel extends JPanel
	{

		@Override
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, getWidth(), getHeight());
			g2.setColor(Color.WHITE);
			int leftMargin = 50;
			int topMargin = 50;
			int width = 30;
			int height = 30;
			for (int i = 0; i < maze.length; i++) {
				for (int j = 0; j < maze[i].length; j++) {
					g2.setColor(Color.WHITE);
					// 计算x,y
					int x = leftMargin + (j + 1) * (width + 10);
					int y = topMargin + (i + 1) * (height + 10);
					if (maze[i][j] == 0) { // □
						g2.drawRect(x, y, width, height);
					} else if (maze[i][j] == 1) { // ★
						g2.setColor(Color.GREEN);
						g2.fillOval(x, y, width, height);
					} else if (maze[i][j] == 3) { // →
						g2.fillRoundRect(x, y, width, height, 10, 10);
					} else { // ■
						g2.fillRect(x, y, width, height);
					}
				}
			}
		}
		
	}
	

	public static void main(String[] args)
	{
		new MouseMaze2();
	}
}
