package com.senac.fluxograma.elemento;

import java.awt.Graphics;

public class Subrotina extends Processamento {
	private static final long serialVersionUID = -192737480350883807L;

	private Fluxograma subrotinaFluxo;

	public Subrotina(int x, int y) {
		super(x, y);
	}

	@Override
	public void desenha(Graphics g) {
		super.desenha(g);

		g.drawRect(this.x, this.y, 20, this.altura);
		g.drawRect(this.x+this.largura-20, this.y, 20, this.altura);
	}

	@Override
	public void setTexto(String texto) {
		super.setTexto(texto);
	 	this.subrotinaFluxo.setNome(texto);
	}

	public Fluxograma getSubrotinaFluxo() {
		return subrotinaFluxo;
	}

	public void setSubrotinaFluxo(Fluxograma fluxograma) {
		fluxograma.setNome(this.getTexto());
		this.subrotinaFluxo = fluxograma;
	}
}