package me.meloni.SolarLogAPI.FileInteraction;

import me.meloni.SolarLogAPI.Handling.APIInitializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.NotDirectoryException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WorkingDirectory {
    private static File workingDirectory = null;

    public static File getDirectory() throws URISyntaxException, IOException {
        if(workingDirectory == null) {
            File jar = new File(APIInitializer.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            Date time = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
            String run = dateFormat.format(time);
            workingDirectory = new File(jar.getParent(), run);
            if(!workingDirectory.mkdirs()) {
             throw new IOException("cant create " + run);
            }
        }
        return workingDirectory;
    }

    public static void clear() throws IOException, URISyntaxException {
        File file = getDirectory();
        for (File subFile : file.listFiles()) {
            if(subFile.isDirectory()) {
                deleteFolder(subFile);
            } else {
                if(!subFile.delete()) {
                    throw new IOException("Can't delete " + subFile);
                }
            }
        }
    }

    public static void setDirectory(File directory) throws FileNotFoundException, NotDirectoryException {
        if(directory.isDirectory()) {
            if(!directory.exists()) {
                throw new FileNotFoundException();
            }
        } else throw new NotDirectoryException(directory.getName() + "is no direcory.");
    }

    private static void deleteFolder(File file) throws IOException {
        for (File subFile : file.listFiles()) {
            if(subFile.isDirectory()) {
                deleteFolder(subFile);
            } else {
                if(!subFile.delete()) {
                    throw new IOException("Can't delete " + subFile.getName());
                }
            }
        }
        if(!file.delete()) {
            throw new IOException("Can't delete " + file.getName());
        }
    }
}
