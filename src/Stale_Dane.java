import java.awt.*;
public class Stale_Dane {
    // klasa zawierające stałe dane, nr.1 ścieżki plików
    public static final String Slownik_Sciezka = "resources/data.txt";
    public static final String Obrazki_Sciezka = "resources/1.png";
    public static final String Czcionka_Sciezka = "resources/FiraCode-Light.ttf";

    // ustawienia kolorów
    public static final Color Glowny_kolor = Color.BLACK;
    public static final Color Drugi_kolor = Color.LIGHT_GRAY;
    public static final Color kolor_Tla = Color.PINK;

    // ustawienia rozmiarów
    public static final Dimension Rozmiar_Ramy_Glownej = new Dimension(600, 760);
    public static final Dimension Rozmiar_Planszy_Przyciskow = new Dimension(Rozmiar_Ramy_Glownej.width, (int)(Rozmiar_Ramy_Glownej.height * 0.42));
    public static final Dimension Rozmiar_Ramki_Wyniku = new Dimension((int)(Rozmiar_Ramy_Glownej.width/1.5), (int)(Rozmiar_Ramy_Glownej.height/5));
}
