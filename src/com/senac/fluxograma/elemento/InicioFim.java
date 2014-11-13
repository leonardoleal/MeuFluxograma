package com.senac.fluxograma.elemento;

import java.awt.Color;
import java.awt.Graphics;

public class InicioFim extends ElementoFluxograma {
	private static final long serialVersionUID = 4033735087077802417L;

	public static enum tipoElemento {INICIO, FIM};
	private tipoElemento tipo;

	public InicioFim(int x, int y, tipoElemento tipo) {
		super(x, y, 86, 30, Color.BLACK);
		this.setTipo(tipo);

		if (tipo == tipoElemento.INICIO) {
			this.setTexto("Inicio");
		} else {
			this.setTexto("Fim");
		}
	}

	public tipoElemento getTipo() {
		return tipo;
	}

	public void setTipo(tipoElemento tipo) {
		this.tipo = tipo;
	}

	@Override
	public void desenha(Graphics g) {
		g.setColor(this.cor);
		g.drawOval(x, y, this.largura, this.altura);

		super.desenha(g);
	}

	/**
	 * Evita colocar linha de entrada no INÍCIO
	 * e linha de saida no FIM
	 */
	@Override
	public boolean addLinhaFluxo(LinhaFluxo linhaFluxo) {
		if (this.getTipo().equals(tipoElemento.INICIO)
			&& linhaFluxo.getFiguraInicial() != null
			|| (this.getTipo().equals(tipoElemento.FIM)
				&& linhaFluxo.getFiguraInicial() == null)
		) {
			return false;
		}

		return super.addLinhaFluxo(linhaFluxo);
	};

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;

		try {
			return this.getTipo().equals(((InicioFim) o).getTipo());
		} catch (ClassCastException cCE) {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}