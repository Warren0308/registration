package net.codejava.menu;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.core.io.ClassPathResource;

public class Demo {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Hi");
		System.out.println(testResourceFile());
	}
	public static String testResourceFile(){
		String text = null;
		try {
			File resource = new ClassPathResource("static/Test.txt").getFile();
			text = new String(Files.readAllBytes(resource.toPath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}

}
