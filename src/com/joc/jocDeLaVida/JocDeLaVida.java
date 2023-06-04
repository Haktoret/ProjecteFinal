package com.joc.jocDeLaVida;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.projecte.paneles.PanelInicio;

public class JocDeLaVida extends JFrame{
// Constants per a canvair facilment el tamany
	public static final int XICOTET_X = 40;
	public static final int XICOTET_Y = 40;
	public static final int MITJANA_X = 60;
	public static final int MITJANA_Y = 60;
	public static final int GRAN_X = 60;
	public static final int GRAN_Y = 90;

// GridLayout que anirem canviant cada vegada
	private static GridLayout gridLayout = new GridLayout();

//JFrame
//	private static JFrame vida;

// JPanel
	private JPanel pantalla = new JPanel();
	private JPanel barra = new JPanel();
	private static JPanel[][] xicotet = new JPanel[XICOTET_X][XICOTET_Y];
	private static JPanel[][] mitjana = new JPanel[MITJANA_X][MITJANA_Y];
	private static JPanel[][] gran = new JPanel[GRAN_X][GRAN_Y];

// Botones barra
	private static JButton atras = new JButton("TAMANY");
	private static JButton ajuda = new JButton("AJUDA");
	private static JButton start = new JButton("START");
	private static JButton next = new JButton("NEXT");
	private static JButton reset = new JButton("RESET");
	private static JButton ajustes = new JButton("AJUSTES");

// Botons de tamany
	private static JButton b1 = new JButton("Xicotet");
	private static JButton b2 = new JButton("Mitjà");
	private static JButton b3 = new JButton("Gran");

//Arraylists i contador
	private static ArrayList<String> coordenadasInicial = new ArrayList<String>();
	private static ArrayList<String> coordenadasRevivir = new ArrayList<String>();
	private static ArrayList<String> coordenadasMorir = new ArrayList<String>();
	private static int contadorCuadrosRevivir = 0;
	private static int contadorCuadrosMorir = 0;

//Random per a marcar cuadro inicialment
	private static Random r = new Random();
	private static int xinicial = r.nextInt(GRAN_X), x;
	private static int yinicial = r.nextInt(GRAN_Y), y;
	private static int numeroCelulesRepartides;
//Numero de cuadros que apareixeran inicialment
	private static int amarillos;
//Contador per al while per a que es pinten tots els que marque "amarillos"
	private static int contador = 0;

//Numero de generacions
	private static int numeroGeneracions = 0;

//Numero Celules Creades durant tot el programa
	private static int numeroCelulesCreades = 0;

//Timer
	private static int temps = 1000; // Temps en milisegundos (1 segundo)

	public int getTemps() {
		return temps;
	}

	public void setTemps(int temps) {
		this.temps = temps;
	}

	private static Timer timer;
	private static TimerTask tarea;
	
	

