package javaNetwork;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javaNetwork.Exam01_socketClient.ClientReader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam04_CharClient extends Application{
	TextArea textarea;
	Button connbtn, disconnbtn; // ���� ����, ���� ��ư
	TextField idTf, msgTf;			  // ���̵� �Է�ĭ, �޽��� �Է�ĭ
	
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	// Ŭ���̾�Ʈ�� Thread 1���� ��������. ThreadPool�� ����� ��� overhead�߻�
	ExecutorService executorService = Executors.newCachedThreadPool();
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg);
		});
	}
	
	// �����κ��� ������ �޽����� ��� �޾Ƽ� ȭ�鿡 ����ϱ� ���� �뵵�� Thread
	class ReceiveRunnable implements Runnable {
		// �����κ��� ������ �޽����� �޾Ƶ��̴� ���� ����
		// ���Ͽ� ���� �Է½�Ʈ���� ������ �ȴ�.
		private BufferedReader br;
		
		public ReceiveRunnable(BufferedReader br) {
			super();
			this.br = br;
		}

		@Override
		public void run() {
			String line = "";
			try {
				while((line = br.readLine()) != null) {
					printMsg(line);
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
		}
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
		
		connbtn = new Button("Server�� ����");
		connbtn.setPrefSize(250, 50);
		connbtn.setOnAction(t->{
			 if (connbtn.getText().equals("Server�� ����")) {
				 try {
						//�����ʿ� Socket������ �õ�
						socket = new Socket("127.0.0.1",6789);
						System.out.println("**���� ���� OK**");
						//���ӿ� �����ϸ� socket��ü�� �ϳ� ȹ��
						InputStreamReader isr = new InputStreamReader(socket.getInputStream());
						br = new BufferedReader(isr);
						out = new PrintWriter(socket.getOutputStream());
						printMsg("ä�� ���� ���� ����\n");
						// ������ ���������� ���� Thread�� ���� ������ ������
						// �����͸� ���� �غ� �Ѵ�.
						ReceiveRunnable runnable = new ReceiveRunnable(br);
						executorService.execute(runnable);
				 } catch(Exception e) {
					 	System.out.println(e);
				 }
			 }
		});
		
		disconnbtn = new Button("���� ����");
		disconnbtn.setPrefSize(250, 50);
		disconnbtn.setOnAction(t->{
			 try {
				 out.println("/EXIT");
				 out.flush();
				 printMsg("���� ���� ����");
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		});
		
		idTf = new TextField();
		idTf.setPrefSize(100, 40);
		
		msgTf = new TextField();
		msgTf.setPrefSize(200, 40);
		msgTf.setOnAction((event) -> {
			String msg = idTf.getText() + ">" + msgTf.getText() + "\n";
			out.println(msg);	// ������ ���ڿ� ����
			out.flush();
			msgTf.setText("");
	    });

		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane�� ��ư �߰�
		flowpane.getChildren().add(connbtn);
		flowpane.getChildren().add(disconnbtn);	
		flowpane.getChildren().add(idTf);
		flowpane.getChildren().add(msgTf);
		root.setBottom(flowpane);
		
		//Scene��ü�� �ʿ�
		Scene scene = new Scene(root); 
		primaryStage.setScene(scene);
		primaryStage.setTitle("Network ���� �Դϴ�.");
		primaryStage.show();
	}
	
	public static void main(String[] args) {		
		launch();
	}

}
