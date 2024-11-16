package me.joepeterson.Timeless.engine.util;

import java.io.InputStream;
import java.util.Scanner;

public class Files {
	public static String loadResource(String path) throws Exception {
		try(InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path)) {
			if(is == null) throw new Exception("Couldn't read " + path + " from resources");

			StringBuilder builder = new StringBuilder();

			try (Scanner scanner = new Scanner(is)) {
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					builder.append(line);
					builder.append("\n");
				}
			}

			return builder.toString();
		}

	}

}
