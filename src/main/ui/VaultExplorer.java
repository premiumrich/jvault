package ui;

import exceptions.CryptoException;
import filesystem.Vault;
import filesystem.VaultDirectory;
import filesystem.VaultEntry;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

// Displays navigation controls, address bar, and vault file explorer
public class VaultExplorer extends BorderPane {

    private Vault vault;
    private Button back;
    private TextField address;
    private ListView<String> list;
    private VaultDirectory currentDir;

    // EFFECTS: adds all elements to pane
    public VaultExplorer() {
        addAddressBar();
        addFileExplorer();
    }

    // MODIFIES: this
    // EFFECTS: adds navigation controls and address bar to pane
    private void addAddressBar() {
        HBox addressBar = new HBox();
        addressBar.setPadding(new Insets(2, 15, 0, 10));

        back = new Button("←");
        back.setDisable(true);
        addressBar.getChildren().add(back);

        address = new TextField();
        address.setEditable(false);
        address.setMouseTransparent(true);
        address.setDisable(true);
        HBox.setHgrow(address, Priority.ALWAYS);
        addressBar.getChildren().add(address);

        this.setTop(addressBar);
    }

    // MODIFIES: this
    // EFFECTS: adds file explorer to pane
    private void addFileExplorer() {
        list = new ListView<>();
        list.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                for (VaultEntry vaultEntry : currentDir.getEntries()) {
                    if (vaultEntry.getName().equals(list.getSelectionModel().selectedItemProperty().get())
                            && vaultEntry.getClass().equals(VaultDirectory.class)) {
                        updateList((VaultDirectory) vaultEntry);
                        break;
                    }
                }
            }
        });
        this.setCenter(list);
    }

    // MODIFIES: this, FxApp.getWindow()
    // EFFECTS: if name is null, loads existing vault at dir
    //          else, creates new vault with name in dir,
    //          then enables UI elements and populates list of files
    protected void loadVault(String name, File directory, String password) {
        try {
            if (name == null) {     // Load vault
                vault = new Vault(directory, password.toCharArray());
            } else {                // Create vault
                vault = new Vault(new File(directory, name), password.toCharArray());
            }
        } catch (IOException e) {
            FxApp.getWindow().statusBar.showError("IO error when loading vault: " + e.getMessage());
            return;
        } catch (CryptoException e) {
            FxApp.getWindow().statusBar.showError("Crypto error when loading vault: " + e.getMessage());
            return;
        }
        updateList(vault.getRoot());
        back.setDisable(false);
        address.setDisable(false);
        FxApp.getWindow().menuBar.setStateVaultMenuItems(true);
        FxApp.getWindow().statusBar.showStatus("Loaded vault \"" + vault.getVaultFolder().getName() + "\"");
    }

    // MODIFIES: this, FxApp.getWindow()
    // EFFECTS: creates new VaultDirectory under currentDir with name and updates list
    protected void createDir(String name) {
        try {
            vault.createDir(name, currentDir);
            updateList(currentDir);
            FxApp.getWindow().statusBar.showStatus("Created new folder \"" + name + "\" under \"/"
                    + vault.getRoot().getPathOfEntry(currentDir.getId(), true));
        } catch (IOException e) {
            FxApp.getWindow().statusBar.showError("IO error when creating folder: " + e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: sets current path and repopulates list of files
    private void updateList(VaultDirectory dir) {
        currentDir = dir;
        address.setText("/" + vault.getRoot().getPathOfEntry(currentDir.getId(), true));
        list.getItems().clear();
        list.getItems().addAll(
                dir.getEntries().stream().map(VaultEntry::getName).collect(Collectors.toList()));
    }

}