	public JocDeLaVida() {
		super("Joc de la vida");
		
		this.addWindowListener(new WindowAdapter() {

			
			public void windowClosing(WindowEvent e) {
				PanelInicio.setContadorJoc(0);
			}
			
		});
		
//Canviar esto de baix, en este cas que es joc de la vida ficarli dispose()

//Panell de contingut
		Container panell = this.getContentPane();
		panell.setLayout(new GridBagLayout());

//Crear las restricciones de celda para los botones
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets.set(10, 10, 10, 10); // Agregar margen a los botones
		constraints.anchor = GridBagConstraints.CENTER; // Centrar los botones horizontalmente

//Agregar el primer botón
		panell.add(b1, constraints);

//Incrementar la posición en el eje X
		constraints.gridx++;
//Agregar el segundo botón

		panell.add(b2, constraints);
//Incrementar la posición en el eje X
		constraints.gridx++;

//Agregar el tercer botón
		panell.add(b3, constraints);

		pantalla.setBackground(Color.gray);
		barra.setBackground(Color.gray);

		b1.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {
				gridLayout = new GridLayout(XICOTET_X, XICOTET_Y);

//Iniciar joc de la vida
				pantallaJocDeLaVida(panell, gridLayout, pantalla, xinicial, yinicial, amarillos, contador, XICOTET_X,
						XICOTET_Y, xicotet, barra);

//Redimensionar JFrame
				setSize(600, 500);
			}

		});

		b2.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {
				gridLayout = new GridLayout(MITJANA_X, MITJANA_Y);

//Iniciar joc de la vida
				pantallaJocDeLaVida(panell, gridLayout, pantalla, xinicial, yinicial, amarillos, contador, MITJANA_X,
						MITJANA_Y, mitjana, barra);

//Redimensionar tamany JFrame
				setSize(800, 800);
			}

		});

		b3.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {
				gridLayout = new GridLayout(GRAN_X, GRAN_Y);

//Iniciar joc de la vida
				pantallaJocDeLaVida(panell, gridLayout, pantalla, xinicial, yinicial, amarillos, contador, GRAN_X,
						GRAN_Y, gran, barra);

//Redimensionar tamany JFrame
				setSize(1200, 800);
			}

		});

		atras.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {
				panell.setLayout(new GridBagLayout());
				panell.removeAll();

//Reiniciar generacions i cèlules creades
				numeroGeneracions = 0;
				numeroCelulesCreades = 0;

//Tornar a 0 l'array i els contadors perque després no ixquen en les altres partides
				coordenadasRevivir.clear();
				coordenadasMorir.clear();
				coordenadasInicial.clear();
				contadorCuadrosRevivir = 0;
				contadorCuadrosMorir = 0;

//Agregar el primer botón
				panell.add(b1, constraints);

//Incrementar la posición en el eje X
				constraints.gridx++;

//Agregar el segundo botón
				panell.add(b2, constraints);

//Incrementar la posición en el eje X
				constraints.gridx++;

//Agregar el tercer botón
				panell.add(b3, constraints);

//Repintar
				panell.repaint();
				panell.revalidate();

				setSize(700, 600);

			}

		});

		ajuda.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//Instruccions del joc
				JOptionPane.showMessageDialog(null, // contenidor d'alt nivell

						"Regles:\n" + "• Tota cèl·lula viva amb menys de dos veïnes vives mor (de solitud).\n"

								+ "• Tota cèl·lula viva amb més de tres veïnes vives mor (d’excés de concentració).\n"

								+ "• Tota cèl·lula viva amb dos o tres veïnes vives, segueix viva per a la següent\n"

								+ "   generació.\n"

								+ "• Tota cèl·lula morta amb exactament tres veïnes vives torna a la vida.", // text

						"Ajuda", // títol del diàleg

						JOptionPane.INFORMATION_MESSAGE);

			}

		});

		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

//Tornar a 0 l'array i els contadors per a que no s'acumulen
				coordenadasRevivir.clear();
				coordenadasMorir.clear();
				contadorCuadrosRevivir = 0;
				contadorCuadrosMorir = 0;

//Comprovem en quina pantalla estem
				if (gridLayout.getRows() == XICOTET_X && gridLayout.getColumns() == XICOTET_Y) {
					nextBoto(xicotet);
				} else if (gridLayout.getRows() == MITJANA_X && gridLayout.getColumns() == MITJANA_Y) {
					nextBoto(mitjana);
				} else if (gridLayout.getRows() == GRAN_X && gridLayout.getColumns() == GRAN_Y) {
					nextBoto(gran);
				}

//Quan s'eliminen totes les cèlules
				bannerPartidaAcabada();
			}

		});

		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

//Reiniciar generacions i cèlules creades
				numeroGeneracions = 0;
				numeroCelulesCreades = 0;

//Comprovem en quina pantalla estem
				if (gridLayout.getRows() == XICOTET_X && gridLayout.getColumns() == XICOTET_Y) {
					repintarInici(xicotet);
				} else if (gridLayout.getRows() == MITJANA_X && gridLayout.getColumns() == MITJANA_Y) {
					repintarInici(mitjana);
				} else if (gridLayout.getRows() == GRAN_X && gridLayout.getColumns() == GRAN_Y) {
					repintarInici(gran);
				}

			}

		});

		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

