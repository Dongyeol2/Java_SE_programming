package javaStream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Exam03_StreamPipeline {
	
	static class Exam03_Employee {
		private String eName;
		private int eAge;
		private String eDept;
		private String eGender;
		private int ePay;
		
		public Exam03_Employee() {
			super();
		}

		public Exam03_Employee(String eName, int eAge, String eDept, String eGender, int ePay) {
			super();
			this.eName = eName;
			this.eAge = eAge;
			this.eDept = eDept;
			this.eGender = eGender;
			this.ePay = ePay;
		}

		public String geteName() {
			return eName;
		}

		public void seteName(String eName) {
			this.eName = eName;
		}

		public int geteAge() {
			return eAge;
		}

		public void seteAge(int eAge) {
			this.eAge = eAge;
		}

		public String geteDept() {
			return eDept;
		}

		public void seteDept(String eDept) {
			this.eDept = eDept;
		}

		public String geteGender() {
			return eGender;
		}

		public void seteGender(String eGender) {
			this.eGender = eGender;
		}

		public int getePay() {
			return ePay;
		}

		public void setePay(int ePay) {
			this.ePay = ePay;
		}	
	}
	
	private static List<Exam03_Employee> employees =
			Arrays.asList(
					new Exam03_Employee("홍길동", 20, "IT", "남자", 2000),
					new Exam03_Employee("홍길동", 20, "IT", "남자", 2000),
					new Exam03_Employee("홍길동", 20, "IT", "남자", 2000),
					new Exam03_Employee("홍길동", 20, "IT", "남자", 2000),
					new Exam03_Employee("홍길동", 20, "IT", "남자", 2000),
					new Exam03_Employee("홍길동", 20, "IT", "남자", 2000),
					new Exam03_Employee("홍길동", 20, "IT", "남자", 2000),
					new Exam03_Employee("홍길동", 20, "IT", "남자", 2000));
	
	public static void main(String[] args) {
		// 부서가 IT인 사람들 중 남자에 대한 연봉 평균을 구해보자
		Stream<Exam03_Employee> stream = employees.stream();
		// stream의 중간처리와 최종처리를 이용해서 원하는 작업을 해보자
		// filter method는 결과값을 가지고 있는 stream을 리턴
		double avg = stream.filter(t -> t.geteDept().equals("IT"))
					.filter(t -> t.geteGender().equals("남자"))
					.mapToInt(t -> t.getePay())
					.average().getAsDouble();
		System.out.println("IT부서의 남자평균 연봉 : " + avg);
		
		// 나이가 35 이상인 직원 중 남자 직원의 이름을 출력
		stream.filter(t -> t.geteAge() >= 35)
					.filter(t ->t.geteGender().equals("남자"))
					.forEach(t -> System.out.println(t.geteName()));
		
		// 반복
		// forEach()를 이용하면 스트림안의 요소를 반복할 수 있다.
		// forEach()는 최종 처리 함수
		// 중간 처리 함수로 반복처리하는 함수가 하나 더 제공
		employees.stream()
						.peek(t -> System.out.println(t.geteName()))
						.mapToInt(t -> t.getePay())
						.forEach(t -> System.out.println(t));
		//확인용 최종 함수 처리
		// 50살 이상인 사람만 추출해서 이름 출력
		boolean result = employees.stream()
						.filter(t -> (t.geteAge() >= 50))
						.allMatch(t -> (t.geteAge() > 55));
		System.out.println(result);
		
		// 최종 확인용 함수로 forEach를 많이 사용했는데
		// forEach말고 collect()를 이용해보자
		// 나이가 50살 이상인 사람들의 연봉을 구해서
		// List<Integer> 형태의 ArrayList에 저장해 보자
		List<Integer> list = employees.stream()
							.filter(t -> (t.geteAge() >= 50))
							.map(t -> t.getePay())
							.collect(Collectors.toList());
							//.collect(Collectors.toCollection(HashSet :: new));
		System.out.println(list);
		// Set으로도 저장할 수 있고, Map으로도 저장할 수 있다.
		
		
	}
}
