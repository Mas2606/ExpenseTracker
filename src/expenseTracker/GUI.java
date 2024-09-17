package expenseTracker;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI {
    private JFrame frame;
    private JTextField nameField;
    private JTextField datumField;
    private JTextField beschreibungField;
    private JTextField kostenField;
    private JTextArea ausgabenArea;
    private JLabel gesamtLabel;

    private ArrayList<Ausgaben> ausgabenListe;

    public GUI() {
        ausgabenListe = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        // Hauptfenster
        frame = new JFrame("Expense Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Panel für Eingabefelder
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        // Eingabefelder
        inputPanel.add(new JLabel("Name der Ausgabe:"));
        nameField = new JTextField();
        nameField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validateTextInput(nameField, "^[^0-9]*$", "Keine Zahlen im Namen erlaubt!"); }
            public void removeUpdate(DocumentEvent e) { validateTextInput(nameField, "^[^0-9]*$", "Keine Zahlen im Namen erlaubt!"); }
            public void changedUpdate(DocumentEvent e) { validateTextInput(nameField, "^[^0-9]*$", "Keine Zahlen im Namen erlaubt!"); }
        });
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Datum (TT.MM.JJJJ):"));
        datumField = new JTextField();
        datumField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validateTextInput(datumField, "^\\d{0,2}(\\.\\d{0,2})?(\\.\\d{0,4})?$", "Datum muss im Format TT.MM.JJJJ sein!"); }
            public void removeUpdate(DocumentEvent e) { validateTextInput(datumField, "^\\d{0,2}(\\.\\d{0,2})?(\\.\\d{0,4})?$", "Datum muss im Format TT.MM.JJJJ sein!"); }
            public void changedUpdate(DocumentEvent e) { validateTextInput(datumField, "^\\d{0,2}(\\.\\d{0,2})?(\\.\\d{0,4})?$", "Datum muss im Format TT.MM.JJJJ sein!"); }
        });
        inputPanel.add(datumField);

        inputPanel.add(new JLabel("Beschreibung:"));
        beschreibungField = new JTextField();
       
        inputPanel.add(beschreibungField);

        inputPanel.add(new JLabel("Kosten (EUR):"));
        kostenField = new JTextField();
        kostenField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { validateTextInput(kostenField, "^[0-9]*\\.?[0-9]*$", "Nur Zahlen im Kostenfeld erlaubt!"); }
            public void removeUpdate(DocumentEvent e) { validateTextInput(kostenField, "^[0-9]*\\.?[0-9]*$", "Nur Zahlen im Kostenfeld erlaubt!"); }
            public void changedUpdate(DocumentEvent e) { validateTextInput(kostenField, "^[0-9]*\\.?[0-9]*$", "Nur Zahlen im Kostenfeld erlaubt!"); }
        });
        inputPanel.add(kostenField);

        JButton hinzufuegenButton = new JButton("Hinzufügen");
        hinzufuegenButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (validiereEingaben()) {
                    hinzufuegenAusgabe();
                }
            }
        });
        inputPanel.add(hinzufuegenButton);

        // Textfeld für Ausgabenanzeige
        ausgabenArea = new JTextArea();
        ausgabenArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(ausgabenArea);

        // Panel für die Gesamtkostenanzeige
        JPanel gesamtPanel = new JPanel();
        gesamtLabel = new JLabel("Gesamtausgaben: 0.00 EUR");
        gesamtPanel.add(gesamtLabel);

        // Komponenten zum Frame hinzufügen
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(gesamtPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Überprüft die Eingabe und zeigt eine Fehlermeldung an, wenn sie nicht den Anforderungen entspricht
    private void validateTextInput(JTextField textField, String regex, String errorMessage) {
        String input = textField.getText();
        if (!input.matches(regex)) {
            JOptionPane.showMessageDialog(frame, errorMessage, "Ungültige Eingabe", JOptionPane.WARNING_MESSAGE);
            // Entfernt das zuletzt eingegebene Zeichen
            textField.setText(input.substring(0, input.length() - 1));
        }
    }

    private void hinzufuegenAusgabe() {
        // Eingabewerte aus den Feldern holen
        String name = nameField.getText();
        String datum = datumField.getText();
        String beschreibung = beschreibungField.getText();
        double kosten = Double.parseDouble(kostenField.getText());

        // Neue Ausgabe erstellen und zur Liste hinzufügen
        Ausgaben ausgabe = new Ausgaben(name, datum, beschreibung, kosten);
        ausgabenListe.add(ausgabe);

        // TextArea aktualisieren
        ausgabenArea.append(ausgabe.toString() + "\n");

        // Gesamtkosten aktualisieren
        aktualisiereGesamtausgaben();

        // Eingabefelder leeren
        nameField.setText("");
        datumField.setText("");
        beschreibungField.setText("");
        kostenField.setText("");
    }

    private void aktualisiereGesamtausgaben() {
        double gesamt = 0.0;
        for (Ausgaben ausgabe : ausgabenListe) {
            gesamt += ausgabe.getKosten();
        }
        gesamtLabel.setText("Gesamtausgaben: " + String.format("%.2f", gesamt) + " EUR");
    }

 // Methode zur Validierung aller Eingabefelder
    private boolean validiereEingaben() {
        // Name darf keine Zahlen enthalten
        if (!nameField.getText().matches("^[^0-9]*$")) {
            JOptionPane.showMessageDialog(frame, "Keine Zahlen im Namen erlaubt!", "Ungültige Eingabe", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Datum muss im Format TT.MM.JJJJ sein
        if (!datumField.getText().matches("^\\d{2}\\.\\d{2}\\.\\d{4}$")) {
            JOptionPane.showMessageDialog(frame, "Datum muss im Format TT.MM.JJJJ sein!", "Ungültige Eingabe", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Beschreibung darf keine Zahlen enthalten
        if (!beschreibungField.getText().matches("^[^0-9]*$")) {
            JOptionPane.showMessageDialog(frame, "Keine Zahlen in der Beschreibung erlaubt!", "Ungültige Eingabe", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Kosten muss eine Zahl sein
        if (!kostenField.getText().matches("^[0-9]*\\.?[0-9]+$")) {
            JOptionPane.showMessageDialog(frame, "Nur Zahlen im Kostenfeld erlaubt!", "Ungültige Eingabe", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Wenn alles gültig ist
        return true;
    }

        
    }