//Canviar el text quan s'aprete el botó
				if (start.getText() == "START") {
					start.setText("PAUSE");

					barra.removeAll();

//Anyadir a JPanel
					barra.add(ajuda);
					barra.add(start);
					barra.repaint();
					barra.revalidate();

				} else if (start.getText() == "PAUSE") {
					start.setText("START");
					barra.removeAll();

//Anyadir a JPanel
					barra.add(atras);
					barra.add(ajuda);
					barra.add(start);
					barra.add(next);
					barra.add(reset);
					barra.add(ajustes);
					barra.repaint();
					barra.revalidate();
				}

				if (gridLayout.getRows() == XICOTET_X && gridLayout.getColumns() == XICOTET_Y) {
					timerStart(xicotet);
				} else if (gridLayout.getRows() == MITJANA_X && gridLayout.getColumns() == MITJANA_Y) {
					timerStart(mitjana);
				} else if (gridLayout.getRows() == GRAN_X && gridLayout.getColumns() == GRAN_Y) {
					timerStart(gran);
				}

//Quan s'eliminen totes les cèlules
				bannerPartidaAcabada();

			}

		});

		ajustes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String resposta = "";
				boolean acertado = false;
				while (resposta != null && !acertado) {

					resposta = JOptionPane.showInputDialog(null, // contenidor
							"Fica lo rapit que vols que vatja en segons(Número enter del 1 al 10)"); // text

					if (resposta != null && !Pattern.compile("[^0-9]").matcher(resposta).find()) {
						if (Integer.parseInt(resposta) > -1 && Integer.parseInt(resposta) < 10) {
							acertado = true;
							setTemps(Integer.parseInt(resposta) * 1000);
						}
					}

				}
			}

		});

//Donar aspecte a la finestra i mostrar
		setSize(700, 600);
		setResizable(false);
		setVisible(true);

	}

//Quan polse el botó "NEXT" se farán totes les comprobacions posibles per cada celda, una per una, per vore
//qui viu, qui reviviu i qui mor per a la següent generació
	public static void nextBoto(JPanel[][] grandaria) {

//Sumar cada vegada que es cree una nova generació
		numeroGeneracions++;

		for (int i = 0; i < grandaria.length; i++) {

			for (int j = 0; j < grandaria[i].length; j++) {

				int amarillosVecinos = 0;

//Comprovem que no esta colorejat ja
				if (grandaria[i][j].getBackground() != Color.yellow) {
//Comprovem els quadrats d'alrededor, les 8 posicions posibles
//per vore qui esta colorejat per a que revivisca la celula morta o no
//Farem les comprobacions necesaries per a que no done error si
// estem en una posició propera als bordes

					amarillosVecinos = busquedaCelulasVivas(grandaria, i, j, amarillosVecinos);

					if (amarillosVecinos == 3) {
// Guardar coordenades en l'Array per a després coloretjar
						coordenadasRevivir.add(i + ":" + j);

// contador per al bucle de després i colorearlos tots d'una
						contadorCuadrosRevivir++;
					}

//Si el cuadro esta groc(celula viva) vetgem si tens els suficients veins com per sobreviure
				} else {
// Comprovem els quadrats d'alrededor, les 8 posicions posibles
// per vore qui NO esta colorejat per a que morga la celula
// Farem les comprobacions necesaries per a que no done error si
// estem en una posició propera als bordes

					amarillosVecinos = busquedaCelulasVivas(grandaria, i, j, amarillosVecinos);

					if (amarillosVecinos != 3 && amarillosVecinos != 2) {
//Guardar coordenades en l'Array per a després coloretjar
						coordenadasMorir.add(i + ":" + j);
//contador per al bucle de després i colorearlos tots d'una
						contadorCuadrosMorir++;
					}
				}
			}
		}

//Sumar les celules revivides
		numeroCelulesCreades += coordenadasRevivir.size();

//Pintar següent generació
		seguentGeneracio(contadorCuadrosRevivir, contadorCuadrosMorir, coordenadasRevivir, coordenadasMorir, grandaria);

	}

	public static void anyadirJPanels(JPanel[][] tamany, JPanel pantalla) {
// JPanels per a la pantalla
		for (int i = 0; i < tamany.length; i++) {
			for (int j = 0; j < tamany[i].length; j++) {
				tamany[i][j] = new JPanel();
				tamany[i][j].setBackground(Color.gray);
				tamany[i][j].setBorder(BorderFactory.createLineBorder(Color.white));
				pantalla.add(tamany[i][j]);
			}
		}
	}

