package persistence;


import model.Image;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileImageLoader implements ImageLoader {
    private final File[] files;
    public FileImageLoader(File folder) {
        this.files = folder.listFiles(imageTypes());
    }
    private FileFilter imageTypes() {
        return (File pathname) -> pathname.getName().endsWith(".jpg");
    }
    
    @Override
    public Image load() {
        return imageAt(0);
    }
    
    private Image imageAt(int i) {
        return new Image() {
            @Override
            public String name() {
                return files[i].getName();
            }
            @Override
            public InputStream stream() {
                try {
                    return new BufferedInputStream(new FileInputStream(files[i]));
                } catch (FileNotFoundException e) {
                    Logger.getLogger(FileImageLoader.class.getName()).log(Level.SEVERE, null, e);
                    return null;
                }
            }
            @Override
            public Image next() {
                return i == files.length -1 ? imageAt(0) : imageAt(i+1);
            }
            @Override
            public Image prev() {
                return i == 0 ? imageAt(files.length-1) : imageAt(i-1);
            }
        };
    }
}