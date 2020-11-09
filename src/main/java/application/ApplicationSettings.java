package application;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;

public class ApplicationSettings {
	@Value("${app.host.addr}")
	private static String hostServer;

	@Value("${app.host.port}")
	private static String hostPort;

	private static Properties props;
	
	public static String getProperty(String name) {
		if (props == null) 
			if (! readProperties())
				return "";
		if (props == null)
			return "";
		if (! props.containsKey(name))
			return "";
		else {
			String p = props.getProperty(name);
			return (p == null) ? "" : p;
		}
	}
	
	public static final String getHost() {
		return hostServer.concat(":").concat(hostPort);
	}
	
	private static boolean readProperties() {
		String propFileName = "application.properties";
		props = new Properties();
		try {
			InputStream inputStream = ApplicationSettings.class.getClassLoader().getResourceAsStream(propFileName);
	
			if (inputStream != null) {
				props.load(inputStream);
				return true;
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
		}catch (Exception e) {
			System.out.println("FATAL: Can not read properties");
			return false;
		}
	}
}