//Pintar les primers celdes aleatoriament
	public static void pintarInicialment(int xinicial, int yinicial, int amarillos, JPanel[][] tamany, int rows,
			int cols, int contador) {
//Marcar celules vives inicials
		tamany[xinicial][yinicial].setBackground(Color.yellow);
//Guardar coordenades inicials per al el botó del Reset
		coordenadasInicial.add(xinicial + ":" + yinicial);

		while (contador < amarillos) {
// Comprobacion X
			if (xinicial == 0) {
				x = xinicial + (r.nextInt(4) + 1);
			} else if (xinicial == 1) {
// Aleatorio restar o sumar
				if (r.nextInt(11) % 2 == 0) {
					x = xinicial + (r.nextInt(3) + 1);
				} else {
					x = xinicial - 1;
				}
			} else if (xinicial == rows - 2) {
// Aleatorio restar o sumar
				if (r.nextInt(11) % 2 == 0) {
					x = xinicial - (r.nextInt(3) + 1);
				} else {
					x = xinicial + 1;
				}
			} else if (xinicial == rows - 1) {
				x = xinicial - (r.nextInt(4) + 1);
			} else {
// Aleatorio restar o sumar
				if (r.nextInt(11) % 2 == 0) {
					x = xinicial + (r.nextInt(2) + 1);
				} else {
					x = xinicial - (r.nextInt(2) + 1);
				}
			}

// Comprobacion Y
			if (yinicial == 0) {
				y = yinicial + (r.nextInt(4) + 1);
			} else if (yinicial == 1) {
// Aleatorio restar o sumar
				if (r.nextInt(11) % 2 == 0) {
					y = yinicial + (r.nextInt(3) + 1);
				} else {
					y = yinicial - 1;
				}
			} else if (yinicial == cols - 2) {
// Aleatorio restar o sumar
				if (r.nextInt(11) % 2 == 0) {
					y = yinicial - (r.nextInt(3) + 1);
				} else {
					y = yinicial + 1;
				}
			} else if (yinicial == cols - 1) {
				y = yinicial - (r.nextInt(4) + 1);
			} else {
// Aleatorio restar o sumar
				if (r.nextInt(11) % 2 == 0) {
					y = yinicial + (r.nextInt(2) + 1);
				} else {
					y = yinicial - (r.nextInt(2) + 1);
				}
			}

// Comprobació de si no esta pintat ja seguira el bucle fins que se pinten tots
			if (tamany[x][y].getBackground() != Color.yellow) {
				tamany[x][y].setBackground(Color.yellow);
				contador++;
//Guardar coordenades en l'Array per al botó reset
				coordenadasInicial.add(x + ":" + y);

			}
		}
		numeroCelulesCreades = coordenadasInicial.size();
	}

	public static void eliminarPanells(Container panell, JPanel pantalla, JPanel barra) {
		panell.removeAll();
		pantalla.removeAll();
		barra.removeAll();
	}

	public static void repintarPanells(Container panell, JPanel pantalla, JPanel barra) {
		panell.repaint();
		panell.revalidate();
		pantalla.repaint();
		pantalla.revalidate();
		barra.repaint();
		barra.revalidate();
	}

