package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client
{
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Client()
    {
        try {

            System.out.println("sending request to the server");
            socket=new Socket("localhost", 999);
            System.out.println("connection is done.........");


            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out=new PrintWriter(socket.getOutputStream());

            startRedading();
            startWriteing();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }



    public void startRedading()
    {
        Runnable reading=() ->
        {
            System.out.println("server started to chat...");

            try {
                while (true)
                {
                    String msg = br.readLine();
                    if (msg.equals("exit"))
                    {
                        System.out.println("client is terminated the chat");
                        socket.close();
                        break;
                    }

                    System.out.println("Server: "+msg);

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        };


        new Thread(reading).start();
    }

    public void startWriteing()
    {
        // user se data lekar client ko send karega

        Runnable writing=() -> {


            System.out.println("writer started.......");

            try {
                while (!socket.isClosed())
                {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if (content.equals("exit"))
                    {
                        socket.close();
                        break;
                    }
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException("bye ");
            }



        };

        new Thread(writing).start();




    }





    public static void main(String[] args) {

        System.out.println("Client is start ");
        new Client();

    }
}
