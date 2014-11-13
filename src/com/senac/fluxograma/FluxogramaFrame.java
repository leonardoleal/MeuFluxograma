package com.senac.fluxograma;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import com.senac.fluxograma.FiltroSelecaoArquivo.tipoArquivo;
import com.senac.fluxograma.elemento.ElementoFluxograma;
import com.senac.fluxograma.elemento.Fluxograma;
import com.senac.fluxograma.elemento.InicioFim;
import com.senac.fluxograma.elemento.Subrotina;

class FluxogramaFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JMenuItem menuItemNovo, menuItemAbrir, menuItemSalvar, menuItemExportarFluxo, menuItemExportarProjeto;
	private JMenuItem menuItemLimpar, menuItemTexto, menuItemRemover, menuItemSair;
	private JMenuItem popupMenuItemLimpar, popupMenuItemTexto, popupMenuItemRemover;
	private JToolBar barraFerramentas;
	private ButtonGroup grupoBotoesBarraFerramenta;
    private JToggleButton botaoInicio, botaoFim, botaoProcessamento, botaoDecisao;
    private JToggleButton botaoSubrotina, botaoLinhaFluxo;
    private PainelPrincipal painelPrincipal;
    private PainelEstruturas painelEstruturas;
	private JLabel barraEstado;
	private File localDefault;
	private JPopupMenu menuPopup;
	private TratadorMenus tratadorMenus;
	private GridBagConstraints gbc;

    public FluxogramaFrame() {
        super("Novo Fluxograma");
        tratadorMenus = new TratadorMenus();
        this.setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();

        iniciaBarraMenus();
        iniciaBarraFerramentas();
        iniciaComponentes();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100, 100, 700, 700);
        this.setVisible(true);
        this.localDefault = new File(System.getProperty("user.home")+"fluxogramas");
    }
    
    public ButtonGroup getGrupoBotoesBarraFerramenta() {
		return grupoBotoesBarraFerramenta;
	}

	public JToggleButton getBotaoInicio() {
		return botaoInicio;
	}

	public JToggleButton getBotaoFim() {
		return botaoFim;
	}

	public JToggleButton getBotaoProcessamento() {
		return botaoProcessamento;
	}

	public JToggleButton getBotaoDecisao() {
		return botaoDecisao;
	}

	public JToggleButton getBotaoSubrotina() {
		return botaoSubrotina;
	}

	public JToggleButton getBotaoLinhaFluxo() {
		return botaoLinhaFluxo;
	}

	public JPopupMenu getMenuPopup() {
		return menuPopup;
	}

	public JMenuItem getPopupMenuItemTexto() {
		return popupMenuItemTexto;
	}

	public PainelEstruturas getPainelEstruturas() {
    	return painelEstruturas;
    }

	public PainelPrincipal getPainelPrincipal() {
    	return painelPrincipal;
    }

    private void iniciaBarraMenus() {
        JMenuBar barraMenu = new JMenuBar();

        JMenu menuArquivo = new JMenu("Arquivo");
        menuArquivo.setMnemonic(KeyEvent.VK_A);

        menuItemNovo = new JMenuItem("Novo");
        menuItemNovo.setMnemonic(KeyEvent.VK_N);
        menuItemNovo.addActionListener(tratadorMenus);
        menuItemNovo.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK)
        );
        menuArquivo.add(menuItemNovo);

        menuItemAbrir = new JMenuItem("Abrir");
        menuItemAbrir.setMnemonic(KeyEvent.VK_A);
        menuItemAbrir.addActionListener(tratadorMenus);
        menuItemAbrir.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK)
        );
        menuArquivo.add(menuItemAbrir);

        menuArquivo.addSeparator();

        menuItemSalvar = new JMenuItem("Salvar");
        menuItemSalvar.setMnemonic(KeyEvent.VK_S);
        menuItemSalvar.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK)
        );
        menuItemSalvar.addActionListener(tratadorMenus);
        menuArquivo.add(menuItemSalvar);

        menuItemExportarFluxo = new JMenuItem("Exportar p/Imagem");
        menuItemExportarFluxo.setMnemonic(KeyEvent.VK_X);
        menuItemExportarFluxo.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK)
        );
        menuItemExportarFluxo.addActionListener(tratadorMenus);
        menuArquivo.add(menuItemExportarFluxo);

        menuItemExportarProjeto = new JMenuItem("Exportar Projeto p/Imagem");
        menuItemExportarProjeto.setMnemonic(KeyEvent.VK_I);
        menuItemExportarProjeto.addActionListener(tratadorMenus);
        menuArquivo.add(menuItemExportarProjeto);

        menuArquivo.addSeparator();

        menuItemSair = new JMenuItem("Sair");
        menuItemSair.setMnemonic(KeyEvent.VK_R);
        menuItemSair.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK)
        );
        menuItemSair.addActionListener(tratadorMenus);
        menuArquivo.add(menuItemSair);

        barraMenu.add(menuArquivo);

        JMenu menuFluxograma = new JMenu("Fluxograma");
        menuFluxograma.setMnemonic(KeyEvent.VK_F);

        menuItemLimpar = new JMenuItem("Limpar Fluxograma");
        menuItemLimpar.setMnemonic(KeyEvent.VK_L);
        menuItemLimpar.addActionListener(tratadorMenus);
        menuItemLimpar.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK)
        );
        menuFluxograma.add(menuItemLimpar);

		menuFluxograma.addSeparator();

        JMenu menuElemento = new JMenu("Elemento");
        menuElemento.setMnemonic(KeyEvent.VK_E);

        menuItemTexto = new JMenuItem("Editar Texto");
        menuItemTexto.setMnemonic(KeyEvent.VK_E);
        menuItemTexto.addActionListener(tratadorMenus);
        menuItemTexto.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK)
        );
        menuElemento.add(menuItemTexto);

        menuItemRemover = new JMenuItem("Remover");
        menuItemRemover.setMnemonic(KeyEvent.VK_R);
        menuItemRemover.addActionListener(tratadorMenus);
        menuItemRemover.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK)
        );
        menuElemento.add(menuItemRemover);

        menuFluxograma.add(menuElemento);
        barraMenu.add(menuFluxograma);

        JMenu menuSobre = new JMenu("Sobre");
        menuSobre.setMnemonic(KeyEvent.VK_S);

        barraMenu.add(menuSobre);

        this.setJMenuBar(barraMenu);
	}

	private void iniciaBarraFerramentas() {
        grupoBotoesBarraFerramenta = new ButtonGroup();
        barraFerramentas = new JToolBar();
        barraFerramentas.setFloatable(false);
        
        botaoInicio = new JToggleButton("Inicio");
        botaoInicio.setSelected(true);
        grupoBotoesBarraFerramenta.add(botaoInicio);
        barraFerramentas.add(botaoInicio);

        botaoFim = new JToggleButton("Fim");
        grupoBotoesBarraFerramenta.add(botaoFim);
        barraFerramentas.add(botaoFim);

        botaoProcessamento = new JToggleButton("Processamento");
        grupoBotoesBarraFerramenta.add(botaoProcessamento);
        barraFerramentas.add(botaoProcessamento);

        botaoDecisao = new JToggleButton("Decisao");
        grupoBotoesBarraFerramenta.add(botaoDecisao);
        barraFerramentas.add(botaoDecisao);

        botaoSubrotina = new JToggleButton("Subrotina");
        grupoBotoesBarraFerramenta.add(botaoSubrotina);
        barraFerramentas.add(botaoSubrotina);

        botaoLinhaFluxo = new JToggleButton("LinhaFluxo");
        grupoBotoesBarraFerramenta.add(botaoLinhaFluxo);
        barraFerramentas.add(botaoLinhaFluxo);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weighty = 0;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridy = 0;

        this.add(barraFerramentas, gbc);
	}

    private void iniciaComponentes() {
//    	TratadorMouse tratadorMouse = new TratadorMouse();

        painelPrincipal = new PainelPrincipal(this);    	
        painelEstruturas = new PainelEstruturas(this);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.ipadx = 150;
        gbc.weighty = 1.0;
        gbc.weightx = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        this.add(getPainelEstruturas(), gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.weightx = 0;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        this.add(painelPrincipal, gbc);

        barraEstado = new JLabel(" ");
        barraEstado.setSize(this.getSize().width, barraEstado.getSize().height);
        barraEstado.setBackground(Color.WHITE);
    	barraEstado.setVisible(true);
    	barraEstado.setToolTipText("Barra de estado.");

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.ipadx = 1000;
        gbc.ipady = 0;
        gbc.weighty = 0;
        gbc.weightx = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridy = 2;
        this.add(barraEstado, gbc);

        iniciaPopupMenu();
    }

	private void iniciaPopupMenu() {
		menuPopup = new JPopupMenu();

		popupMenuItemLimpar = new JMenuItem("Limpar Fluxograma");
		popupMenuItemLimpar.addActionListener(tratadorMenus);
		popupMenuItemLimpar.setMnemonic(KeyEvent.VK_R);
		popupMenuItemLimpar.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK)
		);
		menuPopup.add(popupMenuItemLimpar);

		popupMenuItemTexto = new JMenuItem("Editar Texto");
		popupMenuItemTexto.addActionListener(tratadorMenus);
		popupMenuItemTexto.setMnemonic(KeyEvent.VK_E);
		popupMenuItemTexto.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK)
		);
		menuPopup.add(popupMenuItemTexto);

		popupMenuItemRemover = new JMenuItem("Remover Elemento");
		popupMenuItemRemover.addActionListener(tratadorMenus);
		popupMenuItemRemover.setMnemonic(KeyEvent.VK_R);
		popupMenuItemRemover.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK)
		);
		menuPopup.add(popupMenuItemRemover);
	}

	public void mensagemEstado(String msg) {
    	this.barraEstado.setText(msg);
    	Thread t= new Thread()  {
            public void run()  {
                 try {Thread.sleep(5000);} 
                 catch (Exception ex) {ex.printStackTrace();} 
                 SwingUtilities.invokeLater(new Runnable() {
                	 public void run() {
                		 barraEstado.setText(" ");
                	 }
                 });
            }
       };
       t.start();
    }

	private class TratadorMenus implements ActionListener {
		@Override
	    public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(menuItemAbrir)
					|| e.getSource().equals(menuItemSalvar)
					|| e.getSource().equals(menuItemExportarFluxo)
					|| e.getSource().equals(menuItemExportarProjeto)
			) {
				JFileChooser arquivoDialog = new JFileChooser();
				arquivoDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
				arquivoDialog.setAcceptAllFileFilterUsed(false);
				arquivoDialog.setCurrentDirectory(localDefault);
				arquivoDialog.setSelectedFile(new File(getTitle()));

				try { // exportar p/ imagem
			        if (e.getSource().equals(menuItemExportarFluxo)
							|| e.getSource().equals(menuItemExportarProjeto)
					) {
			        	arquivoDialog.setDialogTitle("Exportar p/Imagem");
				        arquivoDialog.setFileFilter(new FiltroSelecaoArquivo(tipoArquivo.IMAGEM));

				        int acao = arquivoDialog.showSaveDialog(FluxogramaFrame.this);

				        if (acao == JFileChooser.APPROVE_OPTION) {
				        	//remove a selecao
							painelPrincipal.limpaSelecao();

							//extensao default
				        	File arquivo = new File(arquivoDialog.getSelectedFile().getAbsolutePath());
				        	if (!arquivo.getName().endsWith("png")) {
				        		arquivo = new File(arquivo.getAbsolutePath() + ".png");
				        	}

							BufferedImage bi = new BufferedImage(
									painelPrincipal.getSize().width,
									painelPrincipal.getSize().height,
									BufferedImage.TYPE_INT_ARGB
							);

							try{
								Graphics g = bi.createGraphics();

								// exporta todos os fluxos p/ imagem
								if (e.getSource().equals(menuItemExportarProjeto)) {
									int i = 0;
									String nomeArquivo = arquivo.getName().split("\\.")[0];
									for (Object fluxo : painelEstruturas.getFluxogramas()) {
										painelPrincipal.setFluxograma((Fluxograma) fluxo);
										painelPrincipal.paint(g);
										ImageIO.write(bi,"png", arquivo);

										i++;
						        		arquivo = new File(
						        				arquivo.getParentFile() + File.separator +
						        				nomeArquivo + i + ".png"
						        		);
									}
								} else { // exporta somente o fluxo aberto
									painelPrincipal.paint(g);
									ImageIO.write(bi,"png",arquivo);
								}

								g.dispose();
								FluxogramaFrame.this.mensagemEstado("Arquivo exportado com sucesso! ("+arquivo.getName()+")");
							} catch (Exception eI) {
								FluxogramaFrame.this.mensagemEstado("Falha ao exportar para imagem!");
							}
				        }
					} else { // Abrir e Salvar
				        arquivoDialog.setFileFilter(new FiltroSelecaoArquivo(tipoArquivo.DOCUMENTO));

						if (e.getSource().equals(menuItemAbrir)) {
					        //mostra janela para abrir
					        int acao = arquivoDialog.showOpenDialog(FluxogramaFrame.this);
					        if (acao == JFileChooser.APPROVE_OPTION) {
								FileInputStream fis = new FileInputStream(arquivoDialog.getSelectedFile().getAbsolutePath());
								ObjectInputStream ois = new ObjectInputStream(fis);

								painelPrincipal.limpar();
								Fluxograma fluxo = (Fluxograma) ois.readObject();

								while(fluxo != null) {
					                painelEstruturas.addFluxograma(fluxo);
					                fluxo = (Fluxograma) ois.readObject();
								}

								ois.close();
					        }
						} else {
					        //mostra janela para salvar
					        int acao = arquivoDialog.showSaveDialog(FluxogramaFrame.this);

					        if (acao == JFileChooser.APPROVE_OPTION) {
					        	//remove a selecao
								painelPrincipal.limpaSelecao();
					  
								//extensao default
					        	File arquivo = new File(arquivoDialog.getSelectedFile().getAbsolutePath());
					        	if (!arquivo.getName().endsWith("flux")) {
					        		arquivo = new File(arquivo.getAbsolutePath() + ".flux");
					        	}
								FileOutputStream fis = new FileOutputStream(arquivo);
								ObjectOutputStream oos = new ObjectOutputStream(fis);

								for (Object fluxo : painelEstruturas.getFluxogramas()) {
									oos.writeObject(fluxo);
								}

								oos.close();
								FluxogramaFrame.this.mensagemEstado("Arquivo salvo com sucesso!");
								FluxogramaFrame.this.setTitle(arquivoDialog.getSelectedFile().getName());
					        }
						}
					}
				} catch (EOFException e1) {
					FluxogramaFrame.this.mensagemEstado("Arquivo carregado com sucesso.");
					FluxogramaFrame.this.setTitle(arquivoDialog.getSelectedFile().getName());
				} catch (Exception e1) {
					e1.printStackTrace();
					FluxogramaFrame.this.mensagemEstado("Erro na manipulação de arquivo.");
				}
			} else if (e.getSource().equals(menuItemNovo)) {
				painelPrincipal.limpar();
				getPainelEstruturas().limpar();
			} else if (e.getSource().equals(menuItemSair)) {
				FluxogramaFrame.this.dispose();
			} else if (e.getSource().equals(popupMenuItemLimpar)
					|| e.getSource().equals(menuItemLimpar)) {
				painelPrincipal.limpar();
			} else if (e.getSource().equals(popupMenuItemRemover)
					|| e.getSource().equals(menuItemRemover)
			) {
				if (! painelPrincipal.hasSelection()) {
					FluxogramaFrame.this.mensagemEstado("Selecione um item para remover.");
				} else {
					if (painelPrincipal.getFiguraSelecionada() instanceof Subrotina) {
						getPainelEstruturas().removeFluxograma(
								((Subrotina) painelPrincipal.getFiguraSelecionada()).getSubrotinaFluxo()
						);
					}
					painelPrincipal.removerSelecionado();
				}
			} else if (e.getSource().equals(popupMenuItemTexto)
					|| e.getSource().equals(menuItemTexto)
			) {
				if (! painelPrincipal.hasSelection()) {
					FluxogramaFrame.this.mensagemEstado("Selecione um item para editar.");					
				} else {
					ElementoFluxograma selecionado = ((ElementoFluxograma) painelPrincipal.getFiguraSelecionada());

					if (selecionado instanceof InicioFim) {
						FluxogramaFrame.this.mensagemEstado("Os elementos do tipo \"Início\" e \"Fim\" não podem ser editados.");
					} else {
						String novoTexto = JOptionPane.showInputDialog(
								FluxogramaFrame.this, "Entre com o novo texto:",
								selecionado.getTexto()
						);

						selecionado.setTexto(novoTexto);
						painelPrincipal.repaint();

						if (painelPrincipal.getFiguraSelecionada() instanceof Subrotina) {
							getPainelEstruturas().editaFluxograma(
									((Subrotina) painelPrincipal.getFiguraSelecionada()).getSubrotinaFluxo()
							);
						}
					}
				}
			}
		}
    }
}