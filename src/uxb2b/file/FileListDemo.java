package uxb2b.file;

import java.io.File;

public class FileListDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        String pathname = ".";
        File file = new File(pathname);
        listFile(file);

    }

    public static void listFile(File file) {
        if (file.isDirectory()) {
            System.out.println("Dir:" + file.getName());
            for (File tmp : file.listFiles()) {
                // checkFileType(tmp, zos, tmp.getName());
                listFile(tmp);
            }
        } else {
            System.out.println("    " + "File name:" + file.getName());
        }

    }

}
