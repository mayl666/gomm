package com.gome.monitoringplatform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class sadfadf {
	public static void main(String[] args) {
		String file = "C:\\Users\\fangjinwei\\Desktop\\map.txt";
		try {
			// read file content from file
			StringBuffer sb = new StringBuffer("");
			FileInputStream fileInputStream = new FileInputStream(new File(file));
			InputStreamReader inputStream = new InputStreamReader(fileInputStream, "GB2312");
			BufferedReader br = new BufferedReader(inputStream);
			String str = null;
			
			int i=0;
			StringBuffer ss=new StringBuffer();
			
			while ((str = br.readLine()) != null) {
				//'东莞': [113.8953,22.901],
				String tttt=str.trim();
//				System.out.println(tttt);
				String[] oj=tttt.split(":");
				//'东莞'--->东莞
				String name=oj[0].substring(1, oj[0].length()-1);
//				System.out.print(name);
				//[113.8953,22.901],-->113.8953,22.901
				String ip=oj[1].trim().substring(1, oj[1].trim().length()-2);
//				System.out.println(ip);
				String[] ipList=ip.split(",");
//				System.out.print("  x:"+ipList[0]);
//				System.out.println("  Y:"+ipList[1]);
//			INSERT INTO `mo_map_coordinate` VALUES ('1', 'sdf', '0.9999', '0.9999');
				ss.append("INSERT INTO `mo_map_coordinate` VALUES (");
				ss.append("'"+i+"',").append("'"+name+"市',").append("'"+ipList[0]+"',").append("'"+ipList[1]+"');\r\n");
				++i;
			}
			br.close();
			inputStream.close();
			fileInputStream.close();
//			// write string to file
//			FileWriter writer = new FileWriter("c://test2.txt");
//			BufferedWriter bw = new BufferedWriter(writer);
//			bw.write(sb.toString());
//
//			bw.close();
//			writer.close();
			
			System.out.println(ss.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
