import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
       // Server sr = new Server();
//  стартуем сервер на порту 3345
        try (ServerSocket server= new ServerSocket(3345)){

            System.out.print("Server start.");
// становимся в ожидание подключения к сокету под именем - "client" на серверной стороне
            Socket client = server.accept();

// после хэндшейкинга сервер ассоциирует подключающегося клиента с этим сокетом-соединением
            System.out.print("Connection accepted.");
// инициируем каналы для  общения в сокете, для сервера
// канал записи в сокет
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            // канал чтения из сокета
            DataInputStream in = new DataInputStream(client.getInputStream());

// начинаем диалог с подключенным клиентом в цикле, пока сокет не закрыт
            while(!server.isClosed()){
                System.out.println("Server reading from channel");

// сервер ждёт в канале чтения (inputstream) получения данных клиента
                // подключение пользователя
                System.out.println("Wait username:");
                String entry = in.readUTF();
                System.out.println(entry);
                // вывод файлов(базы)
                User.chooseFile(client, out);

                    // получение имени выбранного файла
                String flag = in.readUTF();
                while (!flag.equals("Exit")) {
                    String filename = in.readUTF();
                    System.out.println("File : " + filename);

                    User.sendFile(filename, client, out);
                    Thread.sleep(1000);

                    if (entry.equals("Admin")) {
                        filename = in.readUTF();
                        User.createnew(filename);
                        User.getFile(client, filename, in);
                    }

                    flag = in.readUTF();
                }
// после получения данных считывает их

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}