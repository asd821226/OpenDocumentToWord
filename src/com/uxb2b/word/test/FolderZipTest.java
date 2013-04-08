package com.uxb2b.word.test;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 壓縮比較可參考:
 * http://java-performance.info/java-crc32-and-adler32/
 * 
 * @purpose 測試直接壓縮指定目錄到特定的壓縮名稱中
 * 
 * @author tony
 *
 */
public class FolderZipTest {

    /**
     * @param args
     */
    public static void main(String[] args) {

        File dir1 = new File(".");
        File dir2 = new File("..");
        try {
            String myCurrentDir = System.getProperty("user.dir")
                    + "\\"
                    + System.getProperty("sun.java.command")
                            .substring(0, System.getProperty("sun.java.command").lastIndexOf(".")).replace(".", "\\");
            System.out.println(myCurrentDir);
            System.out.println("Working Directory = " +
                    System.getProperty("user.dir"));
            System.out.println("Current dir : " + dir1.getCanonicalPath());
            System.out.println("Parent  dir : " + dir2.getCanonicalPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            
            // zip output file
            FileOutputStream f = new FileOutputStream("build/test.zip");
            
            // 
            // CheckedOutputStream ch = new CheckedOutputStream(f, new CRC32());
            // Adler32
            CheckedOutputStream ch = new CheckedOutputStream(f, new Adler32());
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    ch));

            // zip input folder
            File file = new File("doc");
            
            fileZip(out, file);
            
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param out
     * @return
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void fileZip(ZipOutputStream out, File file)
            throws UnsupportedEncodingException, FileNotFoundException,
            IOException {
        
        //
        if (file.isDirectory()) {
            //
            System.out.println("Dir:" + file.getName());
            for (File tmp : file.listFiles()) {
                fileZip(out, tmp);
            }
        } else {
            //
            System.out.println("path:" + file.getPath());
            System.out.println("    " + "File name:" + file.getName());
            //
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "ISO8859_1"));
            
            out.putNextEntry(new ZipEntry(file.getPath()));
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
            
            in.close();
        }
        
    }

}
