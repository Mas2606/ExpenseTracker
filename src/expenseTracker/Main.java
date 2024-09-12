package expenseTracker;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
        // Startet die GUI-Anwendung
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI();
            }
        });
}
}
