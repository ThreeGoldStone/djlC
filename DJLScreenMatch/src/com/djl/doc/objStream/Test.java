package com.djl.doc.objStream;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author DJL E-mail:
 * @date 2016-1-25 下午5:17:51
 * @version 1.0
 * @parameter
 * @since
 */
public class Test {
	public static void main(String[] args) {
//		try {
//			Obj obj = new Obj();
//			obj.add();
//			ByteArrayOutputStream objOuput = ObjOuput(obj);
//			FileOutputStream fos = new FileOutputStream(new File("ObjCache"));
//			fos.write(objOuput.toByteArray());
//			fos.flush();
//			fos.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		 try {
		 ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new
		 File("ObjCache")));
		 Obj obj = (Obj) ois.readObject();
		 System.out.println(obj.toString());
		 } catch (FileNotFoundException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 } catch (IOException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 } catch (ClassNotFoundException e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
	}

	// /* 浅复制 */
	// public Object clone() throws CloneNotSupportedException {
	// Prototype proto = (Prototype) super.clone();
	// return proto;
	// }

	// /* 深复制 */
	// public Object deepClone() throws IOException, ClassNotFoundException {
	//
	// /* 写入当前对象的二进制流 */
	// ByteArrayOutputStream bos = ObjOuput();
	//
	// /* 读出二进制流产生的新对象 */
	// ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
	// ObjectInputStream ois = new ObjectInputStream(bis);
	// return ois.readObject();
	// }

	private static ByteArrayOutputStream ObjOuput(Object obj) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(obj);
		return bos;
	}
}
