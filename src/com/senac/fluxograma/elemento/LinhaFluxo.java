package com.senac.fluxograma.elemento;

import java.awt.Color;
import java.awt.Graphics;

public class LinhaFluxo extends ElementoFluxograma {
	private static final long serialVersionUID = -192737480350883807L;

	private ElementoFluxograma figuraInicial = null, figuraFinal = null;
	private int xFinal, yFinal;

	public LinhaFluxo(ElementoFluxograma figuraInicial) {
		super(
			0,0,0,0,Color.BLACK
		);


		if (figuraInicial.addLinhaFluxo(this)) {
			this.figuraInicial = figuraInicial;
			this.figuraFinal = null;
			this.setTexto(" ");
		}
	}

	public void setFiguraFinal(ElementoFluxograma figuraFinal) {
		if (figuraFinal.addLinhaFluxo(this)) {
			this.figuraFinal = figuraFinal;
		}
	}

	public ElementoFluxograma getFiguraInicial() {
		return figuraInicial;
	}

	public ElementoFluxograma getFiguraFinal() {
		return figuraFinal;
	}

	@Override
	public void desenha(Graphics g) {
		this.x = figuraInicial.getX()+figuraInicial.getLargura()/2;
		this.y = figuraInicial.getY()+figuraInicial.getAltura();
		this.largura = 10;

		// caso possua figura final, utiliza as coordenadas da meso
		if (this.figuraFinal != null) {
			this.altura = figuraFinal.getY()-figuraInicial.getY()-figuraInicial.altura;

			this.xFinal = figuraFinal.getX()+figuraFinal.getLargura()/2;
			this.yFinal = figuraFinal.getY();
		} else { // caso contrário cria ponto final temporario
			this.altura = 50;
			this.xFinal = this.x;
			this.yFinal = this.y+50;
		}

		g.setColor(this.cor);
		g.drawLine(this.x, this.y, this.xFinal, yFinal-20);
		g.drawLine(this.xFinal, this.yFinal-20, this.xFinal, this.yFinal);
		g.drawLine(this.xFinal-10, this.yFinal-10, this.xFinal, this.yFinal);
		g.drawLine(this.xFinal+10, this.yFinal-10, this.xFinal, this.yFinal);

		super.desenha(g);
	}
}