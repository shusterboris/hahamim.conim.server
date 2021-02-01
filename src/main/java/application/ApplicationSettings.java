package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class ApplicationSettings {
	private static final String fsepar = FileSystems.getDefault().getSeparator();
	public final static String appPath = getAppFolder();
	public final static String imgStorePath = getAppFolder().concat("imgStore").concat(fsepar);
	public static final Logger log = LoggerFactory.getLogger(Starter.class);
	private static String defaultLanguage = "RU";

	public static String getDefaultLanguage() {
		return defaultLanguage;
	}

	public static void setDefaultLanguage(String choosenLanguage) {
		ApplicationSettings.defaultLanguage = choosenLanguage;
	}

	@Value("${app.host.addr}")
	private static String hostServer;

	@Value("${app.host.port}")
	private static String hostPort;

	private static Properties props;

	public static String getProperty(String name) {
		if (props == null)
			try {
				if (!readProperties())
					return "";
			} catch (Exception e) {
				return "";
			}
		if (props == null)
			return "";
		if (!props.containsKey(name))
			return "";
		else {
			String p = props.getProperty(name);
			return (p == null) ? "" : p;
		}
	}

	public static final String getHost() {
		return hostServer.concat(":").concat(hostPort);
	}

	private static boolean readProperties() throws IOException {
		String propFileName = "application.properties";
		props = new Properties();
		InputStream inputStream = null;
		try {
			URL propUrl = ApplicationSettings.class.getClassLoader().getResource(propFileName);
			inputStream = propUrl.openStream();
			if (inputStream != null) {
				props.load(new InputStreamReader(
						ApplicationSettings.class.getClassLoader().getResourceAsStream(propFileName), "UTF-8"));
				inputStream.close();
				return true;
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
		} catch (Exception e) {
			if (inputStream != null)
				inputStream.close();
			System.out.println("FATAL: Can not read properties");
			return false;
		}
	}

	public static String getAppFolder() {
		URL fileURL = ApplicationSettings.class.getClassLoader().getResource("");
		try {
			URI uri;
			if (fileURL.toString().startsWith("jar")) {
				String urlStr = fileURL.toString();
				int illegalCharPos = urlStr.indexOf("!");
				urlStr = urlStr.substring(4, illegalCharPos);
				uri = new URI(urlStr);
			} else
				uri = fileURL.toURI();
			Path path = Paths.get(uri);
			return path.getParent().toString().concat(fsepar);
		} catch (URISyntaxException e) {
			return "";
		}
	}
}
