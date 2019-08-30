package javaThread;


// 공용객체를 생성하기 위한 class 필요
class MyShared {
	public synchronized void printNum() {
		for(int i = 0; i < 10; i++) {
			try {
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName() + " : "+ i);
				notify(); // 현재 wait상태에 있는 thread를 깨워서 runnable상태로 만들어준다
				wait(); // 자신이 가지고 있는 monitor객체를 놓고 스스로 wait block으로 들어간다.
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
		// 공용객체 생성
		MyShared shared = new MyShared();
		
		// Thread를 생성하면서 공용객체를 널어준다
		Thread t1 = new Thread();
		Thread t2 = new Thread();
		
		// Thread 실행
		t1.start();
		t2.start();
	}

}
