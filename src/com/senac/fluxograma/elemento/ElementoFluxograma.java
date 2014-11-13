package com.senac.fluxograma.elemento;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class ElementoFluxograma extends Figura {
	private static final long serialVersionUID = -4479610298424913211L;

	private String texto;
	private Point centro;
	private ArrayList<LinhaFluxo> linhasFluxo;

	public ElementoFluxograma(int x, int y, int largura, int altura, Color cor) {
		super(x, y, largura, altura, cor);
		this.x = x - largura/2;
		this.y = y - altura/2;
		texto = this.getClass().getSimpleName();
		this.linhasFluxo = new ArrayList<LinhaFluxo>();
	}

	@Override
	public void desenha(Graphics g) {
		calculaCentro();
		g.drawString(this.texto, centro.x, centro.y);
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		texto = (texto == null || texto.equals("")) ? " " : texto;
		this.texto = texto;
	}

	public Point getCentro() {
		this.calculaCentro();
		return this.centro;
	}

	private void calculaCentro() {
		int x, y;

		x = (int) (this.getLargura() / 2 + this.getX() - (this.texto.length()*3));
		y = (int) (this.getAltura() / 2 + this.getY() + 5);

		centro = new Point(x, y);
	}

	public ArrayList<LinhaFluxo> getLinhasFluxo() {
		return linhasFluxo;
	}

	public LinhaFluxo getLinhaFluxo(int indice) {
		return linhasFluxo.get(indice);
	}

	public boolean addLinhaFluxo(LinhaFluxo linhaFluxo) {
		return this.linhasFluxo.add(linhaFluxo);
	}
}