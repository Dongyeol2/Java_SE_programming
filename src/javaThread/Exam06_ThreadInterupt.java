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

public class Exam06_ThreadInterupt extends Application {

	TextArea textarea;
	Button startbtn;
	Button stopbtn;
	Thread counterThread;
	
	private void printMsg(String msg) {
		// textarea에 문자열 출력하는 method
		Platform.runLater(() -> {
			textarea.appendText(msg + "\n");
		});
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println(Thread.currentThread().getName());
		BorderPane root = new BorderPane();
		// BorderPane의 크기를 설정 => 화면에 띄우는 window의 크기 설정
		root.setPrefSize(700, 500);
		
		// Component생성해서 BorderPane에 부착
		textarea = new TextArea();
		root.setCenter(textarea);
		
		startbtn = new Button("Thread 시작");
		startbtn.setPrefSize(250, 50);
		startbtn.setOnAction(t -> {
			counterThread = new Thread(() -> {
				try {
					for(int i = 0;  i < 10; i++) {
						Thread.sleep(1000);
						printMsg(i + "-" + Thread.currentThread().getName());
					}
				} catch (Exception e) {
					// intertupt가 걸려있는 상태에 block상태로 진입하면 exception되면서 catch문으로 이동.
					printMsg("Thread가 종료되었음");
				}
			});
			counterThread.start();
		});
		
		stopbtn = new Button("Thread 중지");
		stopbtn.setPrefSize(250, 50);
		stopbtn.setOnAction(t -> {
			counterThread.interrupt(); // method가 실행된다고 바로 Thread가 종료되는지 않는다.
		
			// interrupt() method가 호출된 Thread는 sleep()과 같이
			// block상태에 들어가야지 interrupt를 시킬 수 있다.
		});
		
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700,50);
		
		flowpane.getChildren().add(startbtn);
		flowpane.getChildren().add(stopbtn);
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
