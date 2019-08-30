package javaLamda;

// 1�� ���
//class MyThread extends Thread {
//	public void run() {
//		System.out.println("�����尡 ����ȴ�.");
//	};
//}

// 2�� ���
//class MyRunnable implements Runnable {
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		
//	}
//



/*
 *  �Ϲ����� interface�� �����ؼ� ���ٽ����� ǥ���� ����\ 
 */
interface Exam01_LambdaIF {
	// �߻�޼ҵ常 �� �� �ִ�.
	// method�� ���ǰ� ���� ���� �����ϴ°� �߻�޼ҵ�(abstract method)
	// ���ٽ� ����ϱ� ���ؼ� �߻�޼ҵ�� �ݵ�� �ϳ��� ���� �ؾ��Ѵ�.
	void myFunc(int k);
}

public class Exam01_LamdaBasic {
	public static void main(String[] args) {
		Exam01_LambdaIF sample = (int k ) -> {System.out.println("���");};
		sample.myFunc(100);
		// Thread�� ����
		// 1. Thread�� subclass�� �̿��ؼ� Thread ����
		// �״��� ���� ����� �ƴ�.
//		MyThread t = new MyThread();
//		t.start();
		// 2. Runnable interface�� ������ class�� �̿��ؼ�
		// Thread�� ����.(�� ���� ���)
//		MyRunnable runnable = new MyRunnable();
//		Thread t = new Thread(runnable);
//		t.start();
		// 3. Runnable interface�� ������ �͸� class�� �̿��ؼ�
		// Thread�� ����. (�ȵ���̵忡�� �Ϲ����� ����)
//		Runnable runnable = new Runnable() {
//			// ��ü�� ���� ���ϴ� ������ �߻� �޼��尡 �����ϱ� �����ε�
//			// �� method�� overriding�ϸ� ��ü�� ������ �� �ִ�.
//			@Override
//			public void run() {
//				
//			}
//		};
//		Thread t = new Thread(runnable);
//		t.start();
		
		// 4. Lambda�� ���� Thread ����
		new Thread(() ->  {
			System.out.println("������ ����!!");
		}).start();
		
	}
}
