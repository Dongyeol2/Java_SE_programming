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
		// JavaFX는 내부적으로 화면을 제어하는 Thread를 생성해서 사용한다
		
		// 화면구성해서 window 띄우는 코드
		// 화면기본 layout을 설정 => 화면을 동서남북중앙(5개의 영역)으로 분리
		BorderPane root = new BorderPane();
		// BorderPane의 크기를 설정 => 화면에 띄우는 window의 크기 설정
		root.setPrefSize(700, 500);
		
		// Component생성해서 BorderPane에 부착
		textarea = new TextArea();
		root.setCenter(textarea);
		
		btn = new Button("버튼 클릭");
		btn.setPrefSize(250, 50);
		btn.setOnAction(t -> {
			// 1부터 5까지 5번 반복
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
		//flowpane에 버튼을 올려요
		flowpane.getChildren().add(btn);
		root.setBottom(flowpane);
		
		//Scene객체가 필요
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread 호출");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		
		// 현재 main method를 호출한 Thread의 이름을 출력
		Thread.currentThread();
		launch();
	}

}
