import menu.MainMenuPanel;
import effects.*;
import entities.*;
import perks.*;
import weapons.*;
import menu.*;
import game.*;
import java.util.*;
import map.*;
import utils.*;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenuPanel menu = new MainMenuPanel();
            menu.showMenu();
        });
    }
}
