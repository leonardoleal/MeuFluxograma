package com.senac.fluxograma;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.util.EventListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.senac.fluxograma.elemento.Fluxograma;

public class PainelEstruturas extends JPanel {
	private static final long serialVersionUID = 6458022136197071926L;

	private DefaultListModel<Fluxograma> modelFluxograma;
	private JList<Fluxograma> listaFluxogramas;
	private JFrame frame;
    
    public PainelEstruturas(FluxogramaFrame fluxogramaFrame) {
    	super(new BorderLayout());

    	frame = fluxogramaFrame;
    	modelFluxograma = new DefaultListModel<Fluxograma>();

        iniciaComponentes();
    }

	private void iniciaComponentes() {
		DefaultListModel<Fluxograma> modelFluxograma = new DefaultListModel<Fluxograma>();

		TratadorLista tratadorLista = new TratadorLista();

        listaFluxogramas = new JList<Fluxograma>();
        listaFluxogramas.setModel(modelFluxograma);
        listaFluxogramas.scrollRectToVisible(new Rectangle());
        listaFluxogramas.setLayoutOrientation(JList.VERTICAL);
        listaFluxogramas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaFluxogramas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (listaFluxogramas.getSelectedIndex() >= 0) {
                	// carregar fluxograma na tela desenho
                }
            }
        });

		JScrollPane listaScroller = new JScrollPane(listaFluxogramas);
		listaScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(listaScroller);
	}

	public Fluxograma[] getFluxogramas() {
		return (Fluxograma[]) modelFluxograma.toArray();
	}

	public Fluxograma getFluxograma(int indice) {
		return modelFluxograma.get(indice);
	}

	public void addFluxograma(Fluxograma fluxograma) {
		modelFluxograma = (DefaultListModel<Fluxograma>) listaFluxogramas.getModel();

		if (!modelFluxograma.contains(fluxograma)) {
	        modelFluxograma.addElement(fluxograma);
		} else {
			int indice = modelFluxograma.indexOf(fluxograma);
			modelFluxograma.set(indice, fluxograma);
		}
    }

	public void editaFluxograma(Fluxograma fluxograma) {
		int indice = modelFluxograma.indexOf(fluxograma);
		modelFluxograma.set(indice, fluxograma);
    }

    public void removeFluxograma(Fluxograma fluxograma) {
		JOptionPane.showMessageDialog(null, "remover");
		if (modelFluxograma.contains(fluxograma)) {
			JOptionPane.showMessageDialog(null, "remove");
	        modelFluxograma.removeElement(fluxograma);
		}
	}

	public void limpar() {
		listaFluxogramas.removeAll();
		modelFluxograma.clear();
	}

	public class TratadorLista implements EventListener {
		
	}
}