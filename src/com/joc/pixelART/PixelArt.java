package com.joc.pixelART;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PixelArt extends JFrame {
    private JPanel[][] cuadricula;
    private Color colorSeleccionado;
    private boolean pintando;
    private boolean borrando;

    public PixelArt() {
        pedirTamanio();
        inicializarCuadricula();
        colorSeleccionado = Color.BLACK;
        pintando = false;
        borrando = false;

        setTitle("Pixel Art");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel panelCuadricula = new JPanel(new GridLayout(cuadroMida, cuadroMida));
        panelCuadricula.setPreferredSize(new Dimension(cuadroMida * 20, cuadroMida * 20));
        panelCuadricula.setMaximumSize(panelCuadricula.getPreferredSize());

        for (int i = 0; i < cuadroMida; i++) {
            for (int j = 0; j < cuadroMida; j++) {
                cuadricula[i][j] = new JPanel();
                cuadricula[i][j].setBackground(Color.WHITE);
                cuadricula[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));
                cuadricula[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            pintando = true;
                            cambiarColor((JPanel) e.getSource());
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            borrando = true;
                            borrarColor((JPanel) e.getSource());
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        pintando = false;
                        borrando = false;
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (pintando) {
                            cambiarColor((JPanel) e.getSource());
                        } else if (borrando) {
                            borrarColor((JPanel) e.getSource());
                        }
                    }
                });
                panelCuadricula.add(cuadricula[i][j]);
            }
        }

        getContentPane().add(panelCuadricula, BorderLayout.CENTER);
        getContentPane().add(createButtonPanel(), BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    private int cuadroMida;
    private int ventanaMida;

    private void pedirTamanio() {
        String[] options = {"Pequeño", "Mediano", "Grande"};
        int choice = JOptionPane.showOptionDialog(PixelArt.this, "Selecciona el tamaño del lienzo", "Tamaño", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            cuadroMida = 12;
            ventanaMida = 200;
        } else if (choice == 1) {
            cuadroMida = 24;
            ventanaMida = 300;
        } else if (choice == 2) {
            cuadroMida = 40;
            ventanaMida = 400;
        }
    }

    private void inicializarCuadricula() {
        cuadricula = new JPanel[cuadroMida][cuadroMida];

        for (int i = 0; i < cuadroMida; i++) {
            for (int j = 0; j < cuadroMida; j++) {
                cuadricula[i][j] = new JPanel();
                cuadricula[i][j].setBackground(Color.WHITE);
                cuadricula[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));
                cuadricula[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            pintando = true;
                            cambiarColor((JPanel) e.getSource());
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            borrando = true;
                            borrarColor((JPanel) e.getSource());
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        pintando = false;
                        borrando = false;
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (pintando) {
                            cambiarColor((JPanel) e.getSource());
                        } else if (borrando) {
                            borrarColor((JPanel) e.getSource());
                        }
                    }
                });
            }
        }
    }

    private void cambiarColor(JPanel panel) {
        panel.setBackground(colorSeleccionado);
    }

    private void borrarColor(JPanel panel) {
        panel.setBackground(Color.WHITE);
    }

    private void limpiarCuadricula() {
        for (int i = 0; i < cuadroMida; i++) {
            for (int j = 0; j < cuadroMida; j++) {
                cuadricula[i][j].setBackground(Color.WHITE);
            }
        }
    }

    private JPanel createButtonPanel() {
        JButton colorBoto = new JButton("Color");
        colorBoto.addActionListener(e -> {
            Color colorSeleccionat = JColorChooser.showDialog(PixelArt.this, "Selecciona un color", colorSeleccionado);
            if (colorSeleccionat != null) {
                colorSeleccionado = colorSeleccionat;
                pintando = true;
                borrando = false;
            }
        });

        JButton guardarBoto = new JButton("Guardar");
        guardarBoto.addActionListener(e -> {
            try {
                guardarCuadricula();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(PixelArt.this, "Error al guardar la partida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cargarBoto = new JButton("Cargar");
        cargarBoto.addActionListener(e -> {
            try {
                cargarCuadricula();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(PixelArt.this, "Error al cargar la partida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton instruccionesBoto = new JButton("Instrucciones");
        instruccionesBoto.addActionListener(e -> {
            JOptionPane.showMessageDialog(PixelArt.this, "Haz clic izquierdo para pintar.\nMantén el clic derecho para borrar.\nUtiliza el botón 'Color' para seleccionar un nuevo color.\nUtiliza el botón 'Borrar' para borrar todo.", "Instrucciones", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton limpiarBoto = new JButton("Limpiar");
        limpiarBoto.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(PixelArt.this, "¿Estás seguro de que quieres borrar todo?", "Limpiar", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                limpiarCuadricula();
            }
        });

        JButton tamañoBoto = new JButton("Tamaño");
        tamañoBoto.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(PixelArt.this, "¿Deseas guardar la partida antes de cambiar el tamaño?", "Guardar", JOptionPane.YES_NO_CANCEL_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                try {
                    guardarCuadricula();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(PixelArt.this, "Error al guardar la partida.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                return;
            }

            pedirTamanio();
            getContentPane().removeAll();
            inicializarCuadricula();
            JPanel panelCuadricula = new JPanel(new GridLayout(cuadroMida, cuadroMida));
            panelCuadricula.setPreferredSize(new Dimension(cuadroMida * 20, cuadroMida * 20));
            panelCuadricula.setMaximumSize(panelCuadricula.getPreferredSize());

            for (int i = 0; i < cuadroMida; i++) {
                for (int j = 0; j < cuadroMida; j++) {
                    panelCuadricula.add(cuadricula[i][j]);
                }
            }

            getContentPane().add(panelCuadricula, BorderLayout.CENTER);
            getContentPane().add(createButtonPanel(), BorderLayout.SOUTH);

            pack();
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(colorBoto);
        panelBotones.add(guardarBoto);
        panelBotones.add(cargarBoto);
        panelBotones.add(instruccionesBoto);
        panelBotones.add(limpiarBoto);
        panelBotones.add(tamañoBoto);

        return panelBotones;
    }

    private void guardarCuadricula() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            int ancho = cuadroMida;
            int alto = cuadroMida;

            BufferedImage image = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = image.createGraphics();

            for (int i = 0; i < ancho; i++) {
                for (int j = 0; j < alto; j++) {
                    Color color = cuadricula[i][j].getBackground();
                    graphics.setColor(color);
                    graphics.fillRect(i, j, 1, 1);
                }
            }

            graphics.dispose();
            ImageIO.write(image, "png", file);
            JOptionPane.showMessageDialog(this, "Cuadrícula guardada exitosamente.", "Guardar", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void cargarCuadricula() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            BufferedImage image = ImageIO.read(file);

            int ancho = Math.min(cuadroMida, image.getWidth());
            int alto = Math.min(cuadroMida, image.getHeight());

            for (int i = 0; i < ancho; i++) {
                for (int j = 0; j < alto; j++) {
                    int rgb = image.getRGB(i, j);
                    cuadricula[i][j].setBackground(new Color(rgb));
                }
            }

            JOptionPane.showMessageDialog(this, "Cuadrícula cargada exitosamente.", "Cargar", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
    	PixelArt paco = new PixelArt();
    	paco.setVisible(true);
    }
}

