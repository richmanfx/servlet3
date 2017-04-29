package ru.r5am.classes;

import com.codeborne.selenide.Configuration;
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

        String pageLink = "http://www.vhfdx.ru/component/option,com_fabrik/Itemid,307/";
        int widthBrowser = 1900;
        int heightBrowser = 950;

        Logger log = LogManager.getLogger(Vhfdx.class);
        timeInit(log);
        log.info("Начало работы.");

        browserInitialize(widthBrowser, heightBrowser, log);

        // Открыть сайт
        open(pageLink);

        // Отсортировать по позывному
        $(By.xpath("//th/a[@id='farbikOrder_jos_fabrik_formdata_30.call']")).click();

        List<String> overallResult = new ArrayList<>();     // Все строки с позывными со всех страниц

        while(true) {
            // Считать данные со страницы
            String[] strings = readDateFromPage();

            // Добавить к общему результату
            for (String str : strings) {
                if (str != null)
                    overallResult.add(str);
                else
                    break;
            }

            // Перейти на следующую страницу если есть ссылка
            if($(By.xpath("//a[@class='pagenav' and @title='Следующая']")).exists()) {
                $(By.xpath("//a[@class='pagenav' and @title='Следующая']")).click();
            } else {
                break;
            }

        }

        close();    // Зкрыть браузер


        /// Обработка результов

        // Генерация матрицы диапазонов
        List<String> stringWithBandMatrix = makeBandMatrix(overallResult);

        // Вычисление расстояния

        // Вычисление азимута

        // Вычисление обратного азимута



        // Данные для темплейта
        List<String> outputInTemplate = new LinkedList<>();
        for(String str: stringWithBandMatrix) {
            str = str.replace("||", "</td><td>");
            str = "<tr><td>" + str + "</td></tr>";
            outputInTemplate.add(str);
        }

        pageContext.put("tableStrings", outputInTemplate);
        return pageContext;
    }

    /**
     *
     * @param overallResult Коллекция строк с сайта с разделителем полей
     * @return Новая коллекция строк с матрицей диапазонов
     */
    private List<String> makeBandMatrix(List<String> overallResult) {

        List<String> outputStringWithBandMatrix = new LinkedList<>();

        for(String str: overallResult) {

            String bandString = "";
            // Парсим диапазоны
            String bands = str.split("\\|\\|")[2];
            String info;
            try {       // Если ячейка с 'Дополнительной информацией' пуста
                info = str.split("\\|\\|")[3];
            } catch (ArrayIndexOutOfBoundsException ex) {
                info = "-&nbsp;-&nbsp;-";
            }

            if(bands.contains("144МГц"))
                bandString += "2м";
            else
                bandString += "-&nbsp;-&nbsp;-";

            if (bands.contains("432МГц"))
                bandString += "||70см";
            else
                bandString += "||-&nbsp;-&nbsp;-";

            if (bands.contains("1296МГц"))
                bandString += "||23см";
            else
                bandString += "||-&nbsp;-&nbsp;-";

            if (bands.contains("5.7ГГц"))
                bandString += "||5см";
            else
                bandString += "||-&nbsp;-&nbsp;-";

            if (bands.contains("10ГГц"))
                bandString += "||3см";
            else
                bandString += "||-&nbsp;-&nbsp;-";

            if (bands.contains("24ГГц"))
                bandString += "||1.2см";
            else
                bandString += "||-&nbsp;-&nbsp;-";

            outputStringWithBandMatrix.add(
                    str.split("\\|\\|")[0]  +
                    "||" + str.split("\\|\\|")[1] +
                    "||" + bandString +
                    "||" + info
            );
        }
        return outputStringWithBandMatrix;
    }


    /**
     * Считывает данные участников со страницы сайта
     */
    private String[] readDateFromPage() {

        // Количество позывных на странице
        String callCountOnPage = $(By.xpath("//td[contains(text(),'Всего')]")).text().split(" ")[3];
        System.out.printf("Позывных на странице: %s\n", callCountOnPage);

        // Позывные
        String[] calls = $$(By.xpath("//tr[@class='oddrow0 fabrik_row ' or " +
                                                       "@class='oddrow1 fabrik_row ']" +
                                                       "/td[contains(@class,'call')]")).texts().toArray(new String[0]);
        // Квадраты
        String[] qra = $$(By.xpath("//tr[@class='oddrow0 fabrik_row ' or " +
                                                       "@class='oddrow1 fabrik_row ']" +
                                                       "/td[contains(@class,'qra')]")).texts().toArray(new String[0]);
        // Диапазоны
        String[] bands = $$(By.xpath("//tr[@class='oddrow0 fabrik_row ' or " +
                                                       "@class='oddrow1 fabrik_row ']" +
                                                       "/td[contains(@class,'band')]")).texts().toArray(new String[0]);
        // Дополнительная информация
        String[] info = $$(By.xpath("//tr[@class='oddrow0 fabrik_row ' or " +
                                                       "@class='oddrow1 fabrik_row ']" +
                                                       "/td[contains(@class,'info')]")).texts().toArray(new String[0]);

        // Преобразовать в заглавные буквы позывные и квадраты
        String[] callsArrayUpCase = Arrays.stream(calls).map(String::toUpperCase).toArray(String[]::new);
        String[] qraArrayUpCase = Arrays.stream(qra).map(String::toUpperCase).toArray(String[]::new);

        // Удалить пробелы из позывных
        String[] callsArrayUpCaseNoSpaces = Arrays.stream(callsArrayUpCase)
                .map(s -> s.replaceAll(" ", ""))
                .toArray(String[]::new);

        String[] stringFromPage = new String[100];
        for (int i=0; i<calls.length; i++) {
            
             stringFromPage[i] = String.format("%s||%s||%s||%s",
                    callsArrayUpCaseNoSpaces[i],
                    qraArrayUpCase[i],
                    bands[i],
                    info[i]
             );
        }

        return stringFromPage;
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
//        Configuration.browser = "phantomjs";
        Configuration.browser = "chrome";

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
