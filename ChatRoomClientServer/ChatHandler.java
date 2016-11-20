import java.net.*;
import java.io.*;
import java.util.*;

public class ChatHandler extends Thread {

   private Socket socket;
   private DataInputStream in;
   private DataOutputStream out;
   private String name;
   protected static Vector handlers = new Vector ();

   public ChatHandler (String name, Socket socket) throws IOException {
      this.name = name;
      this.socket = socket;
      in = new DataInputStream (
		  new BufferedInputStream(socket.getInputStream()));
      out = new DataOutputStream (
		  new BufferedOutputStream (socket.getOutputStream()));
   }

   // this program is to be continued in the next page

   // this program continues from the previous page

   public void run () {
      try {
         handlers.addElement (this);
			if(handlers.size()==2){
				broadcast("GameStart");
			}
         while (true) {


            String message = in.readUTF ();
			broadcastToOtherOne(message);



         }
      } catch (IOException ex) {
         System.out.println("-- Connection to user " + name + " lost.");
      } finally {
         handlers.removeElement (this);
         broadcast(name+" left");
         try {
            socket.close ();
         } catch (IOException ex) {
            System.out.println("-- Socket to user already closed ?");
         }
      }
   }

   // this program is to be continued in the next page

   // this program continues from the previous page

   public static void broadcast (String message) {
      synchronized (handlers) {
         Enumeration e = handlers.elements ();
         while (e.hasMoreElements ()) {
            ChatHandler handler = (ChatHandler) e.nextElement ();
				try {
				   handler.out.writeUTF (message);
				   System.out.println(message);
				   handler.out.flush ();
				} catch (IOException ex) {
				   handler.stop ();
				}
         }
      }
   }
   public  void broadcastToOtherOne (String message) {
         synchronized (handlers) {
            Enumeration e = handlers.elements ();
            while (e.hasMoreElements ()) {
               ChatHandler handler = (ChatHandler) e.nextElement ();
               if(handler.name.equals(name)){continue;}
   				try {
   				   handler.out.writeUTF (message);
   				   System.out.println(message);
   				   handler.out.flush ();
   				} catch (IOException ex) {
   				   handler.stop ();
   				}
            }
         }
   }
}
