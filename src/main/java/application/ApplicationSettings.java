package application;

import org.springframework.beans.factory.annotation.Value;

public class ApplicationSettings {
	@Value("${app.host.addr}")
	private static String hostServer;

	@Value("${app.host.port}")
	private static String hostPort;

	public static final String getHost() {
		return hostServer.concat(":").concat(hostPort);
	}
}
