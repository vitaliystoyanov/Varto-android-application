package es.esy.varto_novomyrgorod.varto.network;

public interface APIUrl {

    String BASE_URL = "http://varto.esy.es/api/";
    String URL_SCHEDULE = BASE_URL + "timetable/get.php";
    String URL_NEWS = BASE_URL + "news/get.php";
    String URL_CATALOGS = BASE_URL + "shares/catalog.php";
    String URL_GOODS = BASE_URL + "shares/get.php";

}
