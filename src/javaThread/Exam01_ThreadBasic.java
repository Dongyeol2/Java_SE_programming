package javaThread;

/*
 * 
 * 1. Thread의 생성
 *  => Thread class를 상속받아서 class를 정의하고 객체 생성해서 사용
 *  => Runnable  interface를 구현한 class를 정의하고 객체를 생성해서 Thread 생성자의 인자로 넣어서 Thread생성.
 *  => 현재 사용되는 Thread의 이름을 출력
 *  
 *  2. 실제 Thread의 생성(new) -> start() (thread를 실행시키는게 아니라 runnable상태로 전환) -> JVM안에 있는 Thread schedule에 의해
 *      하나의 Thread가 선택되서 thread가 running 상태로 전환 -> 어느시점이 되면 Thread scheduler에 의해서 runnable 상태로 전환
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

public class Exam01_ThreadBasic extends Application {

	TextArea textarea;
	Button btn;
	
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
			// 버튼에서 Action이 발생(클릭) 했을 때 호출!
			// 버튼 클릭하면 Thread 생성
			new Thread(() ->  {
				// 화면제어는 JavaFX Application Thread가 담당함.
				// Textarea를 출력하기 위해서 JavaFX Application Thread 한테 부탁한다는 개념
				System.out.println(Thread.currentThread().getName());
				Platform.runLater(() -> {
					textarea.appendText("소리없는 아우성\n ");					
				});
			}).start();
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
