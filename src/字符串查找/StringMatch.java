package 字符串查找;

public class StringMatch {
	private int[]	skip;
	private int		p;
	private String	str;
	private String	key;

	public StringMatch(String key) {
		skip = new int[256];
		this.key = key;

		for (int k = 0; k <= 255; k++)
			skip[k] = key.length();
		for (int k = 0; k < key.length() - 1; k++)
			skip[key.charAt(k)] = key.length() - k - 1;
	}

	public void search(String str) {
		this.str = str;
		p = search(key.length() - 1, str, key);
	}

	private int search(int p, String input, String key) {
		while (p < input.length()) {
			String tmp = input.substring(p - key.length() + 1, p + 1);

			if (tmp.equals(key)) // 比较两字串是否相同
				return p - key.length() + 1;
			p += skip[input.charAt(p)];
		}

		return -1;
	}

	public boolean hasNext() {
		return (p != -1);
	}

	public String next() {
		String tmp = str.substring(p);
		p = search(p + key.length() + 1, str, key);
		return tmp;
	}

	public static void main(String[] args) {
		String str = "";
		String key = "";

		StringMatch strMatch = new StringMatch(key);
		strMatch.search(str);

		while (strMatch.hasNext()) {
			System.out.println(strMatch.next());
		}
	}
}
