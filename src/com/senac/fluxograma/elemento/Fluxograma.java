package com.senac.fluxograma.elemento;

import java.io.Serializable;
import java.util.ArrayList;

public class Fluxograma extends ArrayList<Figura> implements Serializable {
	private static final long serialVersionUID = 521921239124876658L;

	private static int NUM = 0;
	private int hash;
	private String nome;

	public Fluxograma() {
		NUM++;
		hashCode();
	}

	public Fluxograma(String nome) {
		NUM++;
		hashCode();
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getHashCode() {
		return hash;
	}

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public boolean equals(Object obj) {
		if((obj == null) || (obj.getClass() != this.getClass()))
			return false;

		Fluxograma test = (Fluxograma)obj;
		return hash == test.getHashCode();
	}

	public int hashCode() {
		hash = 7;
		hash = 31 * hash + NUM;
		hash = 31 * hash + (null == nome ? 0 : nome.hashCode());
		return hash;
	}
}