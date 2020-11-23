package Interface.BasicUI;

import FileInteraction.GetFile;
import Handling.Logger;
import Handling.SolarMap;
import Interface.SimpleFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BasicSolarMapCustomizer {

    private static JPanel filepanel = new JPanel();
    private JPanel files = new JPanel();
    private SolarMap map = new SolarMap();
    private boolean done = false;
    private List<File> importFiles = new ArrayList<>();
    private List<File> importTars = new ArrayList<>();
    private List<File> dataFiles = new ArrayList<>();

    public BasicSolarMapCustomizer() {
        initPanel();
    }

    public static SolarMap solarMap() {
        JFrame f = new SimpleFrame(filepanel);
        f.setSize(300,500);
        f.setTitle("Customization");
        BasicSolarMapCustomizer basicSolarMapCustomizer = new BasicSolarMapCustomizer();
        while (!basicSolarMapCustomizer.done) {
            System.getSecurityManager();
        }
        f.setVisible(false);
        f.removeAll();
        return basicSolarMapCustomizer.map;
    }

    private void initPanel() {
        filepanel.setLayout(new BorderLayout());

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

        JButton addFile = new JButton("Add File");
        addFile.addActionListener(e -> {
            importFiles.add(GetFile.ValidChosenDataFile());
            repaintList();
        });

        JButton addDirectory = new JButton("Add from Folder");
        addDirectory.addActionListener(e -> {
            try {
                importFiles.addAll(GetFile.ChosenValidFilesInDirectory());
                repaintList();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        JButton addTar = new JButton("Add from tar");
        addTar.addActionListener(e -> {
            importTars.add(GetFile.ChosenTarArchive());
            repaintList();
        });

        JButton addTars = new JButton("Add from tars");
        addTars.addActionListener(e -> {
            try {
                importTars.addAll(GetFile.ChosenTarsInDirectory());
                repaintList();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        JButton addDataFile = new JButton("Add from Data File");
        addDataFile.addActionListener(e -> {
            dataFiles.add(GetFile.ChosenReadLocation());
            repaintList();
        });

        buttons.add(addFile);
        buttons.add(addDirectory);
        buttons.add(addTar);
        buttons.add(addTars);
        buttons.add(addDataFile);

        filepanel.setLayout(new BorderLayout());

        files.setLayout(new BoxLayout(files, BoxLayout.Y_AXIS));

        filepanel.add(files, BorderLayout.CENTER);

        filepanel.add(buttons, BorderLayout.PAGE_START);

        JButton retrain = new JButton("Return");
        filepanel.add(retrain, BorderLayout.PAGE_END);
        retrain.addActionListener(e -> {
            try {
                if(importFiles.size() > 0) {
                    Logger.log("Importing from " + importFiles);
                    map.addImportFromFiles(importFiles);
                }
                if(importTars.size() > 0) {
                    Logger.log("Importing from " + importTars);
                    map.addFromTars(importTars);
                }
                if(dataFiles.size() > 0) {
                    Logger.log("Importing from " + dataFiles);
                    map.addFromDataFiles(dataFiles);
                }
                done = true;
            } catch (Exception ioException) {
                ioException.printStackTrace();
            }
        });


    }

    private void repaintList() {
        files.removeAll();
        files.add(new JLabel("Importing from:"));
        if(importFiles.size() > 0) {
            files.add(new JLabel("Data Files:"));
            for (File importFile : importFiles) {
                files.add(new JLabel(importFile.getName()));
            }
        }
        if(importTars.size() > 0) {
            files.add(new JLabel("Tar Archives:"));
            for (File importTar : importTars) {
                files.add(new JLabel(importTar.getName()));
            }
        }
        if(dataFiles.size() > 0) {
            files.add(new JLabel("Data Files:"));
            for (File dataFile : dataFiles) {
                files.add(new JLabel(dataFile.getName()));
            }
        }
        files.setVisible(false);
        files.setVisible(true);
    }

}
