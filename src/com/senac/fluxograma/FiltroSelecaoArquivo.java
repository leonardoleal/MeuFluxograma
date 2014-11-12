package com.senac.fluxograma;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FiltroSelecaoArquivo extends FileFilter {
	public enum tipoArquivo {IMAGEM, DOCUMENTO};
	private tipoArquivo tipo;

	public FiltroSelecaoArquivo(tipoArquivo tipo) {
		this.tipo = tipo;
	}

	@Override
	public String getDescription() {
		if (this.tipo.equals(tipoArquivo.IMAGEM)) {
			return "Extensão Imagem (png)";
		} else {
            return "Extensão Fluxograma (flux)";
        }
    }

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		if (this.tipo.equals(tipoArquivo.IMAGEM)) {
			return f.getName().toLowerCase().endsWith("png");
		} else {
			return f.getName().toLowerCase().endsWith("flux");
        }

	}
}