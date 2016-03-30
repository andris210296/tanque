package br.com.fatec;

import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.font.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.util.Map;

import javax.imageio.ImageReader;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Tanque implements Runnable{


	

    private double velocidade;
    private Color cor;
    public boolean estaAtivo;
    private double x, y;
    private double angulo;    
    
    
    
    

    public Tanque(int x, int y, int a, Color cor) {
        this.x = x;
        this.y = y;
        this.angulo = 90 - a;
        this.cor = cor;
        velocidade = 2;
        this.estaAtivo = false; 
        
    }
    
    
    

    public double getAngulo() {
		return angulo;
	}



	
    
    public double getX() {
		return x;
	}

	

	public double getY() {
		return y;
	}
    
    

    public void aumentarVelocidade() {
        velocidade++;
    }

    public void diminuirVelocidade() {
        
            velocidade--;
        
    }

    public void girarHorario(int a) {
        angulo += a;
    }

    public void girarAntiHorario(int a) {
        angulo -= a;
    }
    
    
	public void run() {
		
				
		if(x != 1000 && y !=1000){
			
		
		
		
		if (y < 20){
            //positivo
            if( angulo > 0 && angulo < 90 ){ 
                angulo = 145;
                
            }
            else if (angulo >270 && angulo <360){
                angulo = 225;
                
            }
            //negativo
            else if (angulo <0 && angulo >-90){
                angulo = 225;
               
            }
            else if (angulo < -270 && angulo > - 360){
                angulo = 135;
                
            }
            else{
                angulo = 180;
            
            }
            this.y = 20;
        }
        
        //baixo
        else if (y >= 380){
            //positivo
            if (angulo > 90 && angulo < 180){
                angulo = 45;    
            }
            else if (angulo >180 && angulo < 270){
                angulo = 315;  
            }
            //negativo
            else if (angulo <-90 && angulo >-180){
                angulo = 315;
                this.y = 379;
            }
            else if(angulo < -180 && angulo > -270){
                angulo = 45;   
            }       
            else{
                angulo = 0;
            }
            this.y = 379;
        }
        
        //lado esquerdo
        else if(x <=20){
            //positivo
            if (angulo > 270 && angulo < 360){
                angulo = 45;
            }
            else if (angulo > 180 && angulo < 270){
                angulo = 135;
            }
            //negativo
            else if (angulo <-0 && angulo >-90){
                angulo = 45;
            }
            else if (angulo < -90 && angulo > -180){
                angulo = 135;
            }
            else{
            	angulo = 90;
            }
            this.x = 21;
        }
        //lado direito
        else if (x >= 580){
            //positivo
            if (angulo > 0 && angulo < 90){
                angulo = 315;
            }
            else if (angulo > 90 && angulo < 180){
                angulo = 225;
            }
            //negativo
            else if (angulo < 270 && angulo > 360){
                angulo = 315;
            }
            else if (angulo < 180 && angulo > 270){
                angulo = 225;
            }
            else{
            	angulo = 270;
            }
            this.x = 570;
        }
        
        
        x = x + Math.sin(Math.toRadians(angulo)) * velocidade;
        y = y - Math.cos(Math.toRadians(angulo)) * velocidade;
        
		}

	}

    public boolean limite() {
        
      
        if ((x < 0 || y < 0) || (x > 600 || y > 400) || (x < 600 && y > 400) || (x > 600 && y < 400) ) {
            return true;
        }
        return false;

    }

    public void setEstaAtivo(boolean estaAtivo) {
        this.estaAtivo = estaAtivo;
    }

    public boolean getEstaAtivo() {
        return estaAtivo;
    }

    public void draw(Graphics2D g2d) {
        //Armazenamos o sistema de coordenadas original.
        AffineTransform antes = g2d.getTransform();

        //Criamos um sistema de coordenadas para o tanque.
        AffineTransform depois = new AffineTransform();

        depois.translate(x, y);
        depois.rotate(Math.toRadians(angulo));

        //Aplicamos o sistema de coordenadas.		
        g2d.transform(depois);

        //Desenhamos o tanque. Primeiro o corpo
        g2d.setColor(cor);
        g2d.fillRect(-10, -12, 20, 24);

        //Agora as esteiras
        for (int i = -12; i <= 8; i += 4) {
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(-15, i, 5, 4);
            g2d.fillRect(10, i, 5, 4);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(-15, i, 5, 4);
            g2d.fillRect(10, i, 5, 4);
        }
        //O canhão
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(-3, -25, 6, 25);
        g2d.setColor(cor);
        g2d.drawRect(-3, -25, 6, 25);
		//Se o tanque estiver ativo
        //Desenhamos uma margem
        if (estaAtivo) {
            g2d.setColor(new Color(120, 120, 120));
            Stroke linha = g2d.getStroke();
            g2d.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND, 0,
                    new float[]{8}, 0));
            g2d.drawRect(-24, -32, 48, 55);
            g2d.setStroke(linha);                               
            
            
        }        
        
                
        //Aplicamos o sistema de coordenadas
        g2d.setTransform(antes);
        
        
        
    }

    public Shape getRectEnvolvente() {
        AffineTransform at = new AffineTransform();
        at.translate(x, y);
        at.rotate(Math.toRadians(angulo));
        Rectangle rect = new Rectangle(-24, -32, 48, 55);
        return at.createTransformedShape(rect);
    }

    
    
    
    public void remover(){
    	velocidade = 0;
    	cor = new Color(0, 0, 0);
    	x = 1000;
    	y = 1000;
    	 
    	
    }




	public double getVelocidade() {
		return velocidade;
	}




	public void setVelocidade(double velocidade) {
		this.velocidade = velocidade;
	}




	public void setAngulo(double angulo) {
		this.angulo = angulo;
	}






	




	
   
    
    

}
