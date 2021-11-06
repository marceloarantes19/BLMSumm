/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marcelo
 */
public class Sentenca {
    private int num;
    private String sentenca;
    private List<Palavra> palavras = new ArrayList<Palavra>();
    private int numeroPalavras = 0;
    private float valor = 0;
    private boolean noSumario = false;

    public boolean isNoSumario() {
        return noSumario;
    }

    public void setNoSumario(boolean noSumario) {
        this.noSumario = noSumario;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<Palavra> getPalavras() {
        return palavras;
    }

    public void setPalavras(List<Palavra> palavras) {
        this.palavras = palavras;
    }

    public String getSentenca() {
        return sentenca;
    }

    public void setSentenca(String sentenca) {
        this.sentenca = sentenca;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void addValor() {
        this.valor = this.valor+1;
    }

    public void addPalavra(Palavra p) {
        this.palavras.add(p);
    }

    public int getNumeroPalavras() {
        return numeroPalavras;
    }

    public void setNumeroPalavras(int numeroPalavras) {
        this.numeroPalavras = numeroPalavras;
    }

    public void addNumPalavras() {
        this.numeroPalavras++;
    }

    public boolean contem(ArrayList<Sentenca> sentencas) {
        boolean ret = false;
        for(Sentenca s:sentencas) {
            if(this.equals(s)) {
                ret=true;
                break;
            }
        }
        return ret;
    }

    public int getQuantidadePalavraSentenca(Palavra p) {
        int ret = 0;
        for(Palavra pala:palavras) {
            if(p.getPalavra().equals(pala.getPalavra())) ++ret;
        }
        return ret;
    }
    public int getQuantidadeDePalavras() {
        return palavras.size();
    }
}
