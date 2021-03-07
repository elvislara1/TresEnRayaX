package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import sample.resources.Alertas;
import sample.main.Partida;

public class Controller {

    private static Partida partida = new Partida();
    //Hacemos referencia a elementos FXML
    //Botón de empezar y abandonar partida.
    @FXML
    Button button_empezar_partida, button_abandonar_partida;

    //TextAreas donde están las victorias/derrotas de ambos jugadores.
    @FXML
    TextArea vjugador1, vjugador2, djugador1, djugador2;

    //De quien es el turno.
    @FXML
    Text tj1, tj2;

    //Las casillas
    @FXML
    Button b0, b1, b2, b3, b4, b5, b6, b7, b8, bc;

    //Array de botones, para poder acceder a ellos de manera dinámica.
    @FXML
    Button[] listabotones = new Button[8];
    
    //Método Empezar partida
    @FXML
    public void empezarPartida(ActionEvent event) {

        //Obtenemos id del botón empezar partida, y asignamos al array de botones todos los botones clickables.
        button_empezar_partida = (Button) event.getSource();
        listabotones = new Button[]{b0, b1, b2, b3, b4, b5, b6, b7, b8};

        //Pedimos modo de juego al usuario
        partida.setModo(Alertas.eligeModo());

        if (partida.getModo() != null) {
            if (partida.getModo().equals("VSHumano")) {
                partida.empezarPartida();
                if (partida.getTurno() == 0) {
                    mostrarTurno(tj1);
                } else {
                    mostrarTurno(tj2);
                }
                ocultarBoton(button_empezar_partida);
                mostrarBoton(button_abandonar_partida);
            } else {
                //Pedimos dificultad al usuario
                String dificultad = Alertas.eligeDificultad();
                if (dificultad != null) {
                    partida.setIa(dificultad);
                    partida.empezarPartida();
                    ocultarBoton(button_empezar_partida);
                    mostrarBoton(button_abandonar_partida);
                    if (partida.getTurno() == 0) {
                        mostrarTurno(tj1);
                        if (partida.getModo().equals("MáquinaVSMáquina")) {
                            turnoCOM(partida.getCuadricula());
                        }
                    } else {
                        mostrarTurno(tj2);
                        turnoCOM(partida.getCuadricula());
                    }
                }
            }
        }
    }

    @FXML
    public void abandonarPartida(ActionEvent event) {

        button_abandonar_partida = (Button) event.getSource();
        Boolean respuesta = Alertas.abandonarPartida();

        if (respuesta) {
            Partida.abandonarPartida();
            restart();
        }
    }

    @FXML
    public void marcar(ActionEvent event) throws InterruptedException {

        //Si hemos inicado la partida, podremos marcar.
        if (partida.getEstado()) {
            bc = (Button) event.getSource();
            String sid = bc.getId().replaceAll("[b]", "");
            int id = Integer.valueOf(sid);
            char[] cuadricula = partida.getCuadricula();
            partida.propiedadesTurno();

            //Si la casilla NO está marcada, podremos marcarla.
            if (partida.estadoCuadricula(cuadricula[id])) {
                bc.styleProperty().setValue("-fx-text-fill: " + partida.getColor() + ";");
                bc.setText(partida.getValue());
                partida.setPosCuadricula(id, partida.getValue().charAt(0));

                //Comprobamos si se dan las condiciones de Victoria.
                if (partida.comprobarVictoria(partida.getValue().charAt(0))) {
                    Partida.anunciarGanador(partida.getValue().charAt(0));
                    restart();
                    partida.finalizarPartida();
                } else {
                    if (partida.cuadriculaLLena()) {
                        Alertas.anunciarEmpate();
                        restart();
                        partida.finalizarPartida();
                    } else {
                        if (partida.getTurno() == 0) {
                            setTurno(1, tj1, tj2);
                        } else {
                            setTurno(0, tj2, tj1);
                        }
                    }
                }
                if (!partida.getModo().equals("VSHumano") && partida.getEstado()) {
                    turnoCOM(cuadricula);
                }
            }
        } else {
            Alertas.debesEmpezarPartida();
        }
    }

