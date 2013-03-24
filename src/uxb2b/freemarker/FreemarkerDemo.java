package uxb2b.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerDemo {

    public static void main(String[] args) {
        
        // Freemarker configuration object
        Configuration cfg = new Configuration();
        try {
            // Load template from source folder
            Template template = cfg
                    .getTemplate("src/uxb2b/freemarker/ftl/helloworld.ftl");

            // Build the data-model
            Map<String, Object> data = new HashMap<String, Object>();
            
            // set message
            data.put("message", "Hello World!");

            // List parsing
            List<String> countries = new ArrayList<String>();
            countries.add("India");
            countries.add("United States");
            countries.add("Germany");
            countries.add("France");

            data.put("countries", countries);

            // Console output
            Writer out = new OutputStreamWriter(System.out);
            template.process(data, out);
            out.flush();

            // File output
            Writer file = new FileWriter(new File(
                    "build/ftl/FTL_helloworld.txt"));
            template.process(data, file);
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

}