package 字符串查找;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


@SuppressWarnings("serial")
public class KmpInSwing extends JFrame
{
	private MyPanel panel = new MyPanel();
	public static void main(String[] args) {
		new KmpInSwing();
	}
	public KmpInSwing() 
	{
		super.setBounds(100, 100, 550, 550);
		panel.setPreferredSize(new Dimension(10000, 500));
		super.add(new JScrollPane(panel));
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setVisible(true);
		
		text = "0000002000000200000020000002000000200000020000001";
		pattern = "0000001";
		List<Integer> result = KMPMatcher();
		System.out.println("match:" + Arrays.toString(result.toArray()));
	}
	public void refresh()
	{
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		panel.repaint();
	}
	
	/*-------------------- 算法相关 ----------------------*/
	public String	text;
	public String	pattern;
	int i = 0; // 目标串索引
	int j = 0; // 模式串索引
	
	public List<Integer> KMPMatcher() 
	{
		List<Integer> result = new ArrayList<Integer>();
		
		int n = text.length();
		int m = pattern.length();
		// 模式串比目标串长的话就不要比较了
		if (n < 1 || m < 1 || n < m) {
			return result;
		}
		
		int[] next = getNext2(); // 计算next数组
		while (i < n) {
			if (j == -1 || text.charAt(i) == pattern.charAt(j)) {
				i++;
				j++;
			} else {
				j = next[j]; // 消除了指针i的回溯
			}
			// 如果目标串剩下的长度比模式串剩下的长度短，剩下的就不要匹配了
			if (n - i < m - j) {
				break;
			}
			refresh();
			// 模式串匹配完了，匹配位置在i之前strlen(p)的位置
			if (j == m) {
				result.add(i - m);
				// 为了匹配所有的位置
				i = i - m + 1;
				j = 0;
			}
		}
		return result;
	}

	private int[] getNext() 
	{
		int length = pattern.length();
		int[] next = new int[length];

		next[0] = -1;
		int j = 0;
		int k = -1;
		while (j < length - 1) {
			// 匹配的情况下,p[j]==p[k]
			if (k == -1 || pattern.charAt(j) == pattern.charAt(k)) {
				j++;
				k++;
				next[j] = k;
			} else {// p[j]!=p[k]
				k = next[k];
			}
		}
		return next;
	}
	
	private int[] getNext2() {
		char[] p = pattern.toCharArray();
		int length = p.length;
		int[] next = new int[length];

		next[0] = -1;
		// j相当于目标串的索引，k相当于模式串的索引
		int j = 0;
		int k = -1;
		//这里注意，i==0的时候实际上求的是next[1]的值，以此类推
		while (j < length - 1) {
			// 匹配的情况下,p[j]==p[k]
			if (k == -1 || p[j] == p[k]) {
				j++;
				k++;
				next[j] = p[j] != p[k] ? k : next[k];
			} else {// p[j]!=p[k]
				//模式串的位置调整为next[k]
				k = next[k];
			}
		}
		return next;
	}
	
	class MyPanel extends JPanel
	{

		@Override
		public void paint(Graphics g) 
		{
			Graphics2D g2 = (Graphics2D) g;
			// 背景
			g2.setColor(Color.BLACK);
			g2.setFont(new Font("YaHei Consolas Hybrid", Font.BOLD | Font.ITALIC, 24));
			g2.fillRect(0, 0, getWidth(), getHeight());
			g2.setColor(Color.WHITE);
			int leftMargin = 50;
			int topMargin = 50;
			int spacing = 20; // 字符间距离
			// 一个字符的宽度
			int w = (int) g2.getFont().getStringBounds("0", g2.getFontRenderContext()).getWidth();
			for (int m = 0, n = text.length(); m < n; m++) {
				if (m < i) {
					g2.setColor(Color.GREEN);
				} else if (m == i) {
					g2.setColor(Color.RED);
				} else {
					g2.setColor(Color.WHITE);
				}
				g2.drawString("" + text.charAt(m), 
						leftMargin + m * (w + spacing), topMargin);
			}
			// 计算模式串的位置
			topMargin += 30;
			leftMargin += (i - j) * (spacing + w);
			
			for (int m = 0, n = pattern.length(); m < n; m++) {
				if (m < j) {
					g2.setColor(Color.GREEN);
				} else if (m == j) {
					g2.setColor(Color.RED);
				} else {
					g2.setColor(Color.WHITE);
				}
				g2.drawString("" + pattern.charAt(m), 
						leftMargin + m * (w + spacing), topMargin);
			}
			
			g2.drawString("目标串的位置:" + i + ", 模式串的位置:" + j, 100, 130);
		}
		
	}
}
