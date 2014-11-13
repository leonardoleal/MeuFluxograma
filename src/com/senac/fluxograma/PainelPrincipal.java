package com.senac.fluxograma;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.senac.fluxograma.elemento.Decisao;
import com.senac.fluxograma.elemento.ElementoFluxograma;
import com.senac.fluxograma.elemento.Figura;
import com.senac.fluxograma.elemento.Fluxograma;
import com.senac.fluxograma.elemento.InicioFim;
import com.senac.fluxograma.elemento.LinhaFluxo;
import com.senac.fluxograma.elemento.Processamento;
import com.senac.fluxograma.elemento.Quadrado;
import com.senac.fluxograma.elemento.Subrotina;

public class PainelPrincipal extends JPanel {
	private static final long serialVersionUID = -8676753967627332806L;

	private FluxogramaFrame frame;
	private Fluxograma fluxograma;
	private int indiceSelecao;
    
    public PainelPrincipal(FluxogramaFrame fluxogramaFrame) {
    	super(new BorderLayout());
    	indiceSelecao = -1;
    	frame = fluxogramaFrame;
        fluxograma = new Fluxograma("Novo Fluxograma");

        TratadorMouse tratadorMouse = new TratadorMouse();
        this.addMouseListener(tratadorMouse);
        this.addMouseMotionListener(tratadorMouse);
    }

	public Fluxograma getFluxograma() {
		return fluxograma;
	}

    public void setFluxograma(Fluxograma fluxograma) {
		this.fluxograma = fluxograma;
		repaint();
	}

