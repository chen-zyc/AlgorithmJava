package 老鼠走迷宫;

/**
 * 只找出一条路径就行
 * @author zhangyuchen
 *
 */
public class MouseMaze
{
	//迷宫   2表示墙 0表示可走
	int[][] maze = {
			{2, 2, 2, 2, 2, 2, 2},
            {2, 0, 0, 0, 0, 0, 2},    
            {2, 0, 2, 0, 2, 0, 2},    
            {2, 0, 0, 0, 0, 2, 2},    
            {2, 2, 0, 2, 0, 2, 2},    
            {2, 0, 0, 0, 0, 0, 2},
            {2, 2, 2, 2, 2, 2, 2}}; 
	private int startI = 1, startJ = 1;  // 入口    
    private int endI = 5, endJ = 5;  // 出口   
    boolean g_sucess = false;// 用来确保是否到达终点
    
    /**
     * 打印迷宫
     */
	public void printMaze()
	{
		int temp = -1;
		for (int i = 0; i < maze.length; i++)
		{
			for (int j = 0; j < maze[i].length; j++)
			{
				temp = maze[i][j];
				if (temp == 0) {
					System.out.print('□');
				} else if (temp == 1) {
					System.out.print('★');
				} else if (temp == 3) {
					System.out.print('→');
				} else {
					System.out.print('■');
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * 核心算法  递归求解
	 * @return
	 */
	public boolean visit(int i, int j)
	{
		// 该点已走过，设置为1
		maze[i][j] = 1;
		// 到达结尾 返回成功
		if (i == endI && j == endJ)
		{
			maze[i][j] = 3;
			g_sucess = true;
			return g_sucess;
		}
		// 向右走
		if ((!g_sucess) && (maze[i][j + 1] == 0))
		{
			// 如果不通，将这条路封死
			if (!visit(i, j + 1)) {
				maze[i][j + 1] = 2;
			}
		}
		// 向下走
		if ((!g_sucess) && (maze[i + 1][j] == 0))
		{
			// 如果不通，将这条路封死
			if (!visit(i + 1, j)) {
				maze[i + 1][j] = 2;
			}
		}
		// 向左走
		if ((!g_sucess) && (maze[i][j - 1] == 0))
		{
			// 如果不通，将这条路封死
			if (!visit(i, j - 1)) {
				maze[i][j - 1] = 2;
			}
		}
		// 向上走
		if ((!g_sucess) && (maze[i - 1][j] == 0))
		{
			visit(i - 1, j);
			// 如果不通，将这条路封死
			if (!visit(i - 1, j)) {
				maze[i - 1][j] = 2;
			}
		}
		if (!g_sucess)// 如果还没有找到出口则说明该路线是死路
		{
			maze[i][j] = 2;
		}
		return g_sucess;
	}
	
	public static void main(String[] args)
	{
		MouseMaze m = new MouseMaze();
		System.out.println("显示迷宫:");
		m.printMaze();
		
		if(m.visit(m.startI, m.startJ)){
			System.out.println("已找到出口，打印路径:");
			m.printMaze();
		}else{
			System.out.println("没有找到出口！");
		}
	}
}
