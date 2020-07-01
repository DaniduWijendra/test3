import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class chatServer
{
    private static final int port=9001;
    private static HashSet<String> names=new HashSet<String>();// to uniquly identify clients
    private static HashSet<PrintWriter> writers=new HashSet<PrintWriter>();//unique clint's field 

    public chatServer() {
    }

    

 
    public static void main(String args[])throws Exception{

        System.out.println("chat server is runing");

        ServerSocket listener=new ServerSocket(port);//create server socket

        try{
                while(true)
                {
                    Socket socket=listener.accept();
                    Thread handlerThread=new Thread(new handler(socket));
                    handlerThread.start();
                }
        }
        finally{
            listener.close();
        }

    }

    //handle the brodcasts with clients
    private static class handler implements Runnable{
        private String name;
        private Socket socket ;
        private BufferedReader in;//get input
        private PrintWriter out;//get output

        public handler(Socket socket) {

            this.socket=socket;
        }

        public void run()
        {
            try{

                in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out=new PrintWriter(socket.getOutputStream(), true);

                while(true)
                {
                        out.println("SUBMITNAME");
                        name=in.readLine();

                        if(name==null)
                        {
                            return;
                        }
                        if(!names.contains(names))//if not contain this name in hashset then it include
                        {
                            names.add(name);
                            break;
                        }

                        out.println("NAMEACCEPTED");
                        writers.add(out);

                        while(true)//to hide the messages for nonlogin users
                        {
                            String input =in.readLine();
                            if(input==null){
                                return;
                            }
                            for(PrintWriter writer:writers)
                            {
                                writer.println("message"+name+":"+input);
                            }
                        }
                }
            }
            catch(IOException e)
            {
                    System.out.println(e);
            }
            finally
            {
                if(names!=null)
                {
                    names.remove(name);
                }
                if(out!=null)
                {
                    writers.remove(out);
                }

                try{
                        socket.close();

                }
                catch(IOException e)
                {

                }
            }
        }

 
                    
 

    }


}