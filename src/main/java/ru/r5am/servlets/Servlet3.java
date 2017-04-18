package ru.r5am.servlets;

import ru.r5am.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(
        description = "Сервлет для скрапинга УКВ квадратов.",       // Описание сервлета
        name = "Servlet3",                                          // Имя сервлета
        urlPatterns = {"/servlet3"},         // Один или несколько шаблонов URL для сервлета, использовать только один
        loadOnStartup = 2                    // Вторым грузить при старте веб-контейнера
)
public class Servlet3 extends HttpServlet {

    @Override
    protected void doGet(
        HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageContext = new HashMap<>();

        int name = 5;
        pageContext.put("name", name);

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF8");
        String page = PageGenerator.getPage("template1.html", pageContext);
        response.getWriter().println(page);
    }

    @Override
    protected void doPost(
            HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
