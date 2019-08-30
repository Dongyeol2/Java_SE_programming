package javaThread;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

class UserPanel extends FlowPane {
	private TextField namefield = new TextField();
	private ProgressBar progressBar = new ProgressBar(0.0);
	private ProgressIndicator progressIndicator = new ProgressIndicator(0.0);
	
	public UserPanel() {
		super();
	}
	
	public UserPanel(String name) {
		setPrefSize(700,50);
		namefield.setPrefSize(100, 50);
		progressBar.setPrefSize(500, 50);
		progressIndicator.setPrefSize(50, 50);

		namefield.setText(name);
		
		getChildren().add(namefield);
		getChildren().add(progressBar);
		getChildren().add(progressIndicator);
		
	}

	public TextField getNamefield() {
		return namefield;
	}

	public void setNamefield(TextField namefield) {
		this.namefield = namefield;
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public ProgressIndicator getProgressIndicator() {
		return progressIndicator;
	}

	public void setProgressIndicator(ProgressIndicator progressIndicator) {
		this.progressIndicator = progressIndicator;
	}
	
}

class Grade {
	private int grade;
	
	public Grade(int grade) {
		this.grade = grade;
	}

	public int getGrade() {
		grade++;
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
}

class ProgressRunnable implements Runnable {
	private String name;
	private ProgressBar ProgressBar;
	private ProgressIndicator ProgressIndicator;
	private TextArea textarea;
	private Grade grade;
	
	public ProgressRunnable(String name, javafx.scene.control.ProgressBar progressBar,
			javafx.scene.control.ProgressIndicator progressIndicator, TextArea textarea) {
		super();
		this.name = name;
		this.ProgressBar = progressBar;
		this.ProgressIndicator = progressIndicator;
		this.textarea = textarea;
		
	}

	@Override
	public void run() {
		// Thread�� �����ؼ� progressBar�� ����
		Random random = new Random();
		double k = 0;
		while(ProgressBar.getProgress() < 1.0) {
			try {
				//Thread.sleep(1000); // 1�� ���� ���� Thread�� sleep
				k += (random.nextDouble() * 0.1);
				final double tt = k;
				// k���� ���������� ����.
				Platform.runLater(() -> {
					 ProgressBar.setProgress(tt);
					 ProgressIndicator.setProgress(tt);
					 //textarea.appendText(name);
					 System.out.println(name);
				}); 
				if(tt > 1.0) break;
				
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		if(k >= 1.0) {
			textarea.appendText(grade.getGrade() + " : " + name+"\n");
		}
	}
}

public class Exam02_ThreadRace extends Application {

	private List<String> names = Arrays.asList("1�� ��", "2�� ��", "3�� ��");
	
	//progressBar�� ������ thread�� flowpane 1���� 1���� �����ؾ� �Ѵ�.
	private List<ProgressRunnable> uRunnable =
			new ArrayList<ProgressRunnable>();
	
	TextArea textarea;
	Button btn;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// ȭ�鱸���ؼ� window ���� �ڵ�
		// ȭ��⺻ layout�� ���� => ȭ���� ���������߾�(5���� ����)���� �и�
		BorderPane root = new BorderPane();
		// BorderPane�� ũ�⸦ ���� => ȭ�鿡 ���� window�� ũ�� ����
		root.setPrefSize(700, 500);
		
		//center�κ��� ������ TilePane�� ����
		TilePane center = new TilePane();
		center.setPrefColumns(1); //1���� �����ϴ� TilePane
		center.setPrefRows(4);      // 4�ุ �����ϴ� TilePane
		
		// �޽����� ��µ� TextArea ���� �� ũ�� ����
		textarea = new TextArea();
		textarea.setPrefSize(600, 100);

		// ���� ������� TilePane�� 3���� FlowPane�� TextArea�� ����
		for(String name : names) {
			UserPanel panel = new UserPanel(name);
			center.getChildren().add(panel);
			uRunnable.add(new ProgressRunnable(panel.getNamefield().getText(),
																				panel.getProgressBar(), 
																				panel.getProgressIndicator(),
																				textarea)); 
		}
		center.getChildren().add(textarea);
		
		root.setCenter(center);
		
		btn = new Button("���� ����");
		btn.setPrefSize(250, 50);
		btn.setOnAction(t -> {
			// ��ư���� Action�� �߻�(Ŭ��)���� �� ȣ��
			// uRunnable(ArrayList)�� ���鼭 Thread�� �����ϰ�
			// start() ȣ��
			for(ProgressRunnable runnable : uRunnable) {
				new Thread(runnable).start();
			}
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
