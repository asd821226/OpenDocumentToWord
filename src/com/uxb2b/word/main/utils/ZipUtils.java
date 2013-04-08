package com.uxb2b.word.main.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ZipUtils {
    
    private static Log log = LogFactory.getLog(ZipUtils.class);
    
    /**
     * @param out
     * @return
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void fileZip(ZipOutputStream out, File file, String templateName)
            throws IOException {
        
        //
        if (file.isDirectory()) {
            //
            log.info("Dir_Name:" + file.getName());
            for (File tmp : file.listFiles()) {
                fileZip(out, tmp, templateName);
            }
        } else {
            //
            log.info("path:" + file.getPath());
            log.info("    " + "File name:" + file.getName());
            //
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "ISO8859_1"));
            
            // 因直接讀取資料夾時, 會依所傳作的file path來壓縮檔案階層,
            // 所以會造成有多層的檔案目錄.
            // 因此在這做了特別的處理來取得欲壓縮的目錄及檔案.
            String zipPath = file.getPath().substring(file.getPath().indexOf(templateName) + templateName.length() + 1);
            log.info("    zip path:" + zipPath);
            
            out.putNextEntry(new ZipEntry(zipPath));
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
            
            in.close();
        }
        
    }

}
