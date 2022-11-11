package Connoisseur;

/* Source: https://stackoverflow.com/questions/16952727/how-to-use-readattributes-method 
 * Justin Thompson
 */

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.io.*;

public class ViewFile {
	
	public static ArrayList<String> mdata = null;
	
	public static ArrayList<String> FileAttributes(String fName){
    	String pathname;
    	mdata = new ArrayList<String>();
        BasicFileAttributes attr;
        
        pathname = fName;
        
        Path path = Paths.get(pathname);
        try {
         attr = Files.readAttributes(path, BasicFileAttributes.class);
         mdata.add(attr.creationTime() + "");
         mdata.add(attr.lastAccessTime() + "");
         mdata.add(attr.lastModifiedTime() + "");
         mdata.add(attr.size() + " bytesT");
         //System.out.println(mdata);
         
         for (int i = 0; i < mdata.size(); i++) {
        	 String s = mdata.get(i);
        	 String t = s.substring(0, s.indexOf("T"));
        	 mdata.set(i, t);
        	 //System.out.println(mdata.get(i));
         }
        } catch (IOException e) {
         System.out.println("Invalid file path: " + e.getMessage());
        }
        
        return mdata;
	}
   
	/*
	public static void main(String[] args) throws IOException {
    	FileAttributes();
    	System.out.println(mdata);
    }
    */
}