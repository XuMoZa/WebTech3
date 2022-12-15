import java.awt.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class Admin {
    public static void createFile(String str) {
        File newFile = new File(str);
        try
        {
            boolean created = newFile.createNewFile();
            if(created)
                System.out.println("File has been created");
            Desktop desktop = Desktop.getDesktop();
            if(newFile.exists())         //checks file exists or not
                desktop.open(newFile);              //opens the specified file
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void remakeFile(String str) {
        File newFile = new File(str);
        try
        {
            Desktop desktop = Desktop.getDesktop();
            if(newFile.exists())         //checks file exists or not
                desktop.open(newFile);              //opens the specified file

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
