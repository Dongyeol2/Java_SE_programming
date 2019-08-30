package javaLamda;

// 1번 방식
//class MyThread extends Thread {
//	public void run() {
//		System.out.println("쓰레드가 실행된다.");
//	};
//}

// 2번 방식
//class MyRunnable implements Runnable {
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		
//	}
//



/*
 *  일반적인 interface를 정의해서 람다식으로 표현해 보자\ 
 */
interface Exam01_LambdaIF {
	// 추상메소드만 올 수 있다.
	// method의 정의가 없고 선언만 존재하는게 추상메소드(abstract method)
	// 람다식 사용하기 위해서 추상메소드는 반드시 하나만 존재 해야한다.
	void myFunc(int k);
}

public class Exam01_LamdaBasic {
	public static void main(String[] args) {
		Exam01_LambdaIF sample = (int k ) -> {System.out.println("출력");};
		sample.myFunc(100);
		// Thread를 생성
		// 1. Thread의 subclass를 이용해서 Thread 생성
		// 그다지 좋은 방법은 아님.
//		MyThread t = new MyThread();
//		t.start();
		// 2. Runnable interface를 구현한 class를 이용해서
		// Thread를 생성.(더 좋은 방식)
//		MyRunnable runnable = new MyRunnable();
//		Thread t = new Thread(runnable);
//		t.start();
		// 3. Runnable interface를 구현한 익명 class를 이용해서
		// Thread를 생성. (안드로이드에서 일반적인 형태)
//		Runnable runnable = new Runnable() {
//			// 객체를 생성 못하는 이유는 추상 메서드가 존재하기 때문인데
//			// 이 method를 overriding하면 객체를 생성할 수 있다.
//			@Override
//			public void run() {
//				
//			}
//		};
//		Thread t = new Thread(runnable);
//		t.start();
		
		// 4. Lambda식 으로 Thread 실행
		new Thread(() ->  {
			System.out.println("쓰레드 실행!!");
		}).start();
		
	}
}
