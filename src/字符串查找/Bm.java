package 字符串查找;

public class Bm 
{
	public static void main(String[] args) {
		String str = "beautiful, but men seldom realized it when caught by her "
				+ "charmas the Tarleton twins were. In her face were too sharply "
				+ "blended the delicate features of her mother,a Coast aristocrat "
				+ "of French descent, and the heavy ones of her florid Irish father. "
				+ "But it was anarresting face, pointed of chin, square of jaw. "
				+ "Her eyes were pale green without a touch of hazel,starred with "
				+ "bristly black lashes and slightly tilted at the ends. Above "
				+ "them, her thick black browsslanted upward, cutting a startling "
				+ "oblique line in her Seated with Stuart and Brent Tarleton in "
				+ "the cool shade of the porch of Tara, her father’splantation, "
				+ "that bright April afternoon of 1861, she made a pretty picture."
				+ " Her new green flowered-muslin dress spread its twelve yards "
				+ "of billowing material over her hoops and exactly matched "
				+ "theflat-heeled green morocco slippers her father had recently "
				+ "brought her from Atlanta. The dress set off to perfection the "
				+ "seventeen-inch waist, the smallest in three counties, and the "
				+ "tightly fittingbasque showed breasts well matured for her "
				+ "sixteen years. But for all the modesty of her spreadingskirts, "
				+ "the demureness of hair netted smoothly into a chignon and the "
				+ "quietness of small whitehands folded in her lap, her true self "
				+ "was poorly concealed. The green eyes in the carefully sweetface "
				+ "were turbulent, willful, lusty with life, distinctly at variance "
				+ "with her decorous demeanor. Hermanners had been imposed upon "
				+ "her by her mother’s gentle admonitions and the sterner "
				+ "disciplineof her mammy; her eyes were her own.On either "
				+ "side of her, the twins lounged easily in their chairs, "
				+ "squinting at the sunlight throughtall mint-garnished "
				+ "glasses as they laughed and talked, their long legs, "
				+ "booted to the knee and thickwith saddle muscles, crossed "
				+ "negligently. Nineteen years old, six feet two inches tall, "
				+ "long of boneand hard of muscle, with sunburned faces and deep "
				+ "auburn hair, their eyes merry and arrogant,their bodies clothed "
				+ "in identical blue coats and mustard-colored breeches, they "
				+ "were as much alikeas two bolls of cotton.Outside, the late "
				+ "afternoon sun slanted down in the yard, throwing into gleaming "
				+ "brightness thedogwood trees that were solid masses of white "
				+ "blossoms against the background of new green. Thetwins’ "
				+ "horses were hitched in the driveway, big animals, red as their "
				+ "masters’ hair; and around thehorses’ legs quarreled the pack "
				+ "of lean, nervous possum hounds that accompanied Stuart and "
				+ "Brentwherever they went. A little aloof, as became an aristocrat, "
				+ "lay a black-spotted carriage dog,muzzle on paws, patiently waiting "
				+ "for the boys to go home to supper.";
	}
	// 匹配算法
	public static int indexOf(char[] ocean, char[] key) 
	{
		if (key.length == 0) {
			return 0;
		}
		int charTable[] = makeCharTable(key);
		int offsetTable[] = makeOffsetTable(key);
		for (int i = key.length - 1, j; i < ocean.length;) {
			for (j = key.length - 1; key[j] == ocean[i]; --i, --j) {
				if (j == 0) {
					return i;
				}
			}
			// offsetTable根据已经匹配的好后缀的长度取数据 charTable根据坏字符取数据
			i += Math.max(offsetTable[key.length - 1 - j],
							charTable[ocean[i]]);
		}
		return -1;
	}

	/**
	 * 坏字符规则表
	 * @param needle 模式串
	 * @return 各个字符在模式串中后边有几个字符
	 */
	private static int[] makeCharTable(char[] needle) 
	{
		final int ALPHABET_SIZE = 256;
		// table[i]表示i字符在模式串中右边还有几个字符
		int[] skip = new int[ALPHABET_SIZE];
		// 初始化赋值
		// 假定不匹配的那个字符在模式串中没有出现过，此时应该跳过整个模式串的长度
		for (int i = 0; i < skip.length; ++i) {
			skip[i] = needle.length;
		}
		// 重新赋值
		// needle.length - 1 - i是当前位置右边还有几个字符
		for (int i = 0; i < needle.length - 1; ++i) {
			skip[needle[i]] = needle.length - 1 - i;
		}
		return skip;
	}

	/**
	 * 好后缀规则表 ， 根据好后缀长度取需要移动的位置 
	 * 由于规则b的值>=规则a的值（始终） 先根据规则b进行赋值 再根据好后缀规则a重新赋值
	 */
	private static int[] makeOffsetTable(char[] needle) {
		int[] table = new int[needle.length];
		int lastPrefixPosition = needle.length;
		// 根据好后缀规则b)初始化好后缀表
		for (int i = needle.length - 1; i >= 0; --i) {
			if (isPrefix(needle, i + 1)) {
				lastPrefixPosition = i + 1;
			}
			table[needle.length - 1 - i] =
					lastPrefixPosition - i + needle.length - 1;
		}
		// 再根据好后缀规则a)重新赋值
		for (int i = 0; i < needle.length - 1; ++i) {
			int slen = suffixLength(needle, i);
			table[slen] = needle.length - 1 - i + slen;
		}
		return table;
	}

	/**
	 * 判断从p开始到结束是否既是后缀同时是前缀 
	 * abacactttabac 例如p=9（从左到右，字符a处）结果是true（abac == abac）
	 */
	private static boolean isPrefix(char[] needle, int p) {
		for (int i = p, j = 0; i < needle.length; ++i, ++j) {
			if (needle[i] != needle[j]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 获取从p到0位置 的最大的是后缀的字串大小 ababactttabac 例如当p=5（c处），结果是abac的大小4
	 */
	private static int suffixLength(char[] needle, int p) {
		int len = 0;
		for (int i = p, j = needle.length - 1; i >= 0 && needle[i] == needle[j]; --i, --j) {
			len += 1;
		}
		return len;
	}
}
