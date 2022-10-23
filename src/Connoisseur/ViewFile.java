package Connoisseur;

/* Source: https://stackoverflow.com/questions/16952727/how-to-use-readattributes-method */

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class ViewFile {
	
	private static void FileAttributes(){
		Scanner userIn = new Scanner(System.in);
    	String pathname;
    	ArrayList<String> mdata = new ArrayList<String>();
        BasicFileAttributes attr;
        
        System.out.println("Insert file path: ");
        pathname = userIn.nextLine();
        userIn.close();
        
        Path path = Paths.get(pathname);
        try {
         attr = Files.readAttributes(path, BasicFileAttributes.class);
         mdata.add("Creation time: " + attr.creationTime());
         mdata.add("Last access time: " + attr.lastAccessTime());
         mdata.add("Last modified time: " + attr.lastModifiedTime());
         mdata.add("File size: " + attr.size() + " bytes");
         System.out.println(mdata);
        } catch (IOException e) {
         System.out.println("Invalid file path: " + e.getMessage());
        }
	}
    public static void main(String[] args) throws IOException {
    	FileAttributes();
    }
}