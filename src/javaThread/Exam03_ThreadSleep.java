package javaThread;

import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam03_ThreadSleep extends Application {

	TextArea textarea;
	Button btn;
	
	private void printMsg(String msg) {
		Platform.runLater(() -> {
			textarea.appendText(msg + "\n");
		}); 
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println(Thread.currentThread().getName());
		// JavaFX�� ���������� ȭ���� �����ϴ� Thread�� �����ؼ� ����Ѵ�
		
		// ȭ�鱸���ؼ� window ���� �ڵ�
		// ȭ��⺻ layout�� ���� => ȭ���� ���������߾�(5���� ����)���� �и�
		BorderPane root = new BorderPane();
		// BorderPane�� ũ�⸦ ���� => ȭ�鿡 ���� window�� ũ�� ����
		root.setPrefSize(700, 500);
		
		// Component�����ؼ� BorderPane�� ����
		textarea = new TextArea();
		root.setCenter(textarea);
		
		btn = new Button("��ư Ŭ��");
		btn.setPrefSize(250, 50);
		btn.setOnAction(t -> {
			// 1���� 5���� 5�� �ݺ�
			IntStream intstream = IntStream.rangeClosed(1, 5);
			intstream.forEach(i -> {
				Thread thread = new Thread(() -> {
					for(int c = 0; c < 5; c++) {
						try {
							Thread.sleep(3000);
							printMsg(c+ " : " + Thread.currentThread().getName());
						} catch (Exception e) {
							System.out.println(e.toString());
						}
					}
				});
				thread.setName("ThreadNumber-"+ i);
				thread.start();
			});
		});
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700,50);
		//flowpane�� ��ư�� �÷���
		flowpane.getChildren().add(btn);
		root.setBottom(flowpane);
		
		//Scene��ü�� �ʿ�
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread ȣ��");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		
		// ���� main method�� ȣ���� Thread�� �̸��� ���
		Thread.currentThread();
		launch();
	}

}
