package javaNetwork;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

class EchoRunnable implements Runnable {
	// ������ �־�� �ϴ� field
	Socket socket ; // Ŭ���̾�Ʈ�� ����� ����
	BufferedReader br; // �Է��� ���� ��Ʈ��
	PrintWriter out; // ����� ���� ��Ʈ��
	
	
	
	public EchoRunnable(Socket socket) {
		super();
		this.socket = socket;
		try {
			this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	@Override
	public void run() {
		// Ŭ���̾�Ʈ�� echoó�� ����
		// Ŭ���̷�Ʈ�� ���ڿ��� ������ �ش� ���ڿ��� �޾Ƽ� �ٽ� Ŭ���̾�Ʈ���� ����,
		// �ѹ��ϰ� �����ϴ°� �ƴ϶� Ŭ���̾�Ʈ�� "EXIT"��� ���ڿ��� ���������� ����
		String line = "";
		try {
			while((line = br.readLine()) != null) {
				if(line.equals("/EXIT")) {
					break; 
				}
				else {
					out.println(line);
					out.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

public class Exam03_EchoServerMultiClient extends Application{
	TextArea textarea;
	Button startbtn, stopbtn; // ���� ����, ���� ��ư
	ExecutorService executorservice = Executors.newCachedThreadPool();
	// Ŭ���̾�Ʈ�� ������ �޾Ƶ��̴� ��������
	ServerSocket server;
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg + "\n");
		});
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Stage arg0 -> window�� ��Ī��
		//ȭ�鱸���ؼ� window ���� �ڵ�
		//ȭ��⺻ layout�� ���� => ȭ���� ���������߾�(5���� ����)���� �и�
		BorderPane root = new BorderPane();
		//BorderPane�� ũ�⸦ ����=> ȭ���� ���� window�� ũ�⼳��
		root.setPrefSize(700, 500);
		//Component�����ؼ� BorderPane�� ����
		textarea = new TextArea();
		root.setCenter(textarea);
		
		startbtn = new Button("Server ����");
		startbtn.setPrefSize(250, 50);
		startbtn.setOnAction(t->{
			// ��ư���� Action�� �߻�(Ŭ��)���� �� ȣ��!
			// �������α׷��� ����
			// Ŭ���̾�Ʈ�� ������ ��ٸ�
			// -> Thread�� �����ؼ� Ŭ���̾�Ʈ�� Thread�� ����ϵ��� �����.
			// ������ �ٽ� �ٸ� Ŭ���̾�Ʈ�� ������ ��ٸ�
			Runnable runnable = () -> {
				try {
					server = new ServerSocket(7777);
					while(true) {
						printMsg("Ŭ���̾�Ʈ ���� ���");
						Socket socket = server.accept(); // blocking
						printMsg("Ŭ���̾�Ʈ ���� ����");
						// Ŭ���̾�Ʈ�� ���������� Thread ����� �����ؾ���
						EchoRunnable r = new EchoRunnable(socket);
						executorservice.execute(r); // thread ����
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
			executorservice.execute(runnable);
		});
		
		stopbtn = new Button("Server ����");
		stopbtn.setPrefSize(250, 50);
		stopbtn.setOnAction(t->{
			
		});
		
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane�� ��ư �߰�
		flowpane.getChildren().add(startbtn);
		flowpane.getChildren().add(stopbtn);
		root.setBottom(flowpane);
		
		//Scene��ü�� �ʿ�
		Scene scene = new Scene(root); 
		primaryStage.setScene(scene);
		primaryStage.setTitle("Multi Client Echo Server");
		primaryStage.show();
	}
	
	public static void main(String[] args) {		
		launch();
	}

}
