/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SNMP;
import java.io.IOException;
import java.util.List;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

/**
 *
 * @author damien
 */
public class SNMP_utility 
{
    public static void SNMP_GET_synchrone(String cible, String oid, String Community) throws IOException
    {
        TransportMapping transport=null; 

        transport = new DefaultUdpTransportMapping();
        transport.listen(); 
        CommunityTarget target = new CommunityTarget(); 
        target.setVersion(SnmpConstants.version1); 
        target.setCommunity(new OctetString(Community));
        
        
        //cible = X.X.X.X/PORT
        Address targetAddress = new UdpAddress(cible);
        // ou Address targetAddress = GenericAddress.parse("udp:127.0.0.1/161"); 
        target.setAddress(targetAddress);
        target.setRetries(2);
        target.setTimeout(1500);
        
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid))); 
        pdu.setType(PDU.GET);
        
        Snmp snmp = new Snmp(transport); 
        ResponseEvent paquetReponse = null;
        
        paquetReponse = snmp.get(pdu, target); 
        System.out.println("Requete SNMP envoyée");
        if (paquetReponse !=null) 
        {
            PDU pduReponse = paquetReponse.getResponse(); 
            System.out.println("Status réponse = " + pduReponse.getErrorStatus()); 
            System.out.println("Status réponse = " + pduReponse.getErrorStatusText());
            List vecReponse = pduReponse.getVariableBindings(); 
            for (int i=0; i<vecReponse.size(); i++)
            {
                System.out.println("Elément n°"+i+ " : "+vecReponse.get(i)); 
            }
        }
    }
    
}
