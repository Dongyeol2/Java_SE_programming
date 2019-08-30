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
	Button startbtn; // 서버 접속, 종료 버튼
	public static ExecutorService executorservice;
	public static Vector<Client> clients = new Vector<Client>();
	
	// 클라이언트의 접속을 받아들이는 서버소켓
	ServerSocket server;
	
	// 서버를 시작해서 클라이언트의 연결 기다리는 메소드
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
						System.out.println("클라이언트 접속 " + socket.getRemoteSocketAddress() + " : " + Thread.currentThread().getName());
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
	
	// 서버의 작동을 중지시키는 메소드
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
			
			startbtn = new Button("server 실행");
			startbtn.setMaxWidth(Double.MAX_VALUE);
			BorderPane.setMargin(startbtn, new Insets(1,0,0,0));
			root.setBottom(startbtn);
			
			String IP = "127.0.0.1";
			int port = 5554;
			
			startbtn.setOnAction(event -> {
				if(startbtn.getText().equals("server 실행")) {
					startServer(IP, port);
					Platform.runLater(() -> {
						String msg = String.format("서버 시작\n", IP, port);
						textarea.appendText(msg);
						startbtn.setText("server 종료");
					});
				} else {
					stopServer();
					Platform.runLater(() -> {
						String msg = String.format("서버 종료\n", IP, port);
						textarea.appendText(msg);
						startbtn.setText("server 실행");
					});
				}
			});
			//Scene객체가 필요
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
	
	// 반복적으로 클라이언트로부터 메시지를 받는 메소드
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
						System.out.println("메시지 수신 성공" + socket.getRemoteSocketAddress() + " : " + Thread.currentThread().getName());
						String msg = new String(buffer, 0, length, "UTF-8");
						for(Client client : Exam02_EchoServer.clients) {
							client.send(msg);
						}
					}
				} catch (Exception e) {
					try {
						System.out.println("메시지 수신 오류" + socket.getRemoteSocketAddress() + " : " + Thread.currentThread().getName());
						Exam02_EchoServer.clients.remove(Client.this);
						socket.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		};
	}
	
	// 해당 클라이언트에게 메시지를 전송하는 메소드
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
						System.out.println("메시지 송신 오류" + socket.getRemoteSocketAddress() + " : " + Thread.currentThread().getName());
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
