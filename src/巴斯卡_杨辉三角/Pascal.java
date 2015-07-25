package 巴斯卡_杨辉三角;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Pascal extends JFrame
{
	public Pascal()
	{
		setBackground(Color.white);
		setTitle("巴斯卡三角形");
		setSize(520, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private long combi(int n, int r)
	{
		int i;
		long p = 1;
		for (i = 1; i <= r; i++)
			p = p * (n - i + 1) / i;
		return p;
	}

	public void paint(Graphics g)
	{
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.setFont(new Font("华文细黑", Font.BOLD, 14));
		final int N = 12;
		int n, r;
		for (n = 0; n <= N; n++)
		{
			for (r = 0; r <= n; r++)
				g.drawString(" " + combi(n, r), (N - n) * 20 + r * 40, n * 20 + 50);
		}
	}

	public static void main(String args[])
	{
		new Pascal();
	}
}
