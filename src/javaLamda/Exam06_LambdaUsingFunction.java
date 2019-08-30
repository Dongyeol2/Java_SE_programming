package javaLamda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

class Exam06_Student {
	private String sName;
	private int sKor;
	private int sEng;
	private int sMath;
	
	public Exam06_Student() {
		super();
	}

	public Exam06_Student(String sName, int sKor, int sEng, int sMath) {
		super();
		this.sName = sName;
		this.sKor = sKor;
		this.sEng = sEng;
		this.sMath = sMath;
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

	
	
	
	
}

public class Exam06_LambdaUsingFunction {
	
	private static List<Exam06_Student> students = 
			Arrays.asList(
					new Exam06_Student("홍길동", 10, 20,30),
					new Exam06_Student("김길동", 30, 40,50),
					new Exam06_Student("박길동", 60, 70, 80));
	
	private static void printName(Function<Exam06_Student, String> function) {
		for(Exam06_Student s : students) {
			System.out.println(function.apply(s));
		}
	}
	 
	private static double printAvg(Function<Exam06_Student, Integer> function) {
		int sum = 0;
		for(Exam06_Student s: students) {
			sum += function.apply(s);
		} 
		return  (double)sum / students.size();

	}
	
	public static void main(String[] args) {
		printName(t -> {return t.getsName(); });
		System.out.printf("국어 : %.2f \n",printAvg(t -> {return t.getsKor();}));
		System.out.printf("영어 : %.2f \n",printAvg(t -> {return t.getsEng();}));
		System.out.printf("수학 : %.2f \n",printAvg(t -> {return t.getsMath();}));
		
		
	}
	
}
