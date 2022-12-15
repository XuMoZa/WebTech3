
import java.net.*;
import java.io.*;
public class User {
    public static void getFile(Socket s,String str,DataInputStream ois) {
        try {
            try(FileWriter writer = new FileWriter(str, false)) {
                // считаем сначала первую строку
                String line = ois.readUTF();
                while (!line.equals("End")) {
                    System.out.println(line);
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
    public static void sendFile(String str, Socket client, DataOutputStream out) {
        try {
            File file = new File(str);
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


}
