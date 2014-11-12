package com.senac.fluxograma.elemento;

import java.awt.Color;
import java.awt.Graphics;

public class Processamento extends ElementoFluxograma {
	private static final long serialVersionUID = -192737480350883807L;

	public Processamento(int x, int y) {
		super(x, y, 150, 40, Color.BLACK);
	}

	@Override
	public void desenha(Graphics g) {
		g.setColor(this.cor);
		g.drawRect(this.x, this.y, this.largura, this.altura);

		super.desenha(g);
	}
}