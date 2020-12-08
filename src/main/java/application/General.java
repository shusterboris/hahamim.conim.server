package application;

import java.util.UUID;



public class General {
	public static String createUniqueFileName(String folder,String prefix, String suffix) {
		String result;
		do {
			if (prefix != null && !"".equals(prefix))
				result = prefix.concat("--").concat(UUID.randomUUID().toString()).concat(".").concat(suffix);
			else
				result = UUID.randomUUID().toString().concat(".").concat(suffix);
			
		}while(false);
		return result;
	}
}
