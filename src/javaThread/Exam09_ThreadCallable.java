package javaThread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam09_ThreadCallable extends Application {

	TextArea textarea;
	Button initBtn, startBtn, stopBtn;
	ExecutorService executorService;
	
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
			
		});
		
		stopBtn = new Button("Thread Pool 종료");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t -> {
			printMsg("Thread Pool 종료");
			executorService.shutdown();
			
			
		});
		
		startBtn = new Button("Thread 실행");
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t -> {
			for(int i = 0; i < 10; i++) {
				final int k = i;
				
				Callable<String> callable = new Callable<String>() {
					@Override
					public String call() throws Exception {
						Thread.currentThread().setName("MYThread-"+k);
						String msg = Thread.currentThread().getName()
								+ ", Pool의 개수 : "
								+ ((ThreadPoolExecutor)executorService).getPoolSize();
						System.out.println(msg);
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							System.out.println(e.toString());
						}
						return Thread.currentThread().getName() + " 종 료";
					}
				};
				
				Future<String>  future = executorService.submit(callable);
				// future : pending 객체
				try {
					String result = future.get();
					// get() method가 blocking method...
					System.out.println(result);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
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
