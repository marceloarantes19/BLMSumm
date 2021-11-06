/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelos;

import java.util.ArrayList;

/**
 *
 * @author Marcelo
 */
public class Texto {
    private ArrayList<String> texto = new ArrayList<String>();
    private ArrayList<Sentenca> sentencas = new ArrayList<Sentenca>();
    private ArrayList<Palavra> palavras = new ArrayList<Palavra>();
    private int totalPalavras = 0;
    private int compressao = 0;

    public int getTotalPalavras() {
        return totalPalavras;
    }

    public void setTotalPalavras(int totalPalavras) {
        this.totalPalavras = totalPalavras;
    }

    public int getCompressao() {
        return compressao;
    }

    public void setCompressao(int compressao) {
        this.compressao = compressao;
    }

    public ArrayList<Palavra> getPalavras() {
        return palavras;
    }

    public void setPalavras(ArrayList<Palavra> palavra) {
        this.palavras = palavra;
    }

    public void adicionaPalavra(Palavra p) {
        this.palavras.add(p);
    }
    
    public void adicionaTotalPalavras() {
        this.totalPalavras++;
    }

    public ArrayList<Sentenca> getSentencas() {
        return sentencas;
    }

    public void setSentencas(ArrayList<Sentenca> sentenca) {
        this.sentencas = sentenca;
    }
    
    public void adicionaSentenca(Sentenca s) {
        this.sentencas.add(s);
    }

    public ArrayList<String> getTexto() {
        return texto;
    }

    public void setTexto(ArrayList<String> texto) {
        this.texto = texto;
    }
    
    public int getQtdMaxPalavras() {
        return totalPalavras*compressao/100;
    }
    
    public void limpa() {
        texto = new ArrayList<String>();
        sentencas = new ArrayList<Sentenca>();
        palavras = new ArrayList<Palavra>();
        totalPalavras = 0;
        compressao = 0;
    }

    public void zeraValor() {
        for(Sentenca s:sentencas) {
            s.setValor(0);
        }
    }
    
}
