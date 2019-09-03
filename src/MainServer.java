import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {

    public static void main(String[] args) throws IOException {

        ServerSocket serverDoorMan = null;             //позволяет слушать подключения
        //Socket socketToClient = null;
        Socket socketToServer = null;                 //отсюда выскакивает соединеие и тянется к серверу

        //String host = "127.0.0.1";
        int port = 6565;                   //2391 перестало работать в какой-то момент

        try{                                                 //для образовательного процесса можно поймать пару ошибочек
            serverDoorMan = new ServerSocket(port);
        } catch(IOException e){
            System.out.println("[-]Can't listen port 6565");  //и написать об этом, так я узнала, что порт 2391 перестал слушать
            System.exit(-1);
        }

        System.out.println("[+]Listening for connection");      //готовы слушать

        try{
            socketToServer = serverDoorMan.accept();            //швейцар открыл дверь, пожали руки
        } catch(IOException e){
            System.out.println("[-]Accept failed");
            System.exit(-1);
        }

        System.out.println("[+]Connection successful");        //соединились, можно и пообщаться
        System.out.println("[+]Listening for input");

        //BufferedReader inServer = new BufferedReader(new InputStreamReader(socketToClient.getInputStream()));          //не очень понятно, зачем это на сайте, наверное как общий вид, но здесь не используется
        //PrintWriter outServer = new PrintWriter(socketToClient.getOutputStream(), true);

        BufferedReader inClient = new BufferedReader(new InputStreamReader(socketToServer.getInputStream()));    //объект чтения того, что прилетело с сервера эхом
        PrintWriter outClient = new PrintWriter(socketToServer.getOutputStream(), true);              //объект чтения того, что говорит клиент серверу

        String input;    //ОГРОМНАЯ ЗАГАДКА: почему при использовании напрямую каждый раз метода inClient.readLine() без дополнительной строки input начиналось веселье с периодичым выводом чётных строк в одну консколь, каждых третьих в другую консоль???

        while ((input = inClient.readLine()) != null){                  //если клиент что-то написал и вообще пишет довольно продолжительно и обильно
            System.out.println(input);          //пишем в консоль сервера то, что сказал клиет
            outClient.println(input);           //пишем в консоль клиента то, что отвечает ему сервер

            if(input == "bye"){          //пора бы и закончить этот диалог
                break;
            }
        }

        outClient.close();         //закрыть все порты и все объекты чтения
        inClient.close();
        //socketToClient.close();
        socketToServer.close();
        serverDoorMan.close();
    }

}
