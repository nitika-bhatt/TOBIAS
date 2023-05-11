 import java.net.*;
 import java.io.*;
 
 class Client 
 {
    Socket socket;
   
   
    BufferedReader br;
    PrintWriter out;
    public Client()

    {



        try {
            System.out.println("Sending request to server");
            socket=new Socket("127.0.0.1",7777);
            System.out.println("Connection done");


            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
        
             startReading();
             startWriting();
            
        } catch (Exception e) {
            // TODO: handle exception
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
                if(msg.equals("Bye"))
                {
                    System.out.println("Server termnated the chat");
                    break;
                }
            

            System.out.println("Server:" +msg);
        }
    }
        catch( Exception e)
        {
            e.printStackTrace();

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
                System.out.println("Connection closed!!");
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
        System.out.println("This is Client");
        new Client();
    }
    
}
