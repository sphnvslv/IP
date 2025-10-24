package Lab3;
import java.util.Arrays;
import java.util.List;
public class Main {
    public static void main(String[] args) {

        PyrotechnicsList listStorage = new PyrotechnicsList();
        PyrotechnicsMap mapStorage = new PyrotechnicsMap();
        FileReader fileReader = new FileReader();
        FileWriter fileWriter = new FileWriter();
        Menu menu = new Menu(listStorage, mapStorage, fileReader, fileWriter);
        createAndRunMenu(menu);
    }

    private static void createAndRunMenu(Menu menu) {
        menu.showMenu();
    }
}