//Principi de búsqueda de cèlules veines per cada celda
//--------------------------------------------------------------------------------->
//Buscar en la ultima celda de la fila, a l'esquerre del tot
	public static int rowEsquinaEsquerreSuperior(int i, int j, JPanel[][] grandaria, int amarillosVecinos) {
		if (grandaria[i][j + 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i + 1][j].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i + 1][j + 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		return amarillosVecinos;
	}

//Buscar en la ultima celda de la fila, a la dreta del tot
	public static int rowEsquinaDretaSuperior(int i, int j, JPanel[][] grandaria, int amarillosVecinos) {
		if (grandaria[i][j - 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i + 1][j].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i + 1][j - 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}
		return amarillosVecinos;
	}

//Buscar en la primera fila superior
	public static int rowSuperior(int i, int j, JPanel[][] grandaria, int amarillosVecinos) {
		if (grandaria[i][j - 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i][j + 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i + 1][j].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i + 1][j - 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i + 1][j + 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}
		return amarillosVecinos;
	}

//Buscar en la primera celda, en l'ultima fila inferior
	public static int rowEsquinaEsquerreInferior(int i, int j, JPanel[][] grandaria, int amarillosVecinos) {
		if (grandaria[i][j + 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i - 1][j].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i - 1][j + 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}
		return amarillosVecinos;
	}

//Buscar en la última celda, en l'ultima fila inferior
	public static int rowEsquinaDretaInferior(int i, int j, JPanel[][] grandaria, int amarillosVecinos) {
		if (grandaria[i][j - 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i - 1][j].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i - 1][j - 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}
		return amarillosVecinos;
	}

//Buscar en la ultima fila inferior
	public static int rowInferior(int i, int j, JPanel[][] grandaria, int amarillosVecinos) {
		if (grandaria[i][j - 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i][j + 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i - 1][j].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i - 1][j + 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i - 1][j - 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}
		return amarillosVecinos;
	}

//Buscar en la ultima columna esquerre
	public static int colEsquerre(int i, int j, JPanel[][] grandaria, int amarillosVecinos) {
		if (grandaria[i][j + 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i - 1][j].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i + 1][j].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i - 1][j + 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i + 1][j + 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		return amarillosVecinos;
	}

//Buscar en la ultima columna de la dreta
	public static int colDreta(int i, int j, JPanel[][] grandaria, int amarillosVecinos) {
		if (grandaria[i][j - 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i - 1][j].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i + 1][j].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i - 1][j - 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}

		if (grandaria[i + 1][j - 1].getBackground() == Color.yellow) {

			amarillosVecinos++;

		}
		return amarillosVecinos;
	}

//Buscar al mig de la pantalla
	public static int intermedio(int i, int j, JPanel[][] grandaria, int amarillosVecinos) {
		if (grandaria[i][j - 1].getBackground() == Color.yellow) {
			amarillosVecinos++;
		}
		if (grandaria[i][j + 1].getBackground() == Color.yellow) {
			amarillosVecinos++;
		}
		if (grandaria[i - 1][j].getBackground() == Color.yellow) {
			amarillosVecinos++;
		}
		if (grandaria[i + 1][j].getBackground() == Color.yellow) {
			amarillosVecinos++;
		}
		if (grandaria[i - 1][j - 1].getBackground() == Color.yellow) {
			amarillosVecinos++;
		}
		if (grandaria[i - 1][j + 1].getBackground() == Color.yellow) {
			amarillosVecinos++;
		}
		if (grandaria[i + 1][j - 1].getBackground() == Color.yellow) {
			amarillosVecinos++;
		}
		if (grandaria[i + 1][j + 1].getBackground() == Color.yellow) {
			amarillosVecinos++;
		}
		return amarillosVecinos;
	}
//<-------------------------------------------------------------------------------
//Fin de busqueda de cèlules veines per cada celda

//Buscar celules alrededor de cada una i vore quans veins vius te amb els mètodes anteriors
	public static int busquedaCelulasVivas(JPanel[][] grandaria, int i, int j, int amarillosVecinos) {
		if (i == 0) {
			if (j == 0) {
				amarillosVecinos = rowEsquinaEsquerreSuperior(i, j, grandaria, amarillosVecinos);
			} else if (j == grandaria[i].length - 1) {
				amarillosVecinos = rowEsquinaDretaSuperior(i, j, grandaria, amarillosVecinos);
			} else {
				amarillosVecinos = rowSuperior(i, j, grandaria, amarillosVecinos);
			}
		} else if (i == grandaria.length - 1) {

			if (j == 0) {
				amarillosVecinos = rowEsquinaEsquerreInferior(i, j, grandaria, amarillosVecinos);
			} else if (j == grandaria[i].length - 1) {
				amarillosVecinos = rowEsquinaDretaInferior(i, j, grandaria, amarillosVecinos);
			} else {
				amarillosVecinos = rowInferior(i, j, grandaria, amarillosVecinos);
			}
		} else if (j == 0) {
			amarillosVecinos = colEsquerre(i, j, grandaria, amarillosVecinos);
		} else if (j == grandaria[i].length - 1) {
			amarillosVecinos = colDreta(i, j, grandaria, amarillosVecinos);
		} else {
			amarillosVecinos = intermedio(i, j, grandaria, amarillosVecinos);
		}
		return amarillosVecinos;
	}

