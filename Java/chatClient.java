import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.*;

public class chatClient {

    BufferedReader in;
    PrintWriter out;
    JFrame frame=new JFrame("Charter App");
    JTextField textField=new JTextField( 40);
    JTextArea messageArea=new JTextArea(8,40);

    public chatClient()
    {
        textField.setEditable(false);
        MessageArea.setEditable(false);
        frame.getContentPane().add(textField,"North");
        frame.getContentPanel().add(new JScrollPane(messageArea),"Center");
        frame.pack();

        textField.addActionListener(new ActionListener(){//when enter is pressed message is sent 

            public void actionPerformed(ActionEvent e)
            {
                out.println(textField.getText());
                textField.setText("");
            }
        });
    }

    private String getServerAddress()
    {
        return JOptionPane.showInputDialog(frame,"Enter the IP adress of the server",
                                                 "Welcome to chatter App By buddhi",
                                                 JOptionPane.QUESTION_MESSAGE);              
    }

    private String getName()
    {
        return JOptionPane.showInputDialog(frame, "choose a screen name:",
                                                "Screen Name Selection",
                                                JOptionPane.PLAIN_MESSAGE);
    }


    private void run()throws IOException
    {
           String serverAddress= getServerAddress();
           Socket socket=new Socket(serverAddress,9001);

           in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
           out=new PrintWriter(socket.getOutputStream(),true);


           while(true)
           {
               String line=in.readLine();

               if(line.startsWith("SUBMITNAME")){
                   out.println(getName());

               }
               else if(line.startsWith("NameACCEPTED"))
               {
                   textField.setEditable(true);
               }
               else if(line.startsWith("MESSAGE"))
               {
                   messageArea.append(line.substring(8)+"\n");
               }
           }
    }

    public static void main(String args[])throws Exception{

        chatClient client=new chatClient();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
    }


}