package br.com.fatec;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.*;

@SuppressWarnings("serial")
public class Arena extends JComponent implements MouseListener, ActionListener,KeyListener {
	private int largura, altura;
	public Set<Tanque> tanques;
	private Timer contador;
	private HashSet<Tiro> tiros;

	

	private double xTanque, yTanque, anguloTanque;

	public Arena(int largura, int altura) {
		this.largura = largura;
		this.altura = altura;
		tanques = new HashSet<Tanque>();
		addMouseListener(this);
		addKeyListener(this);
		this.setFocusable(true);
		contador = new Timer(100, this);
		contador.start();

		tiros = new HashSet<Tiro>();

	}

	public void adicionaTanque(Tanque t) {
		tanques.add(t);
	}

	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public Dimension getPreferredSize() {
		return new Dimension(largura, altura);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(new Color(245, 245, 255));
		g2d.fillRect(0, 0, largura, altura);
		g2d.setColor(new Color(200, 220, 220));

		for (int _largura = 0; _largura <= largura; _largura += 20)
			g2d.drawLine(_largura, 0, _largura, altura);
		for (int _altura = 0; _altura <= altura; _altura += 20)
			g2d.drawLine(0, _altura, largura, _altura);

		// Desenhamos todos os tanques
		for (Tanque t : tanques)
			t.draw(g2d);

		for (Tiro tiro : tiros)
			tiro.tiroDesenho(g2d);

	}

	public void mouseClicked(MouseEvent e) {

		for (Tanque t : tanques)
			t.setEstaAtivo(false);

		for (Tanque t : tanques) {
			boolean clicado = t.getRectEnvolvente()
					.contains(e.getX(), e.getY());
			if (clicado) {
				t.setEstaAtivo(true);

				xTanque = t.getX();
				yTanque = t.getY();
				anguloTanque = t.getAngulo();

				switch (e.getButton()) {
				case MouseEvent.BUTTON1:
					t.girarAntiHorario(3);
					break;
				case MouseEvent.BUTTON2:
					t.aumentarVelocidade();
					break;
				case MouseEvent.BUTTON3:
					t.girarHorario(3);
					break;
				}
				break;
			}
		}
		repaint();
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
		
		for (Tanque t : tanques) {
			for (Tanque tq : tanques) {
				if (t.getRectEnvolvente().contains(tq.getX(), tq.getY())) {
					if (t.getAngulo() != tq.getAngulo()
							|| t.getVelocidade() != tq.getVelocidade()) {
						t.setAngulo(aleatorio());
						tq.setAngulo(aleatorio());
					}
				}
			}

			for (Tiro tiro : tiros) {

				tiro.run();
				if (t.getRectEnvolvente().contains(tiro.getxTiro(),
						tiro.getyTiro())) {
					t.remover();
					tiro.remover();

				}
			}

			t.run();

		}
		repaint();
		
		

	}
	
	public int aleatorio(){
		Random gerador = new Random();
		// maior e menor
		return gerador.nextInt(360-0);
	}
	

	@Override
	public void keyPressed(KeyEvent e) {

		for (Tanque t : tanques) {

			if (t.estaAtivo) {
				
				xTanque = t.getX();
				yTanque = t.getY();
				anguloTanque = t.getAngulo();
				
				
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					t.girarAntiHorario(5);
					break;
				case KeyEvent.VK_UP:
					t.aumentarVelocidade();
					break;
				case KeyEvent.VK_DOWN:
					t.diminuirVelocidade();
					break;
				case KeyEvent.VK_RIGHT:
					t.girarHorario(5);
					break;
				case KeyEvent.VK_SPACE: {

					Tiro tiro = new Tiro(t.getX(), t.getY(), t.getAngulo(), 15);
					tiros.add(tiro);

				}

				}
				break;
			}
			repaint();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public static void main(String args[]) {
		Arena arena = new Arena(600, 400);

		arena.adicionaTanque(new Tanque(450, 50, 135, Color.YELLOW));
		arena.adicionaTanque(new Tanque(150, 100, 1, Color.GREEN));
		arena.adicionaTanque(new Tanque(350, 310, 20, Color.BLUE));		
		arena.adicionaTanque(new Tanque(10, 260, 300, Color.RED));
		
		arena.adicionaTanque(new Tanque(210, 200, 95, Color.YELLOW));
		arena.adicionaTanque(new Tanque(80, 195, 25, Color.GREEN));
		arena.adicionaTanque(new Tanque(270, 210, 20, Color.BLUE));		
		arena.adicionaTanque(new Tanque(40, 290, 300, Color.RED));
		
		
		JFrame janela = new JFrame("Tanques");
		janela.getContentPane().add(arena);
		janela.pack();
		janela.setVisible(true);
		janela.setDefaultCloseOperation(3);
                
	}

	

}