import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

// запускаем подключение сокета по известным координатам и нициализируем приём сообщений с консоли клиента
        try(Socket socket = new Socket("localhost", 3345);
            BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            DataInputStream ois = new DataInputStream(socket.getInputStream()); )
        {

            System.out.println("Client connected to socket.");
            System.out.println();
            System.out.println("Username : ");
// проверяем живой ли канал и работаем если живой
            while(!socket.isClosed()){

// ждём консоли клиента на предмет появления в ней данных
                if(br.ready()) {
// данные появились - работаем

                    String clientCommand = null;
                    clientCommand = br.readLine();

// пишем данные с консоли в канал сокета для сервера
                    oos.writeUTF(clientCommand);

                    String entry = ois.readUTF();
                   // System.out.println(entry);
                    //вывод списка файлов
                    while (!entry.equals("Stop")) {
                        System.out.println(entry);
                        entry = ois.readUTF();
                    }

                    String flag = "null";
                    oos.writeUTF(flag);
                    while (!flag.equals("Exit")) {
                        System.out.println("Choose file : ");
                        String filename = br.readLine();
                        oos.writeUTF(filename);
                        User.getFile(socket, filename, ois);
                        Thread.sleep(1000);
                        if (clientCommand.equals("Admin")) {
                            System.out.println("1-create");
                            System.out.println("2-remake");
                            String cr = br.readLine();
                            if (cr.equals("1")) {
                                System.out.println("New name");
                                String newname = br.readLine();
                                oos.writeUTF(newname);
                                  Admin.createFile(newname);
                                String status = br.readLine();
                                if (status.equals("Save")) {
                                    User.sendFile(newname, socket, oos);
                                }
                            } else if (cr.equals("2")) {
                                oos.writeUTF(filename);
                                  Admin.remakeFile(filename);
                                String status = br.readLine();
                                if (status.equals("Save")) {
                                    User.sendFile(filename, socket, oos);
                                }
                               // User.sendFile(filename,socket,  oos);
                            }

                        }
                        System.out.println("Choose flag : ");
                        flag = br.readLine();
                        oos.writeUTF(flag);
                    }
                    System.out.println("Username : ");
// ждём чтобы сервер успел прочесть сообщение из сокета и ответить
                }
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}