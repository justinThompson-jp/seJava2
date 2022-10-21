package Connoisseur;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;
import java.io.*;

public class ViewFile {
	
	private static void FileAttributes(){
		Scanner userIn = new Scanner(System.in);
    	String pathname;
        BasicFileAttributes attr;
        
        System.out.println("Insert file path: ");
        pathname = userIn.nextLine();
        userIn.close();
        
        Path path = Paths.get(pathname);
        try {
         attr = Files.readAttributes(path, BasicFileAttributes.class);
         System.out.println("Creation time: " + attr.creationTime());
         System.out.println("Last access time: " + attr.lastAccessTime());
         System.out.println("Last modified time: " + attr.lastModifiedTime());
         System.out.println("File size: " + attr.size() + " bytes");
        } catch (IOException e) {
         System.out.println("Invalid file path: " + e.getMessage());
        }
	}
    public static void main(String[] args) throws IOException {
    	FileAttributes();
    }
}