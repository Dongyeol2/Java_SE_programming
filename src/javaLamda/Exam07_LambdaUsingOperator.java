package javaLamda;

import java.util.function.IntBinaryOperator;

/*
 * Operator는 Function과 하는일이 거의 비슷
 * 입력매개변수가 있고 리턴값이 있다.
 * Function은 매핑용도로 많이 사용된다.(입력매개변수를 리턴타입으로 변환, 매핑의 용도)
 * Operator는 연산용도로 많이 사용된다.(입력매개변수를 연산에 이용하여 같은 타입의 리턴값을 돌려주는 형태로 사용)
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
			System.out.println("최대값 : "+ getMaxMin((a,b) -> {
				return a >= b ? a : b; 
			}));
			System.out.println("최소값 : "+ getMaxMin((a,b) -> {
				return a >= b ? b : a;
			}));
		
	}

}
