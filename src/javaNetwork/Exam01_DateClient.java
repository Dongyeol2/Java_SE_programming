package javaNetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam01_DateClient extends Application{
	TextArea textarea;
	Button btn;
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
		
		btn = new Button("Date 서버 접속");
		btn.setPrefSize(250, 50);
		btn.setOnAction(t->{
			//버튼에서 Action이 발생했을때 호출
			try {
				//서버쪽에 Socket접속을 시도
				Socket socket = new Socket("127.0.0.1",5554);
				//접속에 성공하면 socket객체를 하나 획득
				InputStreamReader isr = new InputStreamReader(socket.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				String msg = br.readLine();
				printMsg(msg);
				
				br.close();
				isr.close();
				socket.close();
				
			}catch(Exception e) {
				System.out.println(e);
			}
		});
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		//flowpane에 버튼 추가
		flowpane.getChildren().add(btn);
		root.setBottom(flowpane);
		
		//Scene객체가 필요
		Scene scene = new Scene(root); 
		primaryStage.setScene(scene);
		primaryStage.setTitle("Network 예제 입니다.");
		primaryStage.show();
	}

}
