package javaStream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/*
 * Stream은 Java 8에서 도입이 되었다. 주의해야 할건 java.io안에 있는 Stream과는 다른 것이다.
 * 사용용도 : 컬렉션처리(List, Set, Map, Array)를 위해서 사용이 된다.
 *                   컬렉션안의 데이터를 반복시키는 반복자의 역할을 하는데 stream
 *                   예를 들어 ArrayList안에 학생 객체가 5개 있으면 그 5개를 하나씩 가져오는 역할을 수행
 *                   => 이렇게 가져온 데이터를 람다식을 이용해서 처리할 수 있다.
 * 
 */
class Exam01_Student {
	private String sName;
	private int sKor;
	private int sEng;
	
	public Exam01_Student() {
		super();
	}

	public Exam01_Student(String sName, int sKor, int sEng) {
		super();
		this.sName = sName;
		this.sKor = sKor;
		this.sEng = sEng;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public int getsKor() {
		return sKor;
	}

	public void setsKor(int sKor) {
		this.sKor = sKor;
	}

	public int getsEng() {
		return sEng;
	}

	public void setsEng(int sEng) {
		this.sEng = sEng;
	}
	
}



public class Exam01_StreamBasic {

	private static List<String> myBuddy = Arrays.asList("홍길동", "김길동", "최길동", "신사임당");
	
	private static List<Exam01_Student> students =
			Arrays.asList(
					new Exam01_Student("홍길동", 10, 20),
					new Exam01_Student("홍길동", 10, 20),
					new Exam01_Student("홍길동", 10, 20),
					new Exam01_Student("홍길동", 10, 20));
	
	public static void main(String[] args) {	
		// 사람 이름을 출력
		// 방법 1. 일반 for문 (철자를 사용)을 이용해서 처리.
		for(int i = 0; i < myBuddy.size(); i++) {
			System.out.println(myBuddy.get(i));
		}
		System.out.println("-----------------------------------");
		
		// 사람 이름을 출력
		// 방법 2. 철자를 이용한 반복을 피하기 위해 Iterator를 사용
		Iterator<String> iter = myBuddy.iterator();
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
		System.out.println("-----------------------------------");
		
		// 사람 이름을 출력
		// 반복자가 필요가 없다. 내부 반복자를 이용
		// 병렬처리가 가능
		Consumer<String> consumer = t -> {
			System.out.println(t + ", " + Thread.currentThread().getName());
		};
		
		Stream<String> stream = myBuddy.parallelStream();
		stream.forEach(consumer);
		System.out.println("-----------------------------------");
		
		Stream<Exam01_Student> studentStream = students.stream();
		double avg = studentStream.mapToInt(t -> t.getsKor()).average().getAsDouble();
		System.out.println(avg);
	}

}
