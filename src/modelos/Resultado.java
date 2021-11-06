/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelos;

/**
 *
 * @author Marcelo
 */
public class Resultado {
    private String nomeEntrada;
    private int percentual;
    private int heuristica;
    private String nomeSaida;
    private int ci;
    private int cs;

    public Resultado(String nome1, int perc, int heu, String nome2, int i, int s) {
        nomeEntrada = nome1;
        percentual = perc;
        heuristica = heu;
        nomeSaida = nome2;
        ci = i;
        cs = s;
    }

    public String getNomeEntrada() {
        return nomeEntrada;
    }

    public void setNomeEntrada(String nomeEntrada) {
        this.nomeEntrada = nomeEntrada;
    }

    public String getNomeSaida() {
        return nomeSaida;
    }

    public void setNomeSaida(String nomeSaida1) {
        this.nomeSaida = nomeSaida1;
    }

    public int getPercentual() {
        return percentual;
    }

    public void setPercentual(int percentual) {
        this.percentual = percentual;
    }

    public int getHeuristica() {
        return heuristica;
    }

    public void setHeuristica(int heuristica) {
        this.heuristica = heuristica;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public int getCs() {
        return cs;
    }

    public void setCs(int cs) {
        this.cs = cs;
    }
}
