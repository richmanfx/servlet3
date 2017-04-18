package ru.r5am.servlets;

import ru.r5am.classes.Vhfdx;
import ru.r5am.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aleksandr Jashhuk (R5AM) on 18.04.17.
 * Сервлет для скрапинга УКВ квадратов.
 */

@WebServlet(
        name = "ServletVhfdx",                                      // Имя сервлета
        description = "Сервлет для скрапинга УКВ квадратов.",       // Описание сервлета
        urlPatterns = {"/vhfdx"},         // Один или несколько шаблонов URL для сервлета, использовать только один
        loadOnStartup = 1                 // Первым грузить при старте веб-контейнера
)
public class ServletVhfdx extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        Vhfdx vhfdx = new Vhfdx();
        Map<String, Object> pageContext = vhfdx.vhfdxGrab();


        response.setContentType("text/html");
        response.setCharacterEncoding("UTF8");
        String page = PageGenerator.getPage("vhfdx1.html", pageContext);
        response.getWriter().println(page);

    }


}
