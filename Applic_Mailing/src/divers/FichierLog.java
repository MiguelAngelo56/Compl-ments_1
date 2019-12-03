package divers;

import java.io.*;

public class FichierLog 
{
    public FichierLog(){}

    public static void Ecrire(String tmp,String cheminfichier)
    {
        try (FileWriter fw = new FileWriter(cheminfichier,true)) 
        {
            fw.write(tmp + '\n');
            fw.close();
        } 
        catch (IOException e) 
        {
            System.out.println(e.getMessage());
        }
    }
    
    public static String Lire(String cheminfichier)
    {
        FileReader flux= null;
        BufferedReader input= null;
        String line;
        String tmp = new String();
        
        try
        { 
            flux= new FileReader (cheminfichier);
            input= new BufferedReader(flux);
            
            //Lis ligne par ligne jusqu'à la fin et ajoute les lignes lu dans le text area
            while((line = input.readLine())!=null)tmp = tmp + '\n' + line;
            
            flux.close();
        }
        catch (IOException e)
        {
             System.out.println(e.getMessage());
        }  
        return tmp;
    }
}