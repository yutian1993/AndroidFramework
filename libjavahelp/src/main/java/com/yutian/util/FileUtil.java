package com.yutian.util;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class FileUtil {

	//Set default decode
	private static String STRCODE = "UTF-8";
	
	/**
	 * Use to get file object
	 * @param filename file path
	 * @return File object or null object
	 */
	protected static File getFile(String filename)
	{
		File curFile = new File(filename);
		if (curFile != null && curFile.exists())
			return curFile;
		else
			return null;
	}
	
	/**
	 * Use to get file all lines
	 * @param filename file path
	 * @return Line List
	 */
	public static List<String> getFileLines(String filename)
	{
		List<String> lines = new ArrayList<String>();
		File openfileFile = getFile(filename);
		if (openfileFile != null) {
			try {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(openfileFile)));
				String input = null;
				while ((input = bufferedReader.readLine()) != null) {
					lines.add(new String(input.getBytes(), STRCODE));
				}
				bufferedReader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lines;
	}
	
	/**
	 * Use to get all file content
	 * @param filename file path
	 * @param hasline has line information("content '\n'")
	 * @return
	 */
	public static String getFileContent(String filename, boolean hasline)
	{
		String fileContent = new String();
		
		//Add line sign
		String lineSign = new String("");
		if (hasline)
			lineSign = "\n";
		
		List<String> fileLines = getFileLines(filename);
		
		for (String string : fileLines) {
			fileContent = fileContent + string + lineSign;
		}
		
		return fileContent;
	}
	
	/**
	 * Output string file content
	 * @param filecontent file content
	 */
	public static void outputFileContent(String filecontent)
	{
		System.out.println(filecontent);
	}
	
	/**
	 * Output string file lines
	 * @param filelines file line list
	 */
	public static void outputFileContent(List<String> filelines)
	{
		String filecontent = "";
		
		for (String string : filelines) {
			filecontent += string + "\n";
		}
		
		outputFileContent(filecontent);
	}
	
	/**
	 * Set file code
	 * @param code URF-8 || GB2312
	 */
	public static void setFileCode(String code)
	{
		if (code == null || (code != null && code.equals("")))
			return;
		FileUtil.STRCODE = code;
	}

	public static String getFileEncodeing(String filepath)
	{
		String encodeing = "UTF-8";
		byte[] buf = new byte[4096];
		try {
			java.io.FileInputStream fis = new java.io.FileInputStream(filepath);
			if (fis != null)
			{
				// (1)
				UniversalDetector detector = new UniversalDetector(null);

				// (2)
				int nread;
				while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
					detector.handleData(buf, 0, nread);
				}

				// (3)
				detector.dataEnd();

				// (4)
				encodeing = detector.getDetectedCharset();
//				if (encodeing != null) {
//					System.out.println("Detected encodeing = " + encodeing);
//				} else {
//					System.out.println("No encodeing detected.");
//				}

				// (5)
				detector.reset();

				detector = null;
			}
		} catch (FileNotFoundException e) {
			System.out.println("Detect file not found");
		} catch (IOException e) {
			System.out.println("File IO error");
		}

		return encodeing;

	}

	public static void testrenamefunction()
	{
		File newfile = new File("F:\\Task\\Sep\\tst");

		if (newfile.isDirectory()) {
			File[] childfile = newfile.listFiles();
			String name = "pic";
			int cnt = 1;
			for (File temp:
					childfile) {
				if (temp.isFile()) {
					System.out.print(temp.getName());
//					System.out.println(temp.getAbsolutePath());
					System.out.print("----->");
					String newfilename = newfile.getAbsolutePath() + "\\" + String.format("pic%03d", cnt) + ".jpg";
					newfilename = newfilename.replace("\\", "/");
					cnt++;
					System.out.println(newfilename);
					if (temp.renameTo(new File(newfilename))) {
						System.out.println("Success");
					} else {
						System.out.println("Fail");
					}
				}
			}
		}
	}

}
