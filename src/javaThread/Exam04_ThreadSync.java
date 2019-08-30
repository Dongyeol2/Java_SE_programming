package javaThread;

/*
 * 2���� Thread�� �Ļ����Ѽ� ���밴ü�� �̿��ϵ��� ����� ����
 * Thread�� ���밴ü�� ����ȭ�ؼ� ����ϴ� ���� �׷��� ���� ��츦 ��
 * 
 * ���밴ü�� ����� ���� class�� ������ ����
 * 
 */

class SharedObject {
	private int number; //���밴ü�� ������ field
	
	// getter & setter (Thread�� ���ؼ� ���ȴ�)
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	// Thread�� ���ؼ� ���Ǵ� business method
	// synchronized keyword�� ����ȭ�� �� �� �ִ�
	// method����ȭ�� ȿ���� ���� �ʴ�.
	// ����ȭ block�� �̿��ؼ� ó���ϴ°� �Ϲ���
	public synchronized void assignNumber(int number) {
		synchronized(this) {
			this.number = number;
			try {
				Thread.sleep(3000);
				System.out.println("���� ���밴ü�� number : " + this.number);
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
}

// Runnable interface�� ������ class(Thread �������� ���ڷ� �̿��ϱ� ����)
class MyRunnable implements Runnable {
	SharedObject shared;
	int input;
	
	public MyRunnable(SharedObject shared, int input) {
		super();
		this.shared = shared;
		this.input = input;
	}

	@Override
	public void run() {
		shared.assignNumber(input);
	}
}

public class Exam04_ThreadSync {

	public static void main(String[] args) {
		// ���밴ü ����
		SharedObject shared = new SharedObject();
		
		// Thread ����(2��)
		Thread t1 = new Thread(new MyRunnable(shared,100));
		Thread t2 = new Thread(new MyRunnable(shared,200));
		
		// Thread ����(runnable)
		t1.start();
		t2.start();
	}

}
