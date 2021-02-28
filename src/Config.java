
import java.util.Properties;

/**
 * 
 * @author Cole Burnham - to handle reading from the config source:
 *         https://www.opencodez.com/java/read-config-file-in-java.htm
 */
public class Config {
	Properties configFile;

	public Config() {
		configFile = new java.util.Properties();
		try {
			configFile.load(this.getClass().getClassLoader().getResourceAsStream("config.txt"));
			System.out.println(this.getClass().getClassLoader().getResourceAsStream("config.txt"));
		} catch (Exception eta) {
			eta.printStackTrace();
		}
	}

	public String getProperty(String key) {
		String value = this.configFile.getProperty(key);
		return value;
	}

	public String setProperty(String property, String key) {
		String value = (String) this.configFile.setProperty(property, key);
		return value;
	}
}
