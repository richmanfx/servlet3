package ru.r5am.classes;

/**
 * Created by Aleksandr Jashhuk (R5AM) on 23.04.17.
 * Класс выходной строки с иформацией о корреспондентах.
 */

class OutInfoString {

    OutInfoString(String call, String qra, String bands, String additionalInfo) {
        this.call = call;
        this.qra = qra;
        this.bands = bands;
        this.additionalInfo = additionalInfo;
    }

    private String call;
    private String qra;
    private String bands;
    private String additionalInfo;

    String get() {
        return String.format("<tr> <td>%s</td> <td>%s</td> <td>%s</td> <td>%s</td> </tr>",
                call, qra, bands, additionalInfo);
    }
}
