package tk.avabin.JavaUpdater.appcode;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import net.lingala.zip4j.exception.ZipException;
import tk.avabin.JavaUpdater.uppcode.CheckVersions;
import tk.avabin.JavaUpdater.uppcode.Update;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    private Button checkButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button installButton;
    // TODO: 03.02.2016 INSTALL MODPACK
    @FXML
    private TextArea textarea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        checkButton.setOnAction(event -> {
            System.out.println("checkVersion button clicked");
            checkButton.setDisable(true);
            CheckVersions checkVersions = new CheckVersions();
            Update update = new Update();
            Runnable check = () -> {
                try {
                    Boolean areModsUpToDate = checkVersions.checkVersion(true);
                    Boolean areConfigsUpToDate = checkVersions.checkVersion(false);
                    if (areConfigsUpToDate) textarea.appendText("\n" +
                            "Posiadasz aktualny plik konfiguracyjny.");
                    else {
                        textarea.appendText("\n" +
                                "Nie posiadasz aktualnego pliku konfiguracyjnego.\n" +
                                "Nastąpi automatyczna jego aktualizacja.");
                        update.updateConfig();
                    }
                    if(areModsUpToDate) textarea.appendText("\n" +
                            "Posiadasz aktualną wersję paczki modów.");
                    else {
                        textarea.appendText("\n" +
                                "Nie posiadasz aktualnej paczki modów. Możesz ją zaktualizować");
                        updateButton.setDisable(false);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            check.run();
        });

        updateButton.setOnAction(event -> {
            Runnable upd = () -> {
                System.out.println("update button clicked");
                updateButton.setDisable(true);
                Update update = new Update();
                try {
                    textarea.appendText("\n" +
                            "Aktualizuję plik konfiguracyjny");
                    update.updateConfig();
                    textarea.appendText("\n" +
                            "Aktualizuję paczkę modów");
                    update.updatePack();
                } catch (IOException | ZipException e) {
                    e.printStackTrace();
                }
            };
            upd.run();
            textarea.appendText("\n" +
                    "Paczka zaktualizowana.");
        });

        installButton.setOnAction(event -> System.out.println("Install button clicked"));
    }
}
