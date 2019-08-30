package javaThread;

/*
 * 
 * 1. Thread�� ����
 *  => Thread class�� ��ӹ޾Ƽ� class�� �����ϰ� ��ü �����ؼ� ���
 *  => Runnable  interface�� ������ class�� �����ϰ� ��ü�� �����ؼ� Thread �������� ���ڷ� �־ Thread����.
 *  => ���� ���Ǵ� Thread�� �̸��� ���
 *  
 *  2. ���� Thread�� ����(new) -> start() (thread�� �����Ű�°� �ƴ϶� runnable���·� ��ȯ) -> JVM�ȿ� �ִ� Thread schedule�� ����
 *      �ϳ��� Thread�� ���õǼ� thread�� running ���·� ��ȯ -> ��������� �Ǹ� Thread scheduler�� ���ؼ� runnable ���·� ��ȯ
 *      -> 
 * 
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam06_ThreadInterupt extends Application {

	TextArea textarea;
	Button startbtn;
	Button stopbtn;
	Thread counterThread;
	
	private void printMsg(String msg) {
		// textarea�� ���ڿ� ����ϴ� method
		Platform.runLater(() -> {
			textarea.appendText(msg + "\n");
		});
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println(Thread.currentThread().getName());
		BorderPane root = new BorderPane();
		// BorderPane�� ũ�⸦ ���� => ȭ�鿡 ���� window�� ũ�� ����
		root.setPrefSize(700, 500);
		
		// Component�����ؼ� BorderPane�� ����
		textarea = new TextArea();
		root.setCenter(textarea);
		
		startbtn = new Button("Thread ����");
		startbtn.setPrefSize(250, 50);
		startbtn.setOnAction(t -> {
			counterThread = new Thread(() -> {
				try {
					for(int i = 0;  i < 10; i++) {
						Thread.sleep(1000);
						printMsg(i + "-" + Thread.currentThread().getName());
					}
				} catch (Exception e) {
					// intertupt�� �ɷ��ִ� ���¿� block���·� �����ϸ� exception�Ǹ鼭 catch������ �̵�.
					printMsg("Thread�� ����Ǿ���");
				}
			});
			counterThread.start();
		});
		
		stopbtn = new Button("Thread ����");
		stopbtn.setPrefSize(250, 50);
		stopbtn.setOnAction(t -> {
			counterThread.interrupt(); // method�� ����ȴٰ� �ٷ� Thread�� ����Ǵ��� �ʴ´�.
		
			// interrupt() method�� ȣ��� Thread�� sleep()�� ����
			// block���¿� ������ interrupt�� ��ų �� �ִ�.
		});
		
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700,50);
		
		flowpane.getChildren().add(startbtn);
		flowpane.getChildren().add(stopbtn);
		root.setBottom(flowpane);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread ȣ��");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		
		Thread.currentThread();
		launch();
	}

}
