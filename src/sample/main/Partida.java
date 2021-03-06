package sample.main;

import sample.resources.Alertas;

public class Partida {

    private static String ia;
    private static String modo;
    private static int vjugador1;
    private static int vjugador2;
    private static int djugador1;
    private static int djugador2;
    private int turno;
    private char[] cuadricula = new char[9];
    private static boolean estado = false;
    private String value;
    private String color;

    //Getters and setters

    public String getColor() { return color; }

    public String getValue() { return value; }

    public char[] getCuadricula() { return cuadricula; }

    public void setModo(String modo) {this.modo = modo;}

    public String getModo() {return modo;}

    public String getIa() { return ia; }

    public void setIa(String dificultad) { ia=dificultad;}

    public int getVjugador1() { return vjugador1; }

    public int getVjugador2() { return vjugador2; }

    public int getDjugador1() { return djugador1; }

    public int getDjugador2() { return djugador2; }

    public boolean getEstado() { return estado; }

    public void setTurno(int turno) { this.turno = turno;}

    public int getTurno() {return turno;}

    //Método que se encarga de elegir el primer turno. (Quien empezará)
    public int eleccionPrimerTurno() {
        int eleccion = (int) Math.random()*10;
        return eleccion;
    }

    //TODO Método que se encarga de Comprobar se cumplen las condiciones de victoria.

    public boolean comprobarVictoria(char value) {
        boolean victoria = false;
        if(cuadricula[0] == value && cuadricula[1] == value && cuadricula[2] == value) {
            victoria = true;
        }else if(cuadricula[3] == value && cuadricula[4] == value && cuadricula[5] == value) {
            victoria = true;
        }else if(cuadricula[6] == value && cuadricula[7] == value && cuadricula[8] == value) {
            victoria = true;
        }else if(cuadricula[0] == value && cuadricula[3] == value && cuadricula[6] == value) {
            victoria = true;
        }else if(cuadricula[1] == value && cuadricula[4] == value && cuadricula[7] == value) {
            victoria = true;
        }else if(cuadricula[2] == value && cuadricula[5] == value && cuadricula[8] == value) {
            victoria = true;
        }else if(cuadricula[0] == value && cuadricula[4] == value && cuadricula[8] == value) {
            victoria = true;
        }else if(cuadricula[2] == value && cuadricula[4] == value && cuadricula[6] == value) {
            victoria = true;
        }
        return victoria;
    }

    //Método que anunciar el ganador y actualiza marcadores.
    public static void anunciarGanador(char value) {
        if(value == 'X') {
            Alertas.anunciarGanador(0);
            vjugador1 = vjugador1+1;
            djugador2 = djugador2+1;
        }else {
            Alertas.anunciarGanador(1);
            vjugador2 = vjugador2+1;
            djugador1 = djugador1+1;
        }
    }

    //Método que se encarga de inicializar la partida.
    public void empezarPartida() {
        estado = true;
        if(eleccionPrimerTurno()<5) {
            turno = 0;
        }else {
            turno =1;
        }
    }

    //Método que se encarga de finalizar la partida.
    public void finalizarPartida() {
        estado = false;
        vaciarCuadricula();
    }

    //Método que se ejecuta al finalizar la partida.
    public static void abandonarPartida() {
        djugador1 = djugador1 +1;
        vjugador2 = vjugador2+1;
        estado = false;
    }

    //Método que comprueba si a cuadrícula está llena.
    public boolean cuadriculaLLena() {
        boolean resultado = true;
        for(int i=0;i<cuadricula.length;i++) {
            if(cuadricula[i] != 'X' && cuadricula[i] != 'O') {
                resultado = false;
                break;
            }
        }
        return resultado;
    }

    //(color de ficha, y tipo)
    public void propiedadesTurno() {
        if(turno==0) {
            value = "X";
            color = "blue";
        }else {
            value = "O";
            color = "red";
        }
    }

    //Método que setea la ficha en la cuadrícula.
    public void setPosCuadricula(int index,char value) {
        cuadricula[index] = value;
    }

    //Vacía la cuadrícula.
    public void vaciarCuadricula() {
        for (int i=0;i<cuadricula.length;i++) {
            cuadricula[i] = ' ';
        }
    }

    //Nos devuelve el estado de la cuadrícula. (Para saber si está ocupada o no)
    public boolean estadoCuadricula(char value) {
        boolean result;
        if(value != 'X' && value != 'O') {
            result = true;
        }else {
            result = false;
        }
        return result;
    }
}
