package javaThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam08_ThreadPoolBasic extends Application {

	TextArea textarea;
	Button initBtn, startBtn, stopBtn;
	// initBtn : Thread Pool을 생성하는 버튼
	// startBtn : Thread Pool을 이용해서 Thread를 실행시키는 버튼
	// stopBtn : Thread Pool을 중지시키는 버튼
	ExecutorService executorService;
	// executorService : Thread Pool
	
	private void printMsg(String msg) {
		Platform.runLater(() -> {
			textarea.appendText(msg + "\n");
		});
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println(Thread.currentThread().getName());
		BorderPane root = new BorderPane();
		root.setPrefSize(750, 500);
		
		textarea = new TextArea();
		root.setCenter(textarea);
		
		initBtn = new Button("Thread Pool 생성");
		initBtn.setPrefSize(250, 50);
		initBtn.setOnAction(t -> {
			executorService = Executors.newCachedThreadPool();
			int threadNum = ((ThreadPoolExecutor)executorService).getPoolSize();
			printMsg("현재 Pool안의 Thread 개수 : " + threadNum);
			// 처음에 만들어지는 Thread Pool에는 thread가 없다.
			// 만약 필요하면 내부적으로 Thread를 생성.
			// 만드는 Thread의 수는 제한이 없다.
			// 60초 동안 Thread가 사용되지 않으면 자동적으로 삭제
			//executorService = Executors.newFixedThreadPool(5);
			//처음에 만들어지는 Thread Pool안에는 thread가 없다.
			// 만약 필요하면 내부적으로 Thread를 생성
			// 인자로 들어온 int 수만큼 Thread를 넘지 못한다.
			// Thread가 사용되지 않더라도 만들어진 Thread는 계속 유지.
		});
		
		stopBtn = new Button("Thread Pool 종료");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t -> {
			executorService.shutdown();
			
		});
		
		startBtn = new Button("Thread 실행");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t -> {
			for(int i = 0; i < 10; i++) {
				final int k = i;
				Runnable runnable = () -> {
					Thread.currentThread().setName("MYThread-"+k);
					String msg = Thread.currentThread().getName()
							+ ", Pool의 개수 : "
							+ ((ThreadPoolExecutor)executorService).getPoolSize();
					printMsg(msg);
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						System.out.println(e.toString());
					}
				};
				executorService.execute(runnable);
			}
		});
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(750,50);
		flowpane.getChildren().add(initBtn);
		flowpane.getChildren().add(stopBtn);
		flowpane.getChildren().add(startBtn);
		root.setBottom(flowpane);
		
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
