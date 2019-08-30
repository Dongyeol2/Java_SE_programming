package javaThread;


// ���밴ü�� �����ϱ� ���� class �ʿ�
class MyShared {
	public synchronized void printNum() {
		for(int i = 0; i < 10; i++) {
			try {
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName() + " : "+ i);
				notify(); // ���� wait���¿� �ִ� thread�� ������ runnable���·� ������ش�
				wait(); // �ڽ��� ������ �ִ� monitor��ü�� ���� ������ wait block���� ����.
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		} 
	}
}

class Exam05_Runnable implements Runnable {
	MyShared obj;
	
	public Exam05_Runnable(MyShared obj) {
		this.obj = obj;
	}
	@Override
	public void run() {
		obj.printNum();
	}
}

public class Exam05_ThreadWaitNotify {

	public static void main(String[] args) {
		// ���밴ü ����
		MyShared shared = new MyShared();
		
		// Thread�� �����ϸ鼭 ���밴ü�� �ξ��ش�
		Thread t1 = new Thread();
		Thread t2 = new Thread();
		
		// Thread ����
		t1.start();
		t2.start();
	}

}
