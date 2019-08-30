package javaLamda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/*
 * Predicate 는 입력 매개변수가 있다. boolean 리턴
 * 사용되는 method는 testXXX()가 사용된다.
 * 입력매개변수값을 조사하여 true, false 값을 리턴해야 하는 경우.
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
					new Exam08_Student("홍길동", 10,20,30,"남자"),
					new Exam08_Student("박길동", 20,30,40,"남자"),
					new Exam08_Student("김길동", 30,40,50,"남자"),
					new Exam08_Student("이길동", 40,50,60,"여자"),
					new Exam08_Student("최길동", 70,80,90,"여자"));
	
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
		System.out.println(avg(t -> t.getsSex().equals("남자"), t -> t.getsMath()));
		System.out.println(avg(t -> t.getsSex().equals("여자"), t -> t.getsMath()));
		
	}
}
