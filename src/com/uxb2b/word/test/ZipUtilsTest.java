package com.uxb2b.word.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.uxb2b.word.main.utils.ZipUtils;

public class ZipUtilsTest {
    
    private static Log log = LogFactory.getLog(ZipUtilsTest.class);

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
            // File file = new File("word_template");
            File file = new File("src/com/uxb2b/word/main/template/sql_table/");
            
            ZipUtils.fileZip(out, file, "sql_table");
            
            out.close();
        } catch (Exception e) {
            log.error("e:" + e);
        }
    }

}
