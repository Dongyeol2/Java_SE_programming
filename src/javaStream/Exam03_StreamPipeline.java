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
					new Exam03_Employee("ȫ�浿", 20, "IT", "����", 2000),
					new Exam03_Employee("ȫ�浿", 20, "IT", "����", 2000),
					new Exam03_Employee("ȫ�浿", 20, "IT", "����", 2000),
					new Exam03_Employee("ȫ�浿", 20, "IT", "����", 2000),
					new Exam03_Employee("ȫ�浿", 20, "IT", "����", 2000),
					new Exam03_Employee("ȫ�浿", 20, "IT", "����", 2000),
					new Exam03_Employee("ȫ�浿", 20, "IT", "����", 2000),
					new Exam03_Employee("ȫ�浿", 20, "IT", "����", 2000));
	
	public static void main(String[] args) {
		// �μ��� IT�� ����� �� ���ڿ� ���� ���� ����� ���غ���
		Stream<Exam03_Employee> stream = employees.stream();
		// stream�� �߰�ó���� ����ó���� �̿��ؼ� ���ϴ� �۾��� �غ���
		// filter method�� ������� ������ �ִ� stream�� ����
		double avg = stream.filter(t -> t.geteDept().equals("IT"))
					.filter(t -> t.geteGender().equals("����"))
					.mapToInt(t -> t.getePay())
					.average().getAsDouble();
		System.out.println("IT�μ��� ������� ���� : " + avg);
		
		// ���̰� 35 �̻��� ���� �� ���� ������ �̸��� ���
		stream.filter(t -> t.geteAge() >= 35)
					.filter(t ->t.geteGender().equals("����"))
					.forEach(t -> System.out.println(t.geteName()));
		
		// �ݺ�
		// forEach()�� �̿��ϸ� ��Ʈ������ ��Ҹ� �ݺ��� �� �ִ�.
		// forEach()�� ���� ó�� �Լ�
		// �߰� ó�� �Լ��� �ݺ�ó���ϴ� �Լ��� �ϳ� �� ����
		employees.stream()
						.peek(t -> System.out.println(t.geteName()))
						.mapToInt(t -> t.getePay())
						.forEach(t -> System.out.println(t));
		//Ȯ�ο� ���� �Լ� ó��
		// 50�� �̻��� ����� �����ؼ� �̸� ���
		boolean result = employees.stream()
						.filter(t -> (t.geteAge() >= 50))
						.allMatch(t -> (t.geteAge() > 55));
		System.out.println(result);
		
		// ���� Ȯ�ο� �Լ��� forEach�� ���� ����ߴµ�
		// forEach���� collect()�� �̿��غ���
		// ���̰� 50�� �̻��� ������� ������ ���ؼ�
		// List<Integer> ������ ArrayList�� ������ ����
		List<Integer> list = employees.stream()
							.filter(t -> (t.geteAge() >= 50))
							.map(t -> t.getePay())
							.collect(Collectors.toList());
							//.collect(Collectors.toCollection(HashSet :: new));
		System.out.println(list);
		// Set���ε� ������ �� �ְ�, Map���ε� ������ �� �ִ�.
		
		
	}
}
