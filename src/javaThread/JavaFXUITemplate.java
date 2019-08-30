package javaThread;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class JavaFXUITemplate extends Application {

	TextArea textarea;
	Button btn;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
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
			// Textarea�� ���ڸ� �־��
			textarea.appendText("�Ҹ����� �ƿ켺\n ");
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
		launch();
	}

}
