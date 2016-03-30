package br.com.fatec;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
public class Tiro implements Runnable{

	private double xTiro, yTiro;
	private double anguloTiro;	
	private double velocidadeTiro;

		
	
	private Image img = Toolkit.getDefaultToolkit().getImage("images/bola.png");

	

	public Tiro(double xTiro, double yTiro, double anguloTiro,double velocidade) {
		setAnguloTiro(anguloTiro);
		setxTiro(xTiro);
		setyTiro(yTiro);
		setVelocidadeTiro(velocidade);
		

	}
public void run() {
				

		setxTiro(getxTiro() + Math.sin(Math.toRadians(getAnguloTiro())) * getVelocidadeTiro());
		setyTiro(getyTiro() - Math.cos(Math.toRadians(getAnguloTiro())) * getVelocidadeTiro());
		
		
	
	}

	public void tiroDesenho(Graphics2D g2d) {

		// Armazenamos o sistema de coordenadas original.
		AffineTransform antes = g2d.getTransform();
		// Criamos um sistema de coordenadas para o tanque.
		AffineTransform depois = new AffineTransform();
		depois.translate(xTiro, yTiro);
		depois.rotate(Math.toRadians(anguloTiro));
		// Aplicamos o sistema de coordenadas.
		g2d.transform(depois);
		// Desenhamos o tiro
		g2d.setColor(Color.BLACK);
		g2d.fillOval(-5, -42, 7, 7);
		
		// Aplicamos o sistema de coordenadas
		g2d.setTransform(antes);
            
            
		
		

		// http://seumestredaweb.blogspot.com.br/2012/06/java-games-2d-tutorial-parte-9.html
		// http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/DrawImage.htm
		// http://abrindoojogo.com.br/djj-07
	}
        
        public Shape getRectEnvolvente() {
		AffineTransform at = new AffineTransform();
		at.translate(xTiro, yTiro);
		at.rotate(Math.toRadians(anguloTiro));
		Rectangle rect = new Rectangle(-24, -32, 48, 55);
		return at.createTransformedShape(rect);
	}
        
        
        public void remover(){
        	xTiro = 1000;
        	yTiro = 1000;
        	velocidadeTiro = 0;
        }

		public double getxTiro() {
			return xTiro;
		}

		public void setxTiro(double xTiro) {
			this.xTiro = xTiro;
		}

		public double getyTiro() {
			return yTiro;
		}

		public void setyTiro(double yTiro) {
			this.yTiro = yTiro;
		}

		public double getAnguloTiro() {
			return anguloTiro;
		}

		public void setAnguloTiro(double anguloTiro) {
			this.anguloTiro = anguloTiro;
		}

		public double getVelocidadeTiro() {
			return velocidadeTiro;
		}

		public void setVelocidadeTiro(double velocidadeTiro) {
			this.velocidadeTiro = velocidadeTiro;
		}

		public Image getImg() {
			return img;
		}

		public void setImg(Image img) {
			this.img = img;
		}

	

}
