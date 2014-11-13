package com.senac.fluxograma;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.senac.fluxograma.elemento.Fluxograma;

public class PainelEstruturas extends JPanel {
	private static final long serialVersionUID = 6458022136197071926L;

	private DefaultListModel<Fluxograma> modelFluxograma;
	private JList<Fluxograma> listaFluxogramas;
	private FluxogramaFrame frame;
    
    public PainelEstruturas(FluxogramaFrame fluxogramaFrame) {
    	super(new BorderLayout());

    	frame = fluxogramaFrame;
    	modelFluxograma = new DefaultListModel<Fluxograma>();

        iniciaComponentes();
    }

	private void iniciaComponentes() {
		DefaultListModel<Fluxograma> modelFluxograma = new DefaultListModel<Fluxograma>();

		TratadorMouse tratadorMouse = new TratadorMouse();

        listaFluxogramas = new JList<Fluxograma>();
        listaFluxogramas.setModel(modelFluxograma);
        listaFluxogramas.addMouseListener(tratadorMouse);
        listaFluxogramas.scrollRectToVisible(new Rectangle());
        listaFluxogramas.setLayoutOrientation(JList.VERTICAL);
        listaFluxogramas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane listaScroller = new JScrollPane(listaFluxogramas);
		listaScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(listaScroller);
	}

	public List<Object> getFluxogramas() {
		return Arrays.asList(modelFluxograma.toArray());
	}

	public Fluxograma getFluxogramaSelecionado() {
		return listaFluxogramas.getSelectedValue();
	}
		//adiciona fluxograma
	public void addFluxograma(Fluxograma fluxograma) {
		modelFluxograma = (DefaultListModel<Fluxograma>) listaFluxogramas.getModel();

		if (!modelFluxograma.contains(fluxograma)) {
	        modelFluxograma.addElement(fluxograma);
	        frame.getPainelPrincipal().setFluxograma(fluxograma);
		} else {
			int indice = modelFluxograma.indexOf(fluxograma);
			modelFluxograma.set(indice, fluxograma);
		}
    }
		//edita fluxograma
	public void editaFluxograma(Fluxograma fluxograma) {
		int indice = modelFluxograma.indexOf(fluxograma);
		modelFluxograma.set(indice, fluxograma);
    }
		//remove fluxograma
    public void removeFluxograma(Fluxograma fluxograma) {
		if (modelFluxograma.contains(fluxograma)) {
	        modelFluxograma.removeElement(fluxograma);
		}
	}
    	//limpar tudo
	public void limpar() {
		listaFluxogramas.removeAll();
		modelFluxograma.clear();
	}

	private class TratadorMouse implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				// exibe o fluxograma selecionado
				frame.getPainelPrincipal().setFluxograma(
						PainelEstruturas.this.
						getFluxogramaSelecionado()
				);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
		
	}
}