import java.net.*;
import java.io.*;

class Server {

    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;


    public Server()
    {
        try {
        server= new ServerSocket(7777 );
        System.out.println("Server is ready to accept the connection");
        System.out.println("Waiting");
        socket=server.accept();

        br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out=new PrintWriter(socket.getOutputStream());
        
        startReading();
        startWriting();
    
      }
        catch(Exception e)
        {
            e.printStackTrace();
        }




    }

    public void startReading()
    {
        //thread--->>read krega

        Runnable r1=()->{
            System.out.println("Reader started....");

            try{
            while(true)
            {
                String msg=br.readLine();
                if(msg.equals("bye"))
                {
                    System.out.println("Client termnated the chat");
                    socket.close();
                    break;
                }
            

            System.out.println("Client:" +msg);
        }
    }
        catch( Exception e)
        {
            // e.printStackTrace();
            System.out.println("Connection closed!!");

        }

    
        };
        new Thread(r1).start();

    }

    public void startWriting()
    {
        //thread---> data willbe taken by the user and will send it to the client
        Runnable r2=()->{
            System.out.println("Writer started");
            try{
            while(!socket.isClosed()) 
            {

                    BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                    String content=br1.readLine();
                    
                    out.println(content);
                    out.flush();
                    if(content.equals("bye"))
                    {
                        socket.close();
                        break;
                    }


                }
                System.out.println("Connection closed");
            }
                catch( Exception e)
                {
                    e.printStackTrace();
                }
            };

        
        new Thread(r2).start();
    }

    public static void main(String[] args)
    {
        System.out.println("This is server....");
        new Server();
    }
    
}
