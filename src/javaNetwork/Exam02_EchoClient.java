package javaNetwork;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class Exam02_EchoClient extends Application{
	Socket socket;
    TextArea textarea;
    
    
    // Ŭ���̾�Ʈ �۵� ���� �޼ҵ�
	public void startClient(String IP, int port) {
    	Thread thread = new Thread() {
    		public void run() {
    			try {
    				socket = new Socket(IP, port);
    				System.out.println("���� ���� ����");
    				receive();
				} catch (Exception e) {
					if(!socket.isClosed()) {
						stopClient();
						System.out.println("���� ���� ����");
						Platform.exit();
					}
				}
    		}
    	};
    	thread.start();
    }
    
	// Ŭ���̾�Ʈ �۵� ���� �޼ҵ�
    public void stopClient() {
    	try {
    		if(socket != null && !socket.isClosed()) {
    			socket.close();
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    // �����κ��� �޽��� ���޹޴� �޼ҵ�
    public void receive() {
    	while(true) {
    		try {
    			InputStream in = socket.getInputStream();
    			byte[] buffer = new byte[512];
    			int length = in.read(buffer);
    			if(length == -1) throw new IOException();
    			String msg = new String(buffer, 0, length, "UTF-8");
    			Platform.runLater(() -> {
    				textarea.appendText(msg);
    			});
			} catch (Exception e) {
				stopClient();
				break;
			}
    	}
    }
    
    // ������ �޽��� �����ϴ� �޼ҵ�
    public void send(String msg) {
    	Thread thread = new Thread() {
    		public void run() {
    			try {
    				OutputStream out = socket.getOutputStream();
    				byte[] buffer = msg.getBytes("UTF-8");
    				out.write(buffer);
    				out.flush();
				} catch (Exception e) {
					stopClient();
				}
    		}
    	};
    	thread.start();
    }
    
    @Override
	public void start(Stage primaryStage) {
    	BorderPane root = new BorderPane();
    	root.setPadding(new Insets(5));
    	
    	HBox hbox = new HBox();
    	hbox.setSpacing(5);
    	
    	TextField userName = new TextField();
    	userName.setPrefWidth(150);
    	userName.setPromptText("�г����� �Է��ϼ���");
    	HBox.setHgrow(userName, Priority.ALWAYS);
    	
    	TextField IPText = new TextField("127.0.0.1");
    	TextField portText = new TextField("5554");
    	portText.setPrefWidth(80);
    	
    	hbox.getChildren().addAll(userName, IPText, portText);
    	root.setTop(hbox);
    
    	textarea = new TextArea();
    	textarea.setEditable(false);
		root.setCenter(textarea);
		
		TextField tf = new TextField();
		tf.setPrefWidth(Double.MAX_VALUE);
		tf.setDisable(true);
		
		tf.setOnKeyPressed(event -> {
			send(userName.getText() + " : " + tf.getText() + "\n");
			tf.setText("");
			tf.requestFocus();
		});
		
		
		Button sendbtn = new Button("����");
		sendbtn.setDisable(true);
		sendbtn.setOnKeyPressed(event -> {
			send(userName.getText() + " : " + tf.getText() + "\n");
			tf.setText("");
			tf.requestFocus();
		});
		
		Button startbtn = new Button("Server����");
		//startbtn.setPrefSize(250, 50);
		startbtn.setOnAction(event -> {
			if(startbtn.getText().equals("Server����")) {
				int port = 5554;
				try {
					port = Integer.parseInt(portText.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(IPText.getText() + " , " + port);
				startClient(IPText.getText(), port);
				Platform.runLater(() -> {
					textarea.appendText("ä�ù� ����\n");
				});
				startbtn.setText("�����ϱ�");
				tf.setDisable(false);
				sendbtn.setDisable(false);
				tf.requestFocus();
			} else {
				stopClient();
				Platform.runLater(() -> {
					textarea.appendText("ä�ù� ������\n");
				});
				startbtn.setText("Server����");
				tf.setDisable(true);
				sendbtn.setDisable(true);
			}
		});
		
		BorderPane pane = new BorderPane();
		
		pane.setLeft(startbtn);
		pane.setCenter(tf);
		pane.setRight(sendbtn);
		root.setBottom(pane);
		//Scene��ü�� �ʿ�
		Scene scene = new Scene(root, 400, 400); 
		primaryStage.setScene(scene);
		primaryStage.setTitle("Network ���� �Դϴ�.");
		primaryStage.setOnCloseRequest(event -> stopClient());
		primaryStage.show();
		
		startbtn.requestFocus();
		
	}
    
    public static void main(String[] arg) {
        launch();
    }
}