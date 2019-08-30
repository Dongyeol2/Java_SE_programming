package javaThread;

/*
 * 1 ���� 100���� ������ ���� ���غ���
 * 1~10���� 1���� Thread�� ���� ����ؼ� ����� return
 * 11~20���� 1���� Thread�� ���� ����ؼ� ����� return
 * ...
 * 91~100���� 1���� Thread�� ���� ����ؼ� ����� return
 * ==> Thread Pool�� �̿��ؾ� �ϰ� Callable�� �̿��ؼ� ���ϰ��� �޾ƾ� �Ѵ�.
 * ==> 10���� Thread�κ��� ���� �����͸� �޾Ƶ��̴� Thread�� �ϳ� ������ �Ѵ�.
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
		
		initBtn = new Button("Thread Pool ����");
		initBtn.setPrefSize(250, 50);
		initBtn.setOnAction(t -> {
			executorService = Executors.newCachedThreadPool();
			executorCompletionService = new ExecutorCompletionService<Integer>(executorService);
			
			
			
		});
		
		stopBtn = new Button("Thread Pool ����");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t -> {
			printMsg("Thread Pool ����");
			executorService.shutdown();
			
			
		});
		
		startBtn = new Button("Thread ����");
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
					printMsg("���� ������� : " + total);
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
		primaryStage.setTitle("Thread ȣ��");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		
		// ���� main method�� ȣ���� Thread�� �̸��� ���
		Thread.currentThread();
		launch();
	}

}