    //Método que se ejecuta cuando es el Turno de Maquina, seteando las propiedades del turno,
    //y eligiendo movimiento a realizar segun dificultad elegida.
    private void turnoCOM(char[] cuadricula) {
        partida.propiedadesTurno();
        if (partida.getIa().equals("Fácil")) {
            IAFacil(cuadricula);
        } else if (partida.getIa().equals("Normal")) {
            IAFacil(cuadricula);
        } else {
            //TODO
            IAFacil(cuadricula);
        }

        if (partida.getEstado()) {
            if (partida.getModo().equals("MáquinaVSMáquina")) {
                if (partida.getTurno() == 0) {
                    setTurno(1, tj1, tj2);
                } else {
                    setTurno(0, tj2, tj1);
                }
                turnoCOM(cuadricula);
            } else {
                setTurno(0, tj2, tj1);
            }
        }
    }

    //Método Facil, La asignación de casilla es aleatoria, siempre y cuando no esté ocupada.

    private void IAFacil(char[] cuadricula) {
        int random;
        do {
            random = (int) (Math.random() * 10 - 1);
        } while (!partida.estadoCuadricula(cuadricula[random]));
        listabotones[random].styleProperty().setValue("-fx-text-fill: " + partida.getColor() + ";");
        listabotones[random].setText(partida.getValue());
        partida.setPosCuadricula(random, partida.getValue().charAt(0));
        if (partida.comprobarVictoria(partida.getValue().charAt(0))) {
            Partida.anunciarGanador(partida.getValue().charAt(0));
            restart();
            partida.finalizarPartida();
        } else {
            if (partida.cuadriculaLLena()) {
                Alertas.anunciarEmpate();
                restart();
                partida.finalizarPartida();
            }
        }
    }

    //Método que se encarga de ocultar el Botón indicado.
    private void ocultarBoton(Button button) {
        button.styleProperty().setValue("Visibility: false");
    }

    //Método que se encarga de Mostrar el Botón indiciado.
    private void mostrarBoton(Button button) {
        button.styleProperty().setValue("Visibility: true");
    }

    //Método que se encarga de mostrar el Texto del turno
    private void mostrarTurno(Text tj) {
        tj.styleProperty().setValue("Visibility:true");
    }

    //Método que se encarga de ocultar el Texto del turno.
    private void ocultarTurno(Text tj) {
        tj.styleProperty().setValue("Visibility: false");
    }

    //Método que se encarga de refrescar el marcador visual.
    private void refrescarMarcador(Partida partida, TextArea vj1, TextArea vj2, TextArea dj1, TextArea dj2) {
        vj1.setText(String.valueOf(partida.getVjugador1()));
        vj2.setText(String.valueOf(partida.getVjugador2()));
        dj1.setText(String.valueOf(partida.getDjugador1()));
        dj2.setText(String.valueOf(partida.getDjugador2()));
    }

    //Método que se encarga de vaciar la cuadricula. (RESET)
    private void vaciarCuadriculaFX() {
        for (Button button : listabotones) {
            button.textProperty().setValue("");
        }
    }

    //Método que se encarga de poner el tablero por default.
    private void restart() {
        refrescarMarcador(partida, vjugador1, vjugador2, djugador1, djugador2);
        ocultarTurno(tj1);
        ocultarTurno(tj2);
        ocultarBoton(button_abandonar_partida);
        mostrarBoton(button_empezar_partida);
        vaciarCuadriculaFX();
    }

    //Método que se encarga de SETEAR los turnos.
    private void setTurno(int turno, Text turnoOcultar, Text turnoMostrar) {
        partida.setTurno(turno);
        ocultarTurno(turnoOcultar);
        mostrarTurno(turnoMostrar);
    }
}