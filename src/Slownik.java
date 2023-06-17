import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList; // tabela do której można dodawać i usuwać
import java.util.Arrays;
import java.util.HashMap; //słownik w python
import java.util.Random;

public class Slownik {
    // bedzie posiadał zestaw klucz -> kategoria i wartosc -> słowo
    private HashMap<String, String[]> ListaSlow;

    // wybiera losową kategorię na podstawie nazwy kategorii
    private ArrayList<String> kategorie;

    public Slownik(){
        try{
            ListaSlow = new HashMap<>();
            kategorie = new ArrayList<>();

            // pobiera ścieżkę pliku
            String filePath = getClass().getClassLoader().getResource(Stale_Dane.Slownik_Sciezka).getPath();
            if(filePath.contains("%20")) filePath = filePath.replaceAll("%20", " ");
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            // iteruje przez każdą linię w data.txt
            String linia;
            while((linia = reader.readLine()) != null){
                // dzieli słowa za pomocą ","
                String[] parts = linia.split(",");

                // pierwsze słowo każdej linii reprezentuje kateogorię
                String kategoria = parts[0];
                kategorie.add(kategoria);

                // reszta słów po pierwszym będzie zawartością kategorii
                String values[] = Arrays.copyOfRange(parts, 1, parts.length);
                ListaSlow.put(kategoria, values);
            }
        }catch(IOException e){
            System.out.println("Error: " + e);
        }
    }

    public String[] Wczytaj_gre(){
        Random rand = new Random();

        // generuje losową cyfrę do wybrania kategorii
        String category = kategorie.get(rand.nextInt(kategorie.size()));

        // generuje losową cyfrę żeby wybrać wartość z kategorii
        String[] categoryValues = ListaSlow.get(category);
        String word = categoryValues[rand.nextInt(categoryValues.length)];

        // [0] -> to kategoria, a [1] -> słowo
        return new String[]{category.toUpperCase(), word.toUpperCase()};
    }
}
