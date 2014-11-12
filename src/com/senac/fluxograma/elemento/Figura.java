package com.senac.fluxograma.elemento;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public abstract class Figura implements Serializable {
	private static final long serialVersionUID = -7724780106485661479L;

	protected int xOriginal, yOriginal, x, y, largura, altura;
    protected Color cor;
    private boolean fill;

    public Figura(int x, int y, int largura, int altura, Color cor) {
        this.xOriginal = x;
        this.yOriginal = y;
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
        this.cor = cor;
        this.fill = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
    
    public void setPontoFinal(int xFim, int yFim)
    {
    	if (xFim == xOriginal && yFim == yOriginal) {
            this.setCor(Color.WHITE);
		}

        if(xFim < xOriginal)
        {
            this.largura = xOriginal - xFim;
            x = xFim;
        } else {
            this.largura = xFim - xOriginal;
            x = xOriginal;
        }
        
        if(yFim < yOriginal){
            this.altura = yOriginal - yFim;
            y = yFim;
        } else {
            this.altura = yFim - yOriginal;
            y = yOriginal;
        }
    }
    
    public abstract void desenha(Graphics g);

	public Color getCor() {
		return cor;
	}

	public void setCor(Color cor) {
		this.cor = cor;
	}

	public boolean isFill() {
		return fill;
	}

	public void setFill(boolean fill) {
		this.fill = fill;
	}
}
