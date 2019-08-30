package javaNetwork;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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

public class Exam04_ChatServer extends Application{
	
	TextArea textarea;				// �޽��� â
	Button startbtn, stopbtn; // ���� ����, ���� ��ư
	ServerSocket server;		// Ŭ���̾�Ʈ�� ������ �޾Ƶ��̴� ��ü
	
	// Thread pool ����
	// resource�� �����ؼ� Thread Pool �����ϱ����ؼ��� FixedThreadPool ���
	// �װ� �ƴ϶�� CachedThreadPool ����Ѵ�.
	ExecutorService executorService = Executors.newCachedThreadPool(); 
	// singleton������ ������ü�� ����
	SharedObject sharedObject = new SharedObject();
	
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg + "\n");
		});
	}
	
	//Ŭ���̾�Ʈ�� ����� Thread�� ����ϴ� ���� ��ü ����� ����
	// Ŭ������ ����	
	// inner class���·� ����. (���� : ����ϱ� ���ϴ�. ���� : ���뼺 ����)
	// Thread�� ���� ��ü�� Thread�� ������ �־�� �ϴ� �ڷᱸ��,
	// ����� ������ ���� ��ü�� ��Ī�Ѵ�.
	class SharedObject {
		// Ŭ���̾�Ʈ Thread�� �����ϰ� �־�� �Ѵ�.
		List<ClientRunnable> clients = new ArrayList<ClientRunnable>();
		// �츮�� �ʿ��� ����� Broadcast ���
		// Thread��  Ŭ���̾�Ʈ�κ��� �����͸� �޾Ƽ� ��� client ���� �����ϴ� ����� ����
		// ������ü�� method�� ���� Thread�� ���ؼ� ���ÿ� ���� �� �ִ�.
		// �̷� ��쿡�� ����ȭ ó���� �� ��� �������� ��µ� �� �ִ�.
		public void broadcast(String msg) {
			clients.stream().forEach(t -> {
				t.out.println(msg);
				t.out.flush();
			});
		}
	}
	
	// Ŭ���̾�Ʈ�� ���εǴ� Thread�� ��������� Runnable class
	class ClientRunnable implements Runnable {
		private SharedObject sharedobject = new SharedObject(); //���� ��ü
		private Socket socket;	// Ŭ���̾�Ʈ�� ���εǴ� socket
		private BufferedReader br;
		private PrintWriter out;
		
		// Ŭ���̾�Ʈ�� ������ �����ؼ� �ش� Ŭ���̾�Ʈ�� ���� Thread�� ������ ��
		// Thread���� 2���� ��ü�� ���޵Ǿ�� �Ѵ�.
		// �����ڸ� 2���� ����(������ü�� ����)�� �޴� ���·� �ۼ�
		// �Ϲ������� �����ڴ� �ʵ� �ʱ�ȭ�� ����ϱ� ������ Stream�� ����
		public ClientRunnable(SharedObject sharedobject, Socket socket) {
			super();
			this.sharedobject = sharedobject;
			this.socket = socket;
			try {
				this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				this.out = new PrintWriter(socket.getOutputStream());
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	
		// Ŭ���̾�Ʈ�� Thread�� ���۵Ǹ� run methos()�� ����ȴ�.
		// Ŭ���̾�Ʈ�� ������ ����� ����
		// �ݺ������� Ŭ���̾�Ʈ�� �����͸� �޾Ƽ� ������ü�� �̿��ؼ�
		// broadcasting
		@Override
		public void run() {
			String msg = "";
			try {
				while((msg = br.readLine()) != null) {
					// Ŭ���̾�Ʈ�� ä���� �����ϸ�
					if(msg.equals("/EXIT")) {
						break;
					}
					// �Ϲ����� ä�� �޽����� ��� ��� Ŭ���̾�Ʈ���� ����
					sharedObject.broadcast(msg);
				}
			} catch (Exception e) {

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
		
		startbtn = new Button("Chat Server ����");
		startbtn.setPrefSize(250, 50);
		startbtn.setOnAction(t->{
			textarea.clear();
			printMsg("[ä�ü����⵿ - 6789]");
			// ���� ������ ���� Ŭ���̾�Ʈ ������ ��ٸ���.
			// JavaFX Thread�� blocking���� �ʵ��� ���ο� Thread�� ����
			// Ŭ���̾�Ʈ ������ ��ٷ��� �Ѵ�.
			Runnable runnable = new Runnable() {
				public void run() {
					// ���� ������ �ؾ��ϴ� �۾�
					try {
						server = new ServerSocket(6789);
						while(true) {
							printMsg("[Client ���� �����]");
							Socket socket = server.accept();
							printMsg("[Client ���� ����]");
							// client�� ���������� Thread�� �ϳ� �����ϰ� �����ؾ� �Ѵ�.
							ClientRunnable cRunnable = new ClientRunnable(sharedObject, socket);
							// ���ο� client�� ���ӵǾ����� ���밴ü�� ArrayList�ȿ� ���ο� client ��ü�� ���� �Ѵ�.
							// Thread�� ���ؼ� ���밴ü�� �����Ͱ� ���� ���� ����ȭ ó���� �� ��� �����ϴ�.
							sharedObject.clients.add(cRunnable);
							printMsg("[���� client �� : " + sharedObject.clients.size() +"]");
							executorService.execute(cRunnable);
						}
					} catch (Exception e) {
						System.out.println(e.toString());
					}
				}
			};
			executorService.execute(runnable);
		});
		
		stopbtn = new Button("Chat Server ����");
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
