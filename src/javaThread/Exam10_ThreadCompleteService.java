package javaThread;

/*
 * 1 부터 100까지 숫자의 합을 구해보자
 * 1~10까지 1개의 Thread가 합을 계산해서 결과를 return
 * 11~20까지 1개의 Thread가 합을 계산해서 결과를 return
 * ...
 * 91~100까지 1개의 Thread가 합을 계산해서 결과를 return
 * ==> Thread Pool을 이용해야 하고 Callable을 이용해서 리턴값을 받아야 한다.
 * ==> 10개의 Thread로부터 각각 데이터를 받아들이는 Thread를 하나 만들어야 한다.
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam10_ThreadCompleteService extends Application {

	TextArea textarea;
	Button initBtn, startBtn, stopBtn;
	ExecutorService executorService;
	ExecutorCompletionService<Integer> executorCompletionService;
	
	private int total = 0;
	
	
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
			executorCompletionService = new ExecutorCompletionService<Integer>(executorService);
			
			
			
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
				
				Callable<Integer> callable = new Callable<Integer>() {
					@Override
					public Integer call() throws Exception {
						IntStream intStream = IntStream.rangeClosed(k, k+9);
						int sum = intStream.sum();
						return sum;
					}
				};
				executorCompletionService.submit(callable);
			}
			
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					for(int i = 0; i < 10; i++) {
						try {
							Future<Integer> future = executorCompletionService.take();
							total += future.get();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					printMsg("최종 결과값은 : " + total);
				}
			};
			executorService.execute(runnable);
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