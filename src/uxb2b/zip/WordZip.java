package uxb2b.zip;

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

public class WordZip {

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            
            // zip output file:副檔名可直接命名為docx
            FileOutputStream f = new FileOutputStream("build/word/test.docx");
            
            // 
            // CheckedOutputStream ch = new CheckedOutputStream(f, new CRC32());
            // Adler32
            CheckedOutputStream ch = new CheckedOutputStream(f, new Adler32());
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
                    ch));

            // zip input folder
            File file = new File("word_template");
            
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
    public static void fileZip(ZipOutputStream out, File file)
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
            
            // 因直接讀取資料夾時,壓縮後會多一層parent folder.
            // 所以在這做了特別的處理來取得欲壓縮的目錄及檔案.
            String yyy = file.getPath().substring(file.getPath().indexOf("\\")+1);
            System.out.println("yyy:" + yyy);
            
            out.putNextEntry(new ZipEntry(yyy));
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
            
            in.close();
        }
        
    }

}
