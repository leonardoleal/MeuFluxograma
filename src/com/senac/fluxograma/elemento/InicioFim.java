package com.senac.fluxograma.elemento;

import java.awt.Color;
import java.awt.Graphics;

public class InicioFim extends ElementoFluxograma {
	private static final long serialVersionUID = 4033735087077802417L;

	public static enum tipoElemento {INICIO, FIM};

	public InicioFim(int x, int y, tipoElemento tipo) {
		super(x, y, 86, 30, Color.BLACK);

		if (tipo == tipoElemento.INICIO) {
			this.setTexto("Inicio");
		} else {
			this.setTexto("Fim");
		}
	}

	@Override
	public void desenha(Graphics g) {
		g.setColor(this.cor);
		g.drawOval(x, y, this.largura, this.altura);

		super.desenha(g);
	}
}