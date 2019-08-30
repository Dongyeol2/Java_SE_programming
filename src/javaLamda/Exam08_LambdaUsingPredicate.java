package javaLamda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/*
 * Predicate �� �Է� �Ű������� �ִ�. boolean ����
 * ���Ǵ� method�� testXXX()�� ���ȴ�.
 * �Է¸Ű��������� �����Ͽ� true, false ���� �����ؾ� �ϴ� ���.
 */
class Exam08_Student {
	private String sName;
	private int sKor;
	private int sEng;
	private int sMath;
	private String sSex;
	
	public Exam08_Student() {
		super();
	}

	public Exam08_Student(String sName, int sKor, int sEng, int sMath, String sSex) {
		super();
		this.sName = sName;
		this.sKor = sKor;
		this.sEng = sEng;
		this.sMath = sMath;
		this.sSex = sSex;
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

	public int getsMath() {
		return sMath;
	}

	public void setsMath(int sMath) {
		this.sMath = sMath;
	}

	public String getsSex() {
		return sSex;
	}

	public void setsSex(String sSex) {
		this.sSex = sSex;
	}
	
}

public class Exam08_LambdaUsingPredicate {
	private static List<Exam08_Student> students = 
			Arrays.asList(
					new Exam08_Student("ȫ�浿", 10,20,30,"����"),
					new Exam08_Student("�ڱ浿", 20,30,40,"����"),
					new Exam08_Student("��浿", 30,40,50,"����"),
					new Exam08_Student("�̱浿", 40,50,60,"����"),
					new Exam08_Student("�ֱ浿", 70,80,90,"����"));
	
	private static double avg(Predicate<Exam08_Student> predicate, 
			ToIntFunction<Exam08_Student> function) {
		int sum = 0;
		int cnt = 0;
		
		for(Exam08_Student s : students) {
				if(predicate.test(s)) {
					cnt++;
					sum += function.applyAsInt(s);
				}
			}
		
		return (double)sum/cnt;
	}
	
	
	public static void main(String[] args) {
		System.out.println(avg(t -> t.getsSex().equals("����"), t -> t.getsMath()));
		System.out.println(avg(t -> t.getsSex().equals("����"), t -> t.getsMath()));
		
	}
}
