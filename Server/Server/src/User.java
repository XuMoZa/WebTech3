
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public class User {
    public static void sendFile(String str, Socket client, DataOutputStream out) {
        try {
            File file = new File("C:/mywork/VT/WT3/Server/Server/Arhiv/"+str);
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            while (line != null) {
                out.writeUTF(line);
               // System.out.println(line);
                // считываем остальные строки в цикле
                line = reader.readLine();
            }
            out.writeUTF("End");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void createnew(String str){
        File newFile = new File("C:/mywork/VT/WT3/Server/Server/Arhiv/"+str);
        try
        {
            boolean created = newFile.createNewFile();
            if(created)
                System.out.println("File has been created");
                        //opens the specified file
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        }
    public static void getFile(Socket s,String str,DataInputStream ois) {
        try {
            try(FileWriter writer = new FileWriter("C:/mywork/VT/WT3/Server/Server/Arhiv/"+str, false)) {
                // считаем сначала первую строку
                String line = ois.readUTF();
                while (!line.equals("End")) {
                    writer.write(line+"\n");
                    // считываем остальные строки в цикле
                    line = ois.readUTF();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void chooseFile(Socket socket,DataOutputStream out) {
        String s = null;
        File dir = new File("C:/mywork/VT/WT3/Server/Server/Arhiv");
        if (dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                System.out.println(item.getName() + " файл");
                try {// Поток вывода данных
                        out.writeUTF (item.getName()); // Отправить содержимое файла
                      //  out.flush (); // снова прочитать n байтов
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            out.writeUTF("Stop");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
