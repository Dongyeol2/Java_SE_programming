package javaIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

class MyClass implements Serializable{
	// 직렬화와 역직렬화를 할때 같은 타입인지 비교하기 위해서 내부적으로 사용
	private static final long serialVersionUID = 1L;
	//필드도 직렬화가 가능한 형태여야 함
	String name;
	int kor;
	//transient: 직렬화 대상에서 제외 시킬때 사용
	transient Socket socket;//Socket 직렬화가 안됨
	
	public MyClass() {}

	public MyClass(String name, int kor) {
		super();
		this.name = name;
		this.kor = kor;
	}
	
}
public class Exam04_Serialization {

	public static void main(String[] args) {
		//ObjectOutStream을 이용해서 File에 class instance를 저장
		//1. 저장할 객체를 생성
		MyClass obj = new MyClass("홍길동",70);
		//2. 객체를 저장할 파일 객체를 생성
		File file = new File("asset/student.txt");
		//3. 파일 객체를 이용해서 ObjectOutputStream을 생성
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			//4. ObjectOutputStream을 이용해서  객체를 파일에 저장
			// 저장될 개체는 반드시 직렬화가 가능한 객체이어야 함
			// => Serializable interface 구현한 클래스로 생성, 
			// 구현한 클래스내에 모든 필드가 직렬화가 가능해야 함
			
			oos.writeObject(obj);
			//5. 저장이 끝나면 Stream을 close
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
