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
		
		btn = new Button("버튼 클릭");
		btn.setPrefSize(250, 50);
		btn.setOnAction(t -> {
			// Thread 생성(for loop를 1초 sleep하면서 10번 반복)
			// 이 Thread는 dead상태로 가기위해 10초가 걸림
			Thread thread = new Thread(() -> {
				try {
					for(int i = 0; i < 10; i++) {
						Thread.sleep(1000);
					}
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			});
			thread.setDaemon(true); // 해당 thread를 demon thread로 설정
			// 자식 thread가 된다고 생각하면 됨
			// 부모 thread가 중지되면 자동으로 자식 thread도 중지.
			thread.start();
			
		});
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700,50);
		flowpane.getChildren().add(btn);
		root.setBottom(flowpane);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread 호출");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		
		Thread.currentThread();
		launch();
	}

}
