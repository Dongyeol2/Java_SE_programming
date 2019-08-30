package javaNetwork;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Exam01_DateServer {

	public static void main(String[] args) {
		//서버쪽 프로그램은 클라이언트의 소켓 접속을 대기구현
		//ServerSocket class를 이용해서 구현
		ServerSocket server = null;
		//클라이언트와 접속된 후 Socket 객체가 있어야지 클라이언트와 데이터 통신이 가능
		Socket socket = null;
		
		try {
			//port번호를 가지고 ServerSocket객체를 생성
			//0~65535 사용가능 0~1023까지는 예약되어 있음
			server = new ServerSocket(5554);
			System.out.println("클라이언트 접속대기");
			socket = server.accept(); //클라이언트 접속대기 block
			//클라이언트가 접속해 오면 Socket객체를 하나 리턴함
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd"); 
			out.print(format.format(new Date()));
			//일반적으로 Reader와 Writer는 내부 buffer를 가지고 있음
			out.flush();//내부 buffer를 비우고 데이터를 전달명령
			out.close();
			socket.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
