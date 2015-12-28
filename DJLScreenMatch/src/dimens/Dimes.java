package dimens;

/**
 * @author DJL E-mail:
 * @date 2015-8-28 ÏÂÎç3:57:31
 * @version 1.0
 * @parameter
 * @since
 */
public class Dimes {
	// public int lenth;
	// public String unit;
	// public final static String[] units = { "dip", "dp", "sp", "px" };
	private String name;
	private String value;
	private final static String[] wTag = { "width", "left", "right",
			"horizontal" };
	private final static String[] hTag = { "height", "top", "bottom",
			"vertical" };
	/**
	 * 1=w,2=h,0=?
	 */
	public int tag;

	public Dimes(String name, String value) {
		this.setName(name);
		this.setValue(value);
	}

	public Dimes() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		for (int i = 0; i < wTag.length; i++) {
			if (name.toLowerCase().contains(wTag[i])) {
				tag = 1;
			}
		}
		for (int i = 0; i < hTag.length; i++) {
			if (name.toLowerCase().contains(hTag[i])) {
				tag = 2;
			}
		}
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Dimes [name=" + getName() + ", value=" + getValue() + "]";
	}
}
