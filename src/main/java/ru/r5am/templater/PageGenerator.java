package ru.r5am.templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import static freemarker.template.Configuration.VERSION_2_3_26;

public class PageGenerator {
    private static Configuration cfg = new Configuration(VERSION_2_3_26);
    private static final String TEMPLATES_DIR = "templates";

    public static String getPage(String filename, Map<String, Object> data) {

        StringWriter outputStream = new StringWriter();
        cfg.setClassLoaderForTemplateLoading(PageGenerator.class.getClassLoader(), TEMPLATES_DIR);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);

        try {
            final Template templateName = cfg.getTemplate(filename);
            templateName.process(data, outputStream);
        } catch (IOException | TemplateException e) {
            System.err.println("Ошибка при выводе в темплейт-файл: " + filename);
            e.printStackTrace();
        }
        return outputStream.toString();
    }
}
