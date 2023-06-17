import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Narzedzia {
    // Tworzenie JLabel z obrazkiem
    public static JLabel wczytajObraz(String resource){
        BufferedImage image;
        try{
            InputStream inputStream = Narzedzia.class.getResourceAsStream(resource);
            image = ImageIO.read(inputStream);
            return new JLabel(new ImageIcon(image));
        }catch(Exception e){
            System.out.println("Error: " + e);
        }
        return null;
    }

    public static void updateImage(JLabel imageContainer, String resource){
        BufferedImage image;
        try{
            InputStream inputStream = Narzedzia.class.getResourceAsStream(resource);
            image = ImageIO.read(inputStream);
            imageContainer.setIcon(new ImageIcon(image));
        }catch(IOException e){
            System.out.println("Error: " + e);
        }
    }

    public static Font stworz_Czcionke(String resource){
        // znajduje scieżkę pliku z czcionką
        String filePath = Narzedzia.class.getClassLoader().getResource(resource).getPath();

        // sprawdza puste przestrzenie w ścieżkach (czy występuje błąd)
        if(filePath.contains("%20")){
            filePath = filePath.replaceAll("%20", " ");
        }

        // swtórz czcionkę
        try{
            File plik_wlasnej_Czcionki = new File(filePath);
            Font wlasna_Czcionka = Font.createFont(Font.TRUETYPE_FONT, plik_wlasnej_Czcionki);
            return wlasna_Czcionka;
        }catch(Exception e){
            System.out.println("Error: " + e);
        }
        return null;
    }

    public static String Ukryj_Slowa(String Slowo){
        String UkryteSlowo = "";
        for(int i = 0; i < Slowo.length(); i++){
            if(!(Slowo.charAt(i) == ' ')){
                UkryteSlowo += "*";
            }else{
                UkryteSlowo += " ";
            }
        }
        return UkryteSlowo;
    }
}