//Revivir, mantindre i eliminar cèlules
	public static void seguentGeneracio(int contadorCuadrosRevivir, int contadorCuadrosMorir,
			ArrayList<String> coordenadasRevivir, ArrayList<String> coordenadasMorir, JPanel[][] grandaria) {
//Coloretgem les celules que revivisquen
		for (int h = 0; h < contadorCuadrosRevivir; h++) {
// Separar les coordenaes
			String[] coordenada = coordenadasRevivir.get(h).split(":");
//Inicialitzar x e y
			int x = Integer.parseInt(coordenada[0]);
			int y = Integer.parseInt(coordenada[1]);
//Canviar color
			grandaria[x][y].setBackground(Color.yellow);
		}

//Coloretgem les celules que mortes
		for (int h = 0; h < contadorCuadrosMorir; h++) {
// Separar les coordenaes
			String[] coordenada = coordenadasMorir.get(h).split(":");
//Inicialitzar x e y
			int x = Integer.parseInt(coordenada[0]);
			int y = Integer.parseInt(coordenada[1]);
//Canviar color
			grandaria[x][y].setBackground(Color.gray);

		}
	}

//Agarrar array que hem inicialitzat al obrir la pestanya amb el tamany que voliem
//i imprimirlo altra vegada per reiniciar la pantalla
	public void repintarInici(JPanel[][] tamany) {
//Pintar tot en gris
		for (int i = 0; i < tamany.length; i++) {
			for (int j = 0; j < tamany[i].length; j++) {
				tamany[i][j].setBackground(Color.gray);
			}
		}
//Pintar en groc la posicio inicial amb l'array guardat anteriorment
		for (int i = 0; i < coordenadasInicial.size(); i++) {
// Separar les coordenaes
			String[] coordenada = coordenadasInicial.get(i).split(":");
//Inicialitzar x e y
			int x = Integer.parseInt(coordenada[0]);
			int y = Integer.parseInt(coordenada[1]);
//Canviar color
			tamany[x][y].setBackground(Color.yellow);
		}
	}

//Inici del programa nomes elegim el tamany
	public void pantallaJocDeLaVida(Container panell, GridLayout gridLayout, JPanel pantalla, int xinicial,
			int yinicial, int amarillos, int contador, int x, int y, JPanel[][] tamany, JPanel barra) {

		panell.setLayout(new BorderLayout());
		pantalla.setLayout(gridLayout);

//Eliminar panells
		eliminarPanells(panell, pantalla, barra);

// JPanels per a la pantalla
		anyadirJPanels(tamany, pantalla);

//Numero de vecindaris de cèl·lules per dir-ho d'una manera
		celulesRepartides(tamany, xinicial, yinicial, amarillos, contador, x, y);

//Anyadir barra i pantalla nova
		panell.add(barra, BorderLayout.SOUTH);
		panell.add(pantalla);

//Anyadir Botons a Barra
		anyadirBarra();

// Repintar panells
		repintarPanells(panell, pantalla, barra);
	}

//Crear celules per diferents seccions
	public void celulesRepartides(JPanel[][] tamany, int xinicial, int yinicial, int amarillos, int contador, int x,
			int y) {

//Numero de celules a repartir
		numeroCelulesRepartides = (r.nextInt(4) + 1);

		for (int i = 0; i < numeroCelulesRepartides; i++) {
//Random per a marcar cuadro inicialment
			xinicial = r.nextInt(x);
			yinicial = r.nextInt(y);
//Numero de cuadros que apareixeran inicialment
			amarillos = (r.nextInt(11) + 5);

//Contador per al while per a que es pinten tots els que marque "amarillos"
			contador = 0;

//Pintar primeres cèl·lules
			pintarInicialment(xinicial, yinicial, amarillos, tamany, x, y, contador);
		}

	}

