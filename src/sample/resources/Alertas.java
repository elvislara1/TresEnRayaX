package sample.resources;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alertas {

    //Utilizare las alertas para elegir el modo que jugaremos.
    public static String eligeModo() {
        String modo = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Elige un modo de juego");
        alert.setHeaderText("Humano VS Humano: Partida humano contra humano.\nHumano VS Máquina: Partida humano contra la Máquina.\nMáquina VS Máquina: Partida Máquina vs Máquina");

        //TODO Tres opciones, vs Humano, VS, COMVSCOM
        ButtonType btHumanoVsHumano = new ButtonType("VSHumano");
        ButtonType btHumanoVsCom = new ButtonType("VSMáquina");
        ButtonType btComVsCom = new ButtonType("MáquinaVSMáquina");

        alert.getButtonTypes().setAll(btHumanoVsHumano,btHumanoVsCom,btComVsCom,ButtonType.CLOSE);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == btHumanoVsHumano) {
            modo = "VSHumano";
        }else if(result.get() == btHumanoVsCom) {
            modo = "VSMáquina";
        }else if(result.get() == btComVsCom) {
            modo = "MáquinaVSMáquina";
        }else {
            alert.close();
            modo = null;
        }
        return modo;
    }

    //Alerta que nos permite elegir dificultad.
    public static String eligeDificultad() {
        String dificultad = null;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Vamos a jugar al tres en raya!");
        alert.setHeaderText("Elije una dificultad del juego.");

        ButtonType btFacil = new ButtonType("Fácil");
        ButtonType btNormal = new ButtonType("Normal");
        ButtonType btDificil = new ButtonType("Dificil");

        alert.getButtonTypes().setAll(btFacil, btNormal, btDificil, ButtonType.CLOSE);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == btFacil) {
            dificultad = "Fácil";
        } else if (result.get() == btNormal) {
            dificultad = "Normal";
        } else if (result.get() == btDificil) {
            dificultad = "Difícil";
        } else {
            alert.close();
            dificultad = null;
        }
        return dificultad;
    }

    //Alerta, abandonar la partida.
    public static Boolean abandonarPartida() {
        Boolean respuesta = null;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Estás seguro que deseas abandonar la partida?");
        alert.setHeaderText("(Si abandonas la partida, obtendrás una derrota.\nY el otro jugador sumará una victoria).");
        alert.getButtonTypes().setAll(ButtonType.CLOSE,ButtonType.YES,ButtonType.NO);

        //Alerta, esto es para recibir la opcion que ha escogido la persona
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.YES) {
            respuesta = true;
        }else{
            respuesta=false;
            alert.close();
        }
        return respuesta;
    }

    //Alerta mostrada al intentar jugar, mientras no está empezada la partida!!!
    public static void debesEmpezarPartida() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ERROR");
        alert.setHeaderText("No puedes jugar, sin antes hacer click en \"EMPEZAR PARTIDA\"");
        alert.getButtonTypes().setAll(ButtonType.CLOSE);
        alert.showAndWait();
    }

    //Alerta para anuciar al ganador.
    public  static void anunciarGanador(int ganador) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if(ganador == 0) {
            alert.setTitle("¡VICTORIA!");
            alert.setHeaderText("¡Enhorabuena jugador 1, has ganado!");
        }else {
            alert.setTitle("¡DERROTA!");
            alert.setHeaderText("¡Jugador 1, has perdido!");
        }
        alert.getButtonTypes().setAll(ButtonType.CLOSE);
        alert.showAndWait();
    }

    //TODO Haria falta un marcador el cual contenga los empates ?

    //Alerta mostrada al empatar.
    public  static void anunciarEmpate() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("¡EMPATE!");
        alert.setHeaderText("¡Ningun jugador ha logrado ganar al otro!");
        alert.getButtonTypes().setAll(ButtonType.CLOSE);
        alert.showAndWait();
    }
}

