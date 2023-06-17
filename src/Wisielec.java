import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent; // reprezentuje zdarzenie akcji takie jak kliknięcie przycisku, wybór z menu
import java.awt.event.ActionListener; /*będziesz tworzyć obiekty klas implementujących ActionListener
i rejestrować je jako nasłuchiwaczy dla odpowiednich komponentów interfejsu użytkownika, aby reagować na zdarzenia akcji generowane przez te komponenty. */

import java.awt.event.WindowAdapter; //
import java.awt.event.WindowEvent;

public class Wisielec extends JFrame implements ActionListener {
    // liczenie niewlasciwych zgadnieci gracza
    private int NieodgadnietaLitera;

    // tutaj przechowuje słowo do zgadnięcia
    private String[] wordChallenge;

    private final Slownik slownik;
    private JLabel Obrazek_Wisielca, Etykieta_Kategorii, Etykieta_Ukrytego_Slowa, Etykieta_Wyniku, Etykieta_Slowa;
    private JButton[] Przyciski_Liter;
    private JDialog Tekst_Wyniku;
    private Font wlasna_Czcionka;


    public Wisielec(){
        super("Projekt Gra Wisielec");
        setSize(Stale_Dane.Rozmiar_Ramy_Glownej);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Stale_Dane.kolor_Tla);

        // init vars
        slownik = new Slownik();
        Przyciski_Liter = new JButton[26];
        wordChallenge = slownik.Wczytaj_gre();
        wlasna_Czcionka = Narzedzia.stworz_Czcionke(Stale_Dane.Czcionka_Sciezka);
        stworz_Tekst_Wyniku();