//Anyadir botons a la barra
	public void anyadirBarra() {
		barra.add(atras);
		barra.add(ajuda);
		barra.add(start);
		barra.add(next);
		barra.add(reset);
		barra.add(ajustes);
	}

	public static boolean comprobarCelulesExterminaes(JPanel[][] tamany) {
		boolean trobat = false;
		for (int i = 0; i < tamany.length; i++) {
			for (int j = 0; j < tamany[i].length; j++) {
				if (tamany[i][j].getBackground() == Color.yellow) {
					trobat = true;
				}
			}
		}
		return trobat;
	}

	public static void timerStart(JPanel[][] tamany) {
		if (start.getText() == "PAUSE") {
//Creem nou timer cada i nova tarea cada vegada
			timer = new Timer();
			tarea = new TimerTask() {
				public void run() {

//Tornar a 0 l'array i els contadors per a que no s'acumulen
					coordenadasRevivir.clear();
					coordenadasMorir.clear();
					contadorCuadrosRevivir = 0;
					contadorCuadrosMorir = 0;

// Accions a realitzar cada vega que es complete el timer
// Comprovem en quina pantalla estem
					if (gridLayout.getRows() == XICOTET_X && gridLayout.getColumns() == XICOTET_Y) {
						nextBoto(xicotet);
					} else if (gridLayout.getRows() == MITJANA_X && gridLayout.getColumns() == MITJANA_Y) {
						nextBoto(mitjana);
					} else if (gridLayout.getRows() == GRAN_X && gridLayout.getColumns() == GRAN_Y) {
						nextBoto(gran);
					}
				}
			};

			timer.scheduleAtFixedRate(tarea, 0, temps); // Inicia el timer
		} else {
//Parem la tarea i el timer
			start.setText("START");
			timer.cancel();
			tarea.cancel();
		}
	}

	public static void bannerPartidaAcabada() {
		if (gridLayout.getRows() == XICOTET_X && gridLayout.getColumns() == XICOTET_Y) {
			if (!comprobarCelulesExterminaes(xicotet)) {

//Informació de partida
				JOptionPane.showMessageDialog(null, // contenidor d'alt nivell

						"Generacions de cèl·lules: " + numeroGeneracions + "\n" + "Número total de cèl·lules creades: "
								+ numeroCelulesCreades, // text

						"Informació de partida", // títol del diàleg

						JOptionPane.INFORMATION_MESSAGE);

//Reiniciar generacions i cèlules creades
				numeroGeneracions = 0;
				numeroCelulesCreades = 0;
			}
		} else if (gridLayout.getRows() == MITJANA_X && gridLayout.getColumns() == MITJANA_Y) {
			if (!comprobarCelulesExterminaes(mitjana)) {

//Reiniciar generacions i cèlules creades
				numeroGeneracions = 0;
				numeroCelulesCreades = 0;

//Informació de partida
				JOptionPane.showMessageDialog(null, // contenidor d'alt nivell

						"Generacions de cèl·lules: " + numeroGeneracions + "\n" + "Número total de cèl·lules creades: "
								+ numeroCelulesCreades, // text

						"Informació de partida", // títol del diàleg

						JOptionPane.INFORMATION_MESSAGE);

//Reiniciar generacions i cèlules creades
				numeroGeneracions = 0;
				numeroCelulesCreades = 0;
			}

		} else if (gridLayout.getRows() == GRAN_X && gridLayout.getColumns() == GRAN_Y) {
			if (!comprobarCelulesExterminaes(gran)) {

//Informació de partida
				JOptionPane.showMessageDialog(null, // contenidor d'alt nivell

						"Generacions de cèl·lules: " + numeroGeneracions + "\n" + "Número total de cèl·lules creades: "
								+ numeroCelulesCreades, // text

						"Informació de partida", // títol del diàleg

						JOptionPane.INFORMATION_MESSAGE);

//Reiniciar generacions i cèlules creades
				numeroGeneracions = 0;
				numeroCelulesCreades = 0;
			}

		}
	}
}