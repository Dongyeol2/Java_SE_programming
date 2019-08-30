package javaThread;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam07_ThreadDaemon extends Application {

	TextArea textarea;
	Button btn;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println(Thread.currentThread().getName());
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);
		
		textarea = new TextArea();
		root.setCenter(textarea);
		
		btn = new Button("��ư Ŭ��");
		btn.setPrefSize(250, 50);
		btn.setOnAction(t -> {
			// Thread ����(for loop�� 1�� sleep�ϸ鼭 10�� �ݺ�)
			// �� Thread�� dead���·� �������� 10�ʰ� �ɸ�
			Thread thread = new Thread(() -> {
				try {
					for(int i = 0; i < 10; i++) {
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			});
			thread.setDaemon(true); // �ش� thread�� demon thread�� ����
			// �ڽ� thread�� �ȴٰ� �����ϸ� ��
			// �θ� thread�� �����Ǹ� �ڵ����� �ڽ� thread�� ����.
			thread.start();
			
		});
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700,50);
		flowpane.getChildren().add(btn);
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
