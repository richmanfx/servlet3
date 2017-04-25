package ru.r5am.classes;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.codeborne.selenide.Selenide.*;

/**
 * Created by Aleksandr Jashhuk (R5AM) on 18.04.17.
 *
 */

public class Vhfdx {

    public Map<String, Object> vhfdxGrab() throws IOException {

        Map<String, Object> pageContext = new HashMap<>();
        List<String> tableStrings = new LinkedList<>();            // Коллекция строк талицы в темплейте

        Set<OutInfoString> outInfoStringSet = new HashSet<>();

        String pageLink = "http://www.vhfdx.ru/component/option,com_fabrik/Itemid,307/";
        int widthBrowser = 1850;
        int heightBrowser = 900;

        Logger log = LogManager.getLogger(Vhfdx.class);
        timeInit(log);
        log.info("Начало работы.");

        browserInitialize(widthBrowser, heightBrowser, log);

        // Открыть страницу
        open(pageLink);

        // Позывные
        ElementsCollection callsElements = $$(By.xpath("//tr[@class='oddrow0 fabrik_row ' or " +
                                                       "@class='oddrow1 fabrik_row ']" +
                                                       "/td[contains(@class,'call')]"));
        // Квадраты
        ElementsCollection qraElements = $$(By.xpath("//tr[@class='oddrow0 fabrik_row ' or " +
                                                       "@class='oddrow1 fabrik_row ']" +
                                                       "/td[contains(@class,'qra')]"));
        // Диапазоны
        ElementsCollection bandsElements = $$(By.xpath("//tr[@class='oddrow0 fabrik_row ' or " +
                                                       "@class='oddrow1 fabrik_row ']" +
                                                       "/td[contains(@class,'band')]"));
        // Дополнительная информация
        ElementsCollection infoElements = $$(By.xpath("//tr[@class='oddrow0 fabrik_row ' or " +
                                                       "@class='oddrow1 fabrik_row ']" +
                                                       "/td[contains(@class,'info')]"));

        // В массивы
        String[] callsArray = callsElements.texts().toArray(new String[0]);
        String[] qraArray = qraElements.texts().toArray(new String[0]);
        String[] bandsArray = bandsElements.texts().toArray(new String[0]);
        String[] infoArray = infoElements.texts().toArray(new String[0]);

        // Преобразовать в заглавные буквы
        String[] callsArrayUpCase = Arrays.stream(callsArray).map(String::toUpperCase).toArray(String[]::new);
        String[] qraArrayUpCase = Arrays.stream(qraArray).map(String::toUpperCase).toArray(String[]::new);



        close();

        for (int i=0; i<callsElements.size(); i++) {
            outInfoStringSet.add(new OutInfoString(
                    callsArrayUpCase[i],
                    qraArrayUpCase[i],
                    bandsArray[i],
                    infoArray[i])
            );
        }

        for (OutInfoString ois: outInfoStringSet) {
            tableStrings.add(ois.get());
        }

        pageContext.put("tableStrings", tableStrings);
        return pageContext;
    }

    /**
     * Инициализирует Chrome браузер с определённым размером окна
     * @param width Ширина окна браузера.
     * @param height Высота окна браузера.
     */
    private void browserInitialize(int width, int height, Logger logger) throws IOException {

        // Если мы в Windows
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            ClassLoader classLoader = getClass().getClassLoader();
            URL url = classLoader.getResource("webdrivers" + File.separator + "chromedriver.exe");
            if (url == null) {
                logger.error("Не обнаружен файл \"chromedriver.exe\"!");
            } else {
                System.setProperty("webdriver.chrome.driver", url.getPath());
            }

            url = classLoader.getResource("webdrivers" + File.separator + "phantomjs.exe");
            if (url == null) {
                logger.error("Не обнаружен файл \"phantomjs.exe\"!");
            } else {
                System.setProperty("webdriver.phantomjs.driver", url.getPath());
                System.setProperty("browser", "phantomjs");
            }

            // phantomjs.binary.path
        }




        Configuration.browserSize = String.format("%dx%d", width, height);
        Configuration.browser = "phantomjs";
    }


    /**
     * Форматирует дату и время, выставляет временнУю зону Москвы.
     * @param log Logger для сообщений в логи.
     */
    private static void timeInit(Logger log) {
        TimeZone tz = TimeZone.getTimeZone("Europe/Moscow");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        dateFormat.setTimeZone(tz);
        log.info("Инициализация времени: " + dateFormat.format(new Date()));
    }
}
