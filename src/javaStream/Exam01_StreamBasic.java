package javaStream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/*
 * Stream�� Java 8���� ������ �Ǿ���. �����ؾ� �Ұ� java.io�ȿ� �ִ� Stream���� �ٸ� ���̴�.
 * ���뵵 : �÷���ó��(List, Set, Map, Array)�� ���ؼ� ����� �ȴ�.
 *                   �÷��Ǿ��� �����͸� �ݺ���Ű�� �ݺ����� ������ �ϴµ� stream
 *                   ���� ��� ArrayList�ȿ� �л� ��ü�� 5�� ������ �� 5���� �ϳ��� �������� ������ ����
 *                   => �̷��� ������ �����͸� ���ٽ��� �̿��ؼ� ó���� �� �ִ�.
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

	private static List<String> myBuddy = Arrays.asList("ȫ�浿", "��浿", "�ֱ浿", "�Ż��Ӵ�");
	
	private static List<Exam01_Student> students =
			Arrays.asList(
					new Exam01_Student("ȫ�浿", 10, 20),
					new Exam01_Student("ȫ�浿", 10, 20),
					new Exam01_Student("ȫ�浿", 10, 20),
					new Exam01_Student("ȫ�浿", 10, 20));
	
	public static void main(String[] args) {	
		// ��� �̸��� ���
		// ��� 1. �Ϲ� for�� (ö�ڸ� ���)�� �̿��ؼ� ó��.
		for(int i = 0; i < myBuddy.size(); i++) {
			System.out.println(myBuddy.get(i));
		}
		System.out.println("-----------------------------------");
		
		// ��� �̸��� ���
		// ��� 2. ö�ڸ� �̿��� �ݺ��� ���ϱ� ���� Iterator�� ���
		Iterator<String> iter = myBuddy.iterator();
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
		System.out.println("-----------------------------------");
		
		// ��� �̸��� ���
		// �ݺ��ڰ� �ʿ䰡 ����. ���� �ݺ��ڸ� �̿�
		// ����ó���� ����
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
