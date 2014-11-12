package com.senac.fluxograma.elemento;

import java.awt.Color;
import java.awt.Graphics;

public class Quadrado extends Figura {
	private static final long serialVersionUID = -192737480350883807L;

	public Quadrado(int x, int y, int largura, int altura, Color cor) {
		super(x, y, largura, altura, cor);
	}

	@Override
	public void desenha(Graphics g) {
		g.setColor(this.cor);
		if (this.isFill()) {
			g.fillRect(this.x, this.y, this.largura, this.altura);
		} else {
			g.drawRect(this.x, this.y, this.largura, this.altura);
		}
	}
}