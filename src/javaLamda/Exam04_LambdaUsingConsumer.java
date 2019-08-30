package javaLamda;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.ObjIntConsumer;

public class Exam04_LambdaUsingConsumer {
	// method�� �ϳ� �����ϴµ� static���� ����.
	public static List<String> names = Arrays.asList("ȫ�浿", "��浿", "�ֱ浿","�ڱ浿");
	
	public static void printName(Consumer<String> consumer) {
		for(String name : names) {
			consumer.accept(name);
		}
	}
	
	//�Ϲ����� methodȣ���� ����ϴ� data�� ���ڷ� ���޵Ǵ� ����.
	// ���ٽ��� ����ϸ� method�� ȣ���� �� data�� �ƴ϶� ���� �ڵ带 �Ѱ��� �� �ִ�.
	// �Ϲ������� ���α׷��� ���� �̷��� �Լ��� �ٸ� �Լ��� ���ڷ� ����� �� �ִµ� �̷� �Լ���
	// first-classes function�̶�� �Ѵ�.
	public static void main(String[] args) {
		printName(t -> {System.out.println(t);});
		
		Consumer<String> consumer = t -> {
			System.out.println(t);
		};
		consumer.accept("�Ҹ����� �ƿ켺");
		
		BiConsumer<String, String> biConsumer = (a,b) -> {
			System.out.println(a+b);
		};
		biConsumer.accept("�Ҹ����� �ƿ켺", "�ƿ켺");
		
		IntConsumer intConsumer = i -> System.out.println(i);
		intConsumer.accept(100);
		
		ObjIntConsumer<String> objIntConsumer = (a,b) -> System.out.println(a+b);
		objIntConsumer.accept("�Ҹ����� �ƿ켺", 123);
	}
}
