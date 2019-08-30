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
	
	TextArea textarea;				// 메시지 창
	Button startbtn, stopbtn; // 서버 접속, 종료 버튼
	ServerSocket server;		// 클라이언트의 접속을 받아들이는 객체
	
	// Thread pool 생성
	// resource가 부족해서 Thread Pool 제한하기위해서는 FixedThreadPool 사용
	// 그게 아니라면 CachedThreadPool 사용한다.
	ExecutorService executorService = Executors.newCachedThreadPool(); 
	// singleton형태의 공유객체를 생성
	SharedObject sharedObject = new SharedObject();
	
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg + "\n");
		});
	}
	
	//클라이언트와 연결된 Thread가 사용하는 공유 객체 만들기 위한
	// 클래스를 선언	
	// inner class형태로 선언. (장점 : 사용하기 편하다. 단점 : 재사용성 부족)
	// Thread의 공유 객체는 Thread가 가지고 있어야 하는 자료구조,
	// 기능을 구현해 놓은 객체를 지칭한다.
	class SharedObject {
		// 클라이언트 Thread를 저장하고 있어야 한다.
		List<ClientRunnable> clients = new ArrayList<ClientRunnable>();
		// 우리가 필요한 기능은 Broadcast 기능
		// Thread가  클라이언트로부터 데이터를 받아서 모든 client 에게 전달하는 기능을 구현
		// 공유객체의 method는 여러 Thread에 의해서 동시에 사용될 수 있다.
		// 이런 경우에는 동기화 처리를 해 줘야 문제없이 출력될 수 있다.
		public void broadcast(String msg) {
			clients.stream().forEach(t -> {
				t.out.println(msg);
				t.out.flush();
			});
		}
	}
	
	// 클라이언트와 매핑되는 Thread를 만들기위한 Runnable class
	class ClientRunnable implements Runnable {
		private SharedObject sharedobject = new SharedObject(); //공유 객체
		private Socket socket;	// 클라이언트와 매핑되는 socket
		private BufferedReader br;
		private PrintWriter out;
		
		// 클라이언트가 서버에 접속해서 해당 클라이언트에 대한 Thread가 생성될 때
		// Thread에는 2개의 객체가 전달되어야 한다.
		// 생성자를 2개의 인자(공유객체와 소켓)을 받는 형태로 작성
		// 일반적으로 생성자는 필드 초기화를 담당하기 때문에 Stream을 생성
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
	
		// 클라이언트의 Thread가 시작되면 run methos()가 수행된다.
		// 클라이언트와 데이터 통신을 시작
		// 반복적으로 클라이언트에 데이터를 받아서 공유객체를 이용해서
		// broadcasting
		@Override
		public void run() {
			String msg = "";
			try {
				while((msg = br.readLine()) != null) {
					// 클라이언트가 채팅을 종료하면
					if(msg.equals("/EXIT")) {
						break;
					}
					// 일반적인 채팅 메시지일 경우 모든 클라이언트에게 전달
					sharedObject.broadcast(msg);
				}
			} catch (Exception e) {

			}
		}
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Stage arg0 -> window를 지칭함
		//화면구성해서 window 띄우는 코드
		//화면기본 layout을 설정 => 화면을 동서남북중앙(5개의 영역)으로 분리
		BorderPane root = new BorderPane();
		//BorderPane의 크기를 설정=> 화면을 띄우는 window의 크기설정
		root.setPrefSize(700, 500);
		//Component생성해서 BorderPane에 부착
		textarea = new TextArea();
		root.setCenter(textarea);
		
		startbtn = new Button("Chat Server 시작");
		startbtn.setPrefSize(250, 50);
		startbtn.setOnAction(t->{
			textarea.clear();
			printMsg("[채팅서버기동 - 6789]");
			// 서버 소켓을 만들어서 클라이언트 접속을 기다린다.
			// JavaFX Thread가 blocking되지 않도록 새로운 Thread를 만들어서
			// 클라이언트 접속을 기다려야 한다.
			Runnable runnable = new Runnable() {
				public void run() {
					// 실제 서버가 해야하는 작업
					try {
						server = new ServerSocket(6789);
						while(true) {
							printMsg("[Client 접속 대기중]");
							Socket socket = server.accept();
							printMsg("[Client 접속 성공]");
							// client가 접속했으니 Thread를 하나 생성하고 실행해야 한다.
							ClientRunnable cRunnable = new ClientRunnable(sharedObject, socket);
							// 새로운 client가 접속되었으니 공용객체의 ArrayList안에 새로운 client 객체가 들어가야 한다.
							// Thread에 의해서 공용객체의 데이터가 사용될 때는 동기화 처리를 해 줘야 안전하다.
							sharedObject.clients.add(cRunnable);
							printMsg("[현재 client 수 : " + sharedObject.clients.size() +"]");
							executorService.execute(cRunnable);
						}
					} catch (Exception e) {
						System.out.println(e.toString());
					}
				}
			};
			executorService.execute(runnable);
		});
		
		stopbtn = new Button("Chat Server 종료");
		stopbtn.setPrefSize(250, 50);
		stopbtn.setOnAction(t->{
			
		});
		
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane에 버튼 추가
		flowpane.getChildren().add(startbtn);
		flowpane.getChildren().add(stopbtn);
		root.setBottom(flowpane);
		
		//Scene객체가 필요
		Scene scene = new Scene(root); 
		primaryStage.setScene(scene);
		primaryStage.setTitle("Multi Client Echo Server");
		primaryStage.show();
	}
	
	public static void main(String[] args) {		
		launch();
	}

}
