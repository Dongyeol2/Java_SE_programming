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
	Button connbtn, disconnbtn; // 서버 접속, 종료 버튼
	TextField idTf, msgTf;			  // 아이디 입력칸, 메시지 입력칸
	
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	// 클라이언트쪽 Thread 1개만 만들진다. ThreadPool을 사용할 경우 overhead발생
	ExecutorService executorService = Executors.newCachedThreadPool();
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg);
		});
	}
	
	// 서버로부터 들어오는 메시지를 계속 받아서 화면에 출력하기 위한 용도의 Thread
	class ReceiveRunnable implements Runnable {
		// 서버로부터 들어오는 메시지를 받아들이는 역할 수행
		// 소켓에 대한 입력스트림만 있으면 된다.
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
		//Stage arg0 -> window를 지칭함
		//화면구성해서 window 띄우는 코드
		//화면기본 layout을 설정 => 화면을 동서남북중앙(5개의 영역)으로 분리
		BorderPane root = new BorderPane();
		//BorderPane의 크기를 설정=> 화면을 띄우는 window의 크기설정
		root.setPrefSize(700, 500);
		//Component생성해서 BorderPane에 부착
		textarea = new TextArea();
		root.setCenter(textarea);
		
		connbtn = new Button("Server에 접속");
		connbtn.setPrefSize(250, 50);
		connbtn.setOnAction(t->{
			 if (connbtn.getText().equals("Server에 접속")) {
				 try {
						//서버쪽에 Socket접속을 시도
						socket = new Socket("127.0.0.1",6789);
						System.out.println("**서버 연결 OK**");
						//접속에 성공하면 socket객체를 하나 획득
						InputStreamReader isr = new InputStreamReader(socket.getInputStream());
						br = new BufferedReader(isr);
						out = new PrintWriter(socket.getOutputStream());
						printMsg("채팅 서버 접속 성공\n");
						// 접속을 성공했으니 이제 Thread를 만들어서 서버가 보내준
						// 데이터를 받을 준비를 한다.
						ReceiveRunnable runnable = new ReceiveRunnable(br);
						executorService.execute(runnable);
				 } catch(Exception e) {
					 	System.out.println(e);
				 }
			 }
		});
		
		disconnbtn = new Button("접속 종료");
		disconnbtn.setPrefSize(250, 50);
		disconnbtn.setOnAction(t->{
			 try {
				 out.println("/EXIT");
				 out.flush();
				 printMsg("서버 접속 종료");
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
			out.println(msg);	// 서버로 문자열 전송
			out.flush();
			msgTf.setText("");
	    });

		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane에 버튼 추가
		flowpane.getChildren().add(connbtn);
		flowpane.getChildren().add(disconnbtn);	
		flowpane.getChildren().add(idTf);
		flowpane.getChildren().add(msgTf);
		root.setBottom(flowpane);
		
		//Scene객체가 필요
		Scene scene = new Scene(root); 
		primaryStage.setScene(scene);
		primaryStage.setTitle("Network 예제 입니다.");
		primaryStage.show();
	}
	
	public static void main(String[] args) {		
		launch();
	}

}
