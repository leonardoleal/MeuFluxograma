package com.senac.fluxograma.elemento;

import java.awt.Color;
import java.awt.Graphics;

public class Decisao extends ElementoFluxograma {
	private static final long serialVersionUID = -2491584545464727831L;
	private int[] xArray;
	private int[] yArray;
	
	public Decisao(int x, int y) {
		super(x, y, 100, 60, Color.BLACK);
	}

	@Override
	public void desenha(Graphics g) {
		g.setColor(this.cor);

		this.xArray = new int[] {this.x, this.x+50, this.x+100, this.x+50, this.x};
		this.yArray = new int[] {this.y+30, this.y, this.y+30, this.y+60, this.y+30};

		g.drawPolygon(xArray, yArray, xArray.length);
		super.desenha(g);
	}
}