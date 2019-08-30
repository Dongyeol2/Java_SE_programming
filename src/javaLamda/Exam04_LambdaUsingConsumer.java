package javaLamda;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.ObjIntConsumer;

public class Exam04_LambdaUsingConsumer {
	// method를 하난 정의하는데 static으로 정의.
	public static List<String> names = Arrays.asList("홍길동", "김길동", "최길동","박길동");
	
	public static void printName(Consumer<String> consumer) {
		for(String name : names) {
			consumer.accept(name);
		}
	}
	
	//일반적인 method호출은 사용하는 data가 인자로 전달되는 형태.
	// 람다식을 사용하면 method를 호출할 때 data가 아니라 실행 코드를 넘겨줄 수 있다.
	// 일반적으로 프로그래밍 언어에서 이렇게 함수를 다른 함수의 인자로 사용할 수 있는데 이런 함수를
	// first-classes function이라고 한다.
	public static void main(String[] args) {
		printName(t -> {System.out.println(t);});
		
		Consumer<String> consumer = t -> {
			System.out.println(t);
		};
		consumer.accept("소리없는 아우성");
		
		BiConsumer<String, String> biConsumer = (a,b) -> {
			System.out.println(a+b);
		};
		biConsumer.accept("소리없는 아우성", "아우성");
		
		IntConsumer intConsumer = i -> System.out.println(i);
		intConsumer.accept(100);
		
		ObjIntConsumer<String> objIntConsumer = (a,b) -> System.out.println(a+b);
		objIntConsumer.accept("소리없는 아우성", 123);
	}
}
