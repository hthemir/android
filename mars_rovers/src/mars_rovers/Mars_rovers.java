/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mars_rovers;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Hugo
 */
public class Mars_rovers {

    /**
     * @param args the command line arguments
     */   
    
    public static void main(String[] args) {
        System.out.println("Digite -1 na coordenada x para parar de inserir rovers");
        Scanner s = new Scanner(System.in);
        int x = s.nextInt();
        int y = s.nextInt();  
        Coordenada aux = new Coordenada(x, y);
        char direcao;
        char[] comandos;
        Plateau plateau = new Plateau(new Coordenada(0, 0),aux);
        ArrayList<Rover> lista = new ArrayList<Rover>();
        
        
        while(true){
            x = s.nextInt();
            if(x==-1)
                break;
            aux.setX(x);
            y = s.nextInt();
            aux.setY(y);
            direcao = s.next(".").charAt(0);
            Rover rover = new Rover(aux, direcao);
            
            s.nextLine();
            comandos = s.nextLine().toCharArray();
            for(char c:comandos){
                rover.comandar(c);
            }
            lista.add(rover);
        }
        
        for(Rover r: lista){
            System.out.println(r.getPosicao().getX() + " " + r.getPosicao().getY() + " " + r.getDirecao());
        }
    }
    
}