        addGuiComponents();
    }

    private void addGuiComponents(){
        // Obrazek wisielca
        Obrazek_Wisielca = Narzedzia.wczytajObraz(Stale_Dane.Obrazki_Sciezka);
        Obrazek_Wisielca.setBounds(20, 0, Obrazek_Wisielca.getPreferredSize().width, Obrazek_Wisielca.getPreferredSize().height);

        // wyswietlanie kategorii
        Etykieta_Kategorii = new JLabel(wordChallenge[0]);
        Etykieta_Kategorii.setFont(wlasna_Czcionka.deriveFont(30f));
        Etykieta_Kategorii.setHorizontalAlignment(SwingConstants.CENTER);
        Etykieta_Kategorii.setOpaque(true);
        Etykieta_Kategorii.setForeground(Color.WHITE);
        Etykieta_Kategorii.setBackground(Stale_Dane.Drugi_kolor);
        Etykieta_Kategorii.setBorder(BorderFactory.createLineBorder(Stale_Dane.Drugi_kolor));
        Etykieta_Kategorii.setBounds(
                0,
                Obrazek_Wisielca.getPreferredSize().height - 28,
                Stale_Dane.Rozmiar_Ramy_Glownej.width,
                Etykieta_Kategorii.getPreferredSize().height
        );

        // ukryte słowo
        Etykieta_Ukrytego_Slowa = new JLabel(Narzedzia.Ukryj_Slowa(wordChallenge[1]));
        Etykieta_Ukrytego_Slowa.setFont(wlasna_Czcionka.deriveFont(50f));
        Etykieta_Ukrytego_Slowa.setForeground(Color.WHITE);
        Etykieta_Ukrytego_Slowa.setHorizontalAlignment(SwingConstants.CENTER);
        Etykieta_Ukrytego_Slowa.setBounds(
                0,
                Etykieta_Kategorii.getY() + Etykieta_Kategorii.getPreferredSize().height + 40,
                Stale_Dane.Rozmiar_Ramy_Glownej.width,
                Etykieta_Ukrytego_Slowa.getPreferredSize().height
        );

        // Przyciski liter
        GridLayout rozklad_Przyciskow = new GridLayout(4, 7);
        JPanel panel_Przycisku = new JPanel();
        panel_Przycisku.setBounds(
                -5,
                Etykieta_Ukrytego_Slowa.getY() + Etykieta_Ukrytego_Slowa.getPreferredSize().height,
                Stale_Dane.Rozmiar_Planszy_Przyciskow.width,
                Stale_Dane.Rozmiar_Planszy_Przyciskow.height
        );
        panel_Przycisku.setLayout(rozklad_Przyciskow);

        // Tworzenie przycisków dla liter
        for(char c = 'A'; c <= 'Z'; c++){
            JButton przycisk = new JButton(Character.toString(c));
            przycisk.setBackground(Stale_Dane.Glowny_kolor);
            przycisk.setFont(wlasna_Czcionka.deriveFont(18f));
            przycisk.setForeground(Color.WHITE);
            przycisk.addActionListener(this);

            // wykorzystywanie wartosci ASCII aby policzyć obecny index
            int obecny_Index = c - 'A';

            Przyciski_Liter[obecny_Index] = przycisk;
            panel_Przycisku.add(Przyciski_Liter[obecny_Index]);
        }

        //Przycisk restartu
        JButton Przycisk_Resetu = new JButton("Reset");
        Przycisk_Resetu.setFont(wlasna_Czcionka.deriveFont(10f));
        Przycisk_Resetu.setForeground(Color.WHITE);
        Przycisk_Resetu.setBackground(Stale_Dane.Drugi_kolor);
        Przycisk_Resetu.addActionListener(this);
        panel_Przycisku.add(Przycisk_Resetu);

        //Przycisk wyjścia
        JButton Przycisk_Wyjdz = new JButton("Wyjdz");
        Przycisk_Wyjdz.setFont(wlasna_Czcionka.deriveFont(10f));
        Przycisk_Wyjdz.setForeground(Color.WHITE);
        Przycisk_Wyjdz.setBackground(Stale_Dane.Drugi_kolor);
        Przycisk_Wyjdz.addActionListener(this);
        panel_Przycisku.add(Przycisk_Wyjdz);

        getContentPane().add(Etykieta_Kategorii);
        getContentPane().add(Obrazek_Wisielca);
        getContentPane().add(Etykieta_Ukrytego_Slowa);
        getContentPane().add(panel_Przycisku);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String polecenie = e.getActionCommand();
        if(polecenie.equals("Reset") || polecenie.equals("Restart")){
            reset_gry();

            if(polecenie.equals("Restart")){
                Tekst_Wyniku.setVisible(false);
            }
        }else if(polecenie.equals("Wyjdz")){
            dispose();

        }else{
            // guziki z literami

            // wyłącz guzik po wciśnięciu
            JButton button = (JButton) e.getSource();
            button.setEnabled(false);

            // Sprawdzenie czy ukryte słow zawiera literę wciśniętą przez gracza
            if(wordChallenge[1].contains(polecenie)){
                // zmiana koloru guzika kiedy użytkownik zgadnie
                button.setBackground(Color.GREEN);

                // zapisz ukryte słowo w tablicy znaków, aby zaktualizować ukryty tekst
                char[] hiddenWord = Etykieta_Ukrytego_Slowa.getText().toCharArray();

                for(int i = 0; i < wordChallenge[1].length(); i++){
                    // zmiana znaku pustej literki na literę
                    if(wordChallenge[1].charAt(i) == polecenie.charAt(0)){
                        hiddenWord[i] = polecenie.charAt(0);
                    }
                }

                // update hiddenWordLabel
                Etykieta_Ukrytego_Slowa.setText(String.valueOf(hiddenWord));

                // gracz odgadł poprawnie słowo
                if(!Etykieta_Ukrytego_Slowa.getText().contains("*")){
                    //wyświetla wiadomość w momencie wygranej
                    Etykieta_Wyniku.setText("Gratulacje! Zgadłeś słowo");
                    Tekst_Wyniku.setVisible(true);
                }

            }else{
                // zmiana koloru guzika na czerwony kiedy gracz się pomyli
                button.setBackground(Color.RED);

                // zwieksza licznik błędów
                ++NieodgadnietaLitera;

                // zmiena obrazka wisielca o jeden wiecej
                Narzedzia.updateImage(Obrazek_Wisielca, "resources/" + (NieodgadnietaLitera + 1) + ".png");

                // graczowi nie udało się odgadnąć słowa
                if(NieodgadnietaLitera >= 6){
                    // wyswietla tekst w razie niepowodzenia
                    Etykieta_Wyniku.setText("Nie udało Ci się odgadnąć słowa, spróbuj ponownie");
                    Tekst_Wyniku.setVisible(true);
                }
            }
            Etykieta_Slowa.setText("Slowo: " + wordChallenge[1]);
        }

    }

    private void stworz_Tekst_Wyniku(){
        Tekst_Wyniku = new JDialog();
        Tekst_Wyniku.setTitle("Wynik");
        Tekst_Wyniku.setSize(Stale_Dane.Rozmiar_Ramki_Wyniku);
        Tekst_Wyniku.getContentPane().setBackground(Stale_Dane.kolor_Tla);
        Tekst_Wyniku.setResizable(false);
        Tekst_Wyniku.setLocationRelativeTo(this);
        Tekst_Wyniku.setModal(true);
        Tekst_Wyniku.setLayout(new GridLayout(3, 1));
        Tekst_Wyniku.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                reset_gry();
            }
        });

        Etykieta_Wyniku = new JLabel();
        Etykieta_Wyniku.setForeground(Color.WHITE);
        Etykieta_Wyniku.setHorizontalAlignment(SwingConstants.CENTER);

        Etykieta_Slowa = new JLabel();
        Etykieta_Slowa.setForeground(Color.WHITE);
        Etykieta_Slowa.setHorizontalAlignment(SwingConstants.CENTER);

        JButton restart_Przycisk = new JButton("Restart");
        restart_Przycisk.setForeground(Color.WHITE);
        restart_Przycisk.setBackground(Stale_Dane.Drugi_kolor);
        restart_Przycisk.addActionListener(this);

        Tekst_Wyniku.add(Etykieta_Wyniku);
        Tekst_Wyniku.add(Etykieta_Slowa);
        Tekst_Wyniku.add(restart_Przycisk);
    }

    private void reset_gry(){
        // wczytaj nową grę
        wordChallenge = slownik.Wczytaj_gre();
        NieodgadnietaLitera = 0;

        // wczytaj początkowy obraz
        Narzedzia.updateImage(Obrazek_Wisielca, Stale_Dane.Obrazki_Sciezka);

        // zaktualizuj kategorię
        Etykieta_Kategorii.setText(wordChallenge[0]);

        // zaktualizuj zgadywane słowo
        String hiddenWord = Narzedzia.Ukryj_Slowa(wordChallenge[1]);
        Etykieta_Ukrytego_Slowa.setText(hiddenWord);

        // wszystkie guziki działają ponownie
        for(int i = 0; i < Przyciski_Liter.length; i++){
            Przyciski_Liter[i].setEnabled(true);
            Przyciski_Liter[i].setBackground(Stale_Dane.Glowny_kolor);
        }
    }
}

