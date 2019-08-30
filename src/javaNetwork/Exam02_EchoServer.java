package javaNetwork;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Exam02_EchoServer extends Application{
	TextArea textarea;
	Button startbtn; // ���� ����, ���� ��ư
	public static ExecutorService executorservice;
	public static Vector<Client> clients = new Vector<Client>();
	
	// Ŭ���̾�Ʈ�� ������ �޾Ƶ��̴� ��������
	ServerSocket server;
	
	// ������ �����ؼ� Ŭ���̾�Ʈ�� ���� ��ٸ��� �޼ҵ�
	public void startServer(String IP, int port) {
		try {
			server = new ServerSocket();
			server.bind(new InetSocketAddress(IP, port));
		} catch (Exception e) {
			e.printStackTrace();
			if(!server.isClosed())
				stopServer();
			return;
		}
		
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						Socket socket = server.accept();
						clients.add(new Client(socket));
						System.out.println("Ŭ���̾�Ʈ ���� " + socket.getRemoteSocketAddress() + " : " + Thread.currentThread().getName());
					} catch (Exception e) {
						if(!server.isClosed())
							stopServer();
						break;
					}
				}
			}
		};
		executorservice = Executors.newCachedThreadPool();
		executorservice.submit(r);
	}
	
	// ������ �۵��� ������Ű�� �޼ҵ�
	public void stopServer() {
		try {
			Iterator<Client> iterator = clients.iterator();
			while(iterator.hasNext()) {
				Client client = iterator.next();
				client.socket.close();
				iterator.remove();
			}
			if(server != null && !server.isClosed())
				server.close();
			if(executorservice != null && !executorservice.isShutdown())
				executorservice.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage primaryStage){
		try {
			BorderPane root = new BorderPane();
			root.setPrefSize(700, 500);
			
			textarea = new TextArea();
			root.setCenter(textarea);
			
			startbtn = new Button("server ����");
			startbtn.setMaxWidth(Double.MAX_VALUE);
			BorderPane.setMargin(startbtn, new Insets(1,0,0,0));
			root.setBottom(startbtn);
			
			String IP = "127.0.0.1";
			int port = 5554;
			
			startbtn.setOnAction(event -> {
				if(startbtn.getText().equals("server ����")) {
					startServer(IP, port);
					Platform.runLater(() -> {
						String msg = String.format("���� ����\n", IP, port);
						textarea.appendText(msg);
						startbtn.setText("server ����");
					});
				} else {
					stopServer();
					Platform.runLater(() -> {
						String msg = String.format("���� ����\n", IP, port);
						textarea.appendText(msg);
						startbtn.setText("server ����");
					});
				}
			});
			//Scene��ü�� �ʿ�
			Scene scene = new Scene(root); 
			primaryStage.setScene(scene);
			primaryStage.setTitle("Multi Client Echo Server");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {		
		launch();
	}

}

class Client {
	Socket socket;

	public Client(Socket socket) {
		super();
		this.socket = socket;
		receive();
	}
	
	// �ݺ������� Ŭ���̾�Ʈ�κ��� �޽����� �޴� �޼ҵ�
	public void receive() {
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				try {
					while(true) {
						InputStream in = socket.getInputStream();
						byte[] buffer = new byte[512];
						
						int length = in.read(buffer);
						if(length == -1) throw new IOException();
						System.out.println("�޽��� ���� ����" + socket.getRemoteSocketAddress() + " : " + Thread.currentThread().getName());
						String msg = new String(buffer, 0, length, "UTF-8");
						for(Client client : Exam02_EchoServer.clients) {
							client.send(msg);
						}
					}
				} catch (Exception e) {
					try {
						System.out.println("�޽��� ���� ����" + socket.getRemoteSocketAddress() + " : " + Thread.currentThread().getName());
						Exam02_EchoServer.clients.remove(Client.this);
						socket.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		};
	}
	
	// �ش� Ŭ���̾�Ʈ���� �޽����� �����ϴ� �޼ҵ�
	public void send(String msg) {
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				try {
					OutputStream out = socket.getOutputStream();
					byte[] buffer = msg.getBytes("UTF-8");
					out.write(buffer);
					out.flush();
				} catch (Exception e) {
					try {
						System.out.println("�޽��� �۽� ����" + socket.getRemoteSocketAddress() + " : " + Thread.currentThread().getName());
						Exam02_EchoServer.clients.remove(Client.this);
						socket.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		};
		Exam02_EchoServer.executorservice.submit(r);
	}
	
	
}
