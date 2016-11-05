/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mars_rovers;

/**
 *
 * @author Hugo
 */
public class Rover {
    private Coordenada posicao;
    private char direcao;

    public Rover(Coordenada posicao, char direcao) {
        this.posicao = posicao;
        this.direcao = direcao;
    }  

    public char getDirecao() {
        return direcao;
    }

    public Coordenada getPosicao() {
        return posicao;
    }

    public void setDirecao(char direcao) {
        this.direcao = direcao;
    }

    public void setPosicao(Coordenada posicao) {
        this.posicao = posicao;
    }
    
    public void virar(char comando){
        switch(this.direcao){
            case 'N':
                if(comando == 'L')
                    this.setDirecao('W');                
                else
                    this.setDirecao('E');
                break;
            case 'E':
                if(comando == 'L')
                    this.setDirecao('N');                
                else
                    this.setDirecao('S');
                break;
            case 'S':
                if(comando == 'L')
                    this.setDirecao('E');                
                else
                    this.setDirecao('W');
                break;
            case 'W':
                if(comando == 'L')
                    this.setDirecao('S');                
                else
                    this.setDirecao('N');
                break;            
        }
    }
    
    public void mover(){
        Coordenada aux = new Coordenada(this.getPosicao().getX(), this.getPosicao().getY());
        switch(this.direcao){
            case 'N':
                aux.setY((this.getPosicao().getY())+1);
                break;
            case 'E':
                aux.setX((this.getPosicao().getX())+1);
                break;
            case 'S':
                aux.setY((this.getPosicao().getY())-1);
                break;
            case 'W':
                aux.setX((this.getPosicao().getX())-1);
                break;            
            default:
                System.out.println("direcao invalida");
                break;
        }
        this.setPosicao(aux);
    }
    
    public void comandar(char comando){
        switch(comando){
            case 'L':
                this.virar(comando);
                break;
            case 'R':
                this.virar(comando);
                break;
            case 'M':
                this.mover();
                break;
            default:
                System.out.println("Opcao invalida");
        }
    }
    
}
