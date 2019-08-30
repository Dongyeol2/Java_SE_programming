package javaIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 * JavaFX를 이용해서 GUI 프로그램을 구현
 * 화면에 창 띄우려면 Application이라는 class의 instance를 생성
 * 
 */
public class Exam02_NotePad extends Application {//is a 관계

	TextArea textarea;
	Button openBtn, saveBtn;
	File file;
	public static void main(String[] args) {
		launch();
	}
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg+"\n");
		});	
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Stage primaryStage - window를 지칭하는 객체
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 500);
		
		textarea = new TextArea();
		root.setCenter(textarea);
		
		openBtn = new Button("파일 열기");
		openBtn.setPrefSize(150, 50);
		openBtn.setOnAction(t->{
			textarea.clear();//textarea안의 내용을 초기화
			FileChooser chooser = new FileChooser();
			chooser.setTitle("오픈할 파일을 선택하세요");
			//파일 chooser로부터 오픈할 파일에 대한  reference를 획득
			file = chooser.showOpenDialog(primaryStage);
			//file객체로부터 input stream 오픈
			try {
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String line = "";
				while((line = br.readLine()) != null) {
					printMsg(line);									
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		});
		saveBtn = new Button("파일 저장");
		saveBtn.setPrefSize(150, 50);
		saveBtn.setOnAction(t->{
			String content = textarea.getText();
			try {
				FileWriter fw = new FileWriter(file);
				fw.write(content);
				fw.close();//반드시 close처리
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("파일저장");
				alert.setHeaderText("File Save!!");
				alert.setContentText("파일에 내용이 저장되었습니다.");
				alert.showAndWait();//확인버튼 누를때 까지 대기
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		});
		
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		flowpane.getChildren().add(openBtn);
		flowpane.getChildren().add(saveBtn);
		root.setBottom(flowpane);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
