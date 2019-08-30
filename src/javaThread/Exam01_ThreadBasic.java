package javaThread;

/*
 * 
 * 1. Thread�� ����
 *  => Thread class�� ��ӹ޾Ƽ� class�� �����ϰ� ��ü �����ؼ� ���
 *  => Runnable  interface�� ������ class�� �����ϰ� ��ü�� �����ؼ� Thread �������� ���ڷ� �־ Thread����.
 *  => ���� ���Ǵ� Thread�� �̸��� ���
 *  
 *  2. ���� Thread�� ����(new) -> start() (thread�� �����Ű�°� �ƴ϶� runnable���·� ��ȯ) -> JVM�ȿ� �ִ� Thread schedule�� ����
 *      �ϳ��� Thread�� ���õǼ� thread�� running ���·� ��ȯ -> ��������� �Ǹ� Thread scheduler�� ���ؼ� runnable ���·� ��ȯ
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
			// ��ư���� Action�� �߻�(Ŭ��) ���� �� ȣ��!
			// ��ư Ŭ���ϸ� Thread ����
			new Thread(() ->  {
				// ȭ������� JavaFX Application Thread�� �����.
				// Textarea�� ����ϱ� ���ؼ� JavaFX Application Thread ���� ��Ź�Ѵٴ� ����
				System.out.println(Thread.currentThread().getName());
				Platform.runLater(() -> {
					textarea.appendText("�Ҹ����� �ƿ켺\n ");					
				});
			}).start();
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