	private void limparTela(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 
        		this.getWidth(), this.getHeight()
        );
    }
    
    public void limpar() {
        fluxograma.clear();
        repaint();
    }
    
    public void adicionaFigura(Figura figura) {
    	limpaSelecao();
        fluxograma.add(figura);
        repaint();
    }

    public Figura getUltimaFigura() {
        int indice = fluxograma.size() - 1;
        Figura figura = fluxograma.get(indice);
        return figura;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        limparTela(g);
        
        for(Figura figura: fluxograma)
            figura.desenha(g);
    }

    public int quantFiguras() {
    	return fluxograma.size();
    }

	public ArrayList<Figura> getDesenhos() {
		return fluxograma;
	}

	public void mostraSelecao(Point pontos) {
		limpaSelecao();

		indiceSelecao = procuraFigura(pontos);

		if (hasSelection()) {
			Figura figura = fluxograma.get(indiceSelecao);
			fluxograma.add(
					new Quadrado(
							figura.getX(), figura.getY(),
							figura.getLargura(), figura.getAltura(), Color.RED
					)
			);
		}

		repaint();
	}

	public boolean hasSelection() {
		return isIndiceUsado(indiceSelecao);
	}

	public void limpaSelecao() {
		if (indiceSelecao >= 0) {
			indiceSelecao = -1;
			remover(fluxograma.size()-1);
		}
	}

	public boolean removerSelecionado() {
		if (this.getFiguraSelecionada() instanceof LinhaFluxo) {
			LinhaFluxo linha = (LinhaFluxo) this.getFiguraSelecionada();
			linha.getFiguraInicial().getLinhasFluxo().remove(linha);
			linha.getFiguraFinal().getLinhasFluxo().remove(linha);
		} else {
			ArrayList<LinhaFluxo> linhas = ((ElementoFluxograma) this.getFiguraSelecionada()).getLinhasFluxo();
			for (LinhaFluxo linha : linhas) {
				this.fluxograma.remove(linha);
			}
		}

		if (remover(indiceSelecao)) {
			limpaSelecao();
			return true;
		}

		return false;
	}

	private boolean remover(int indice) {
		if (isIndiceUsado(indice)) {
			if (fluxograma.remove(indice) != null) {
				repaint();
				return true;
			}
		}

		return false;
	}

	private boolean isIndiceUsado(int indice) {
		if (indice >= 0 && indice < fluxograma.size()) {
			return true;
		}

		return false;
	}

	private int procuraFigura(Point pontos) {
		Figura figuraAtaul;
		for (int i = (fluxograma.size()-1); i >= 0; i--) {
			figuraAtaul = fluxograma.get(i);

			if (pontos.getX() > figuraAtaul.getX()
				&& pontos.getX() < figuraAtaul.getX() + figuraAtaul.getLargura()
				&& pontos.getY() > figuraAtaul.getY()
				&& pontos.getY() < figuraAtaul.getY() + figuraAtaul.getAltura()
			) {
				return i;
			}
		}

		return -1;
	}

	public Figura getFiguraSelecionada() {
		if (this.hasSelection()) {
			return fluxograma.get(indiceSelecao);
		}

		return null;
	}

	public void moverFigura(Point point) {
		if (this.hasSelection()) {
			Figura figura = this.getFiguraSelecionada();

			int x, y;
			x = point.x - figura.getLargura()/2;
			y = point.y - figura.getAltura()/2;

			figura.setX(x);
			figura.setY(y);

			getUltimaFigura().setX(x);
			getUltimaFigura().setY(y);

			repaint();
		}
	}

	private class TratadorMouse implements MouseListener, MouseMotionListener {
    	LinhaFluxo linhaFluxoAtual;

        @Override
        public void mousePressed(MouseEvent e) {
        	PainelPrincipal.this.mostraSelecao(e.getPoint());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        	PainelPrincipal.this.moverFigura(e.getPoint());
        }  

        @Override
        public void mouseReleased(MouseEvent e) {
        	if (e.getButton() == MouseEvent.BUTTON1) {
        		int x = e.getX();
        		int y = e.getY();

				frame.getPainelEstruturas().addFluxograma(PainelPrincipal.this.getFluxograma());
        		
				if (! PainelPrincipal.this.hasSelection()) {
					if(frame.getBotaoInicio().isSelected()) {
						PainelPrincipal.this.adicionaFigura(new InicioFim(x, y, InicioFim.tipoElemento.INICIO));

					} else if(frame.getBotaoFim().isSelected()) {
						PainelPrincipal.this.adicionaFigura(new InicioFim(x, y, InicioFim.tipoElemento.FIM));

					} else if(frame.getBotaoProcessamento().isSelected()) {
						PainelPrincipal.this.adicionaFigura(new Processamento(x, y));

					} else if(frame.getBotaoDecisao().isSelected()) {
						PainelPrincipal.this.adicionaFigura(new Decisao(x, y));

					} else if(frame.getBotaoSubrotina().isSelected()) {
						Subrotina subrotina = new Subrotina(x, y);
						subrotina.setSubrotinaFluxo(new Fluxograma());

						PainelPrincipal.this.adicionaFigura(subrotina);
						frame.getPainelEstruturas().addFluxograma(subrotina.getSubrotinaFluxo());
					}
					frame.getPainelEstruturas().addFluxograma(PainelPrincipal.this.getFluxograma());

				} else if(frame.getBotaoLinhaFluxo().isSelected()) {
					ElementoFluxograma selecionado = (ElementoFluxograma) PainelPrincipal.this.getFiguraSelecionada();
					if (! (selecionado instanceof LinhaFluxo)) {
						if (linhaFluxoAtual == null) {
							linhaFluxoAtual = new LinhaFluxo(selecionado);

							if (linhaFluxoAtual.getFiguraInicial() != null) {
								PainelPrincipal.this.adicionaFigura(linhaFluxoAtual);
							} else {
								frame.mensagemEstado(
										"Não é possível adicionar linha de fluxo como saída neste elemento."
								);
								linhaFluxoAtual = null;
							}
						} else {
							linhaFluxoAtual.setFiguraFinal(selecionado);

							if (linhaFluxoAtual.getFiguraFinal() != null) {
								PainelPrincipal.this.repaint();
								frame.getGrupoBotoesBarraFerramenta().clearSelection();
								linhaFluxoAtual = null;
							} else {
								frame.mensagemEstado(
										"Não é possível adicionar linha de fluxo como entrada neste elemento."
								);
							}
						}
					}
				}
        	}
        }

		@Override
		public void mouseMoved(MouseEvent e) {}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON3) { 
                if(e.getComponent() instanceof PainelPrincipal)
                	frame.getMenuPopup().show(e.getComponent(), e.getX(), e.getY()); 
            } else if (e.getClickCount() == 2) {
            	// se não for subrotina, edita o texto
            	if (! (PainelPrincipal.this.getFiguraSelecionada()
            			instanceof Subrotina)
            	) {
            		frame.getPopupMenuItemTexto().doClick();
				} else { // exibe o fluxograma da subrotina
					PainelPrincipal.this.setFluxograma(
							((Subrotina) PainelPrincipal.this.
							getFiguraSelecionada()).getSubrotinaFluxo()
					);
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}
    }
}