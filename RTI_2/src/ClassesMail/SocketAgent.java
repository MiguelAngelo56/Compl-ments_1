/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesMail;

import java.io.Serializable;
import java.net.Socket;

/**
 *
 * @author Miguel
 */
public class SocketAgent implements Serializable{
    
    private Socket socket;
    private Agent agent;
    
    public Agent getAgent(){
        return agent;
    }
    
    public Socket getSocket(){
        return socket;
    }
    
    public void setSocket(Socket sock){
        socket = sock;
    }
    
    public void setAgent(Agent ag){
        agent = ag;
    }
    
    public SocketAgent(){
        agent = null;
        socket = null;
    }
    
    public SocketAgent(Socket s, Agent a){
        agent = a;
        socket = s;
    }
}
