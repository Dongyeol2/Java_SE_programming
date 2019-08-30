package javaLamda;

import java.util.function.IntBinaryOperator;

/*
 * Operator�� Function�� �ϴ����� ���� ���
 * �Է¸Ű������� �ְ� ���ϰ��� �ִ�.
 * Function�� ���ο뵵�� ���� ���ȴ�.(�Է¸Ű������� ����Ÿ������ ��ȯ, ������ �뵵)
 * Operator�� ����뵵�� ���� ���ȴ�.(�Է¸Ű������� ���꿡 �̿��Ͽ� ���� Ÿ���� ���ϰ��� �����ִ� ���·� ���)
 */



public class Exam07_LambdaUsingOperator {

	public static int arr[] = {100,92,50,89,34,27,99,3};
	
	private static int getMaxMin(IntBinaryOperator operator) {
		int result = arr[0];
		
		for (int k : arr) {
			result = operator.applyAsInt(result, k);
		}
		return result;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			System.out.println("�ִ밪 : "+ getMaxMin((a,b) -> {
				return a >= b ? a : b; 
			}));
			System.out.println("�ּҰ� : "+ getMaxMin((a,b) -> {
				return a >= b ? b : a;
			}));
		
	}

}
