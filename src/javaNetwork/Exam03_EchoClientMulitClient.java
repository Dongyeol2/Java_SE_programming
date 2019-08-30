package javaNetwork;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

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

public class Exam03_EchoClientMulitClient extends Application{
	TextArea textarea;
	Button startbtn, stopbtn; // 서버 접속, 종료 버튼
	TextField tf;
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	
	public static void main(String[] args) {		
		launch();
	}
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg);
		});
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
		
		startbtn = new Button("Server에 접속");
		startbtn.setPrefSize(250, 50);
		startbtn.setOnAction(t->{
			 if (startbtn.getText().equals("Server에 접속")) {
				 try {
						//서버쪽에 Socket접속을 시도
						socket = new Socket("127.0.0.1",7777);
						System.out.println("**서버 연결 OK**");
						//접속에 성공하면 socket객체를 하나 획득
						InputStreamReader isr = new InputStreamReader(socket.getInputStream());
						br = new BufferedReader(isr);
						out = new PrintWriter(socket.getOutputStream());
						printMsg("접속 성공\n");
						startbtn.setText("접속 종료");
						
					}catch(Exception e) {
						System.out.println(e);
					}
	            } else if (startbtn.getText().equals("접속 종료")) {
	                try {
	                    printMsg("서버에 접속해제하였습니다\n");
	                    startbtn.setText("JOIN");
	                    socket.close();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
		});
		
		tf = new TextField();
		tf.setPrefSize(200, 40);
		tf.setOnKeyPressed((event) -> {
			if (event.getCode() == KeyCode.ENTER) {
				try {
					String msg = tf.getText();
					out.println(msg);
					out.flush();
					try {
						String result = br.readLine();
						printMsg(result+"\n");
					}catch (Exception e) {
						e.printStackTrace();
					}
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });

		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane에 버튼 추가
		flowpane.getChildren().add(startbtn);
	
		flowpane.getChildren().add(tf);
		root.setBottom(flowpane);
		
		//Scene객체가 필요
		Scene scene = new Scene(root); 
		primaryStage.setScene(scene);
		primaryStage.setTitle("Network 예제 입니다.");
		primaryStage.show();
	}

}
