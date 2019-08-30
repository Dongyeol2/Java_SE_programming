package javaStream;

import java.awt.TextField;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.FlowPane;

/*
 * java.util.stream package안에 우리가 사용할 수 있는 stream이 존재한다.
 * BaseStream으로부터 상속받아서 몇몇개의 Stream이 존재
 * IntStream => 해당 Stream안에 객체들어가 있는 경우
 * LongStream, DoubleStream도 존재한다.
 * 
 * 여러가지 형태의 다양한 source에서 Stream을 얻어낼 수 있다.
 */

public class Exam02_StreamSource {
	
	private static List<String> names =
			Arrays.asList("홍길동", "김길동", "최길동");
	
	private static int myArr[] = {10,20,30,40,50};
	
	public static void main(String[] args) {
		//List로부터 Stream을 생성할 수 있다.
		Stream<String> stream1 = names.stream();
		IntStream stream2 = Arrays.stream(myArr);
		System.out.println(stream2.sum());
		// 정수형 숫자 영역들 이용해서 Stream을 생성할 수 있다
		IntStream stream3 = IntStream.rangeClosed(1, 10);
		//파일로부터 Stream을 생성할 수 있다.
		Path path = Paths.get("asset/readme.txt");
		// File객체 (java.io)와 유사한 java.nio에 포함된 class
		// Path
		try {
			Stream<String> stream4 = Files.lines(path, Charset.forName("UTF-8"));
			stream4.forEach(t -> System.out.println(t));
			stream4.close();
		}catch (Exception e) {
			System.out.println(e);
		}
	}

}
