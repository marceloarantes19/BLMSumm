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
public class Palavra {
    private String palavra;
    private boolean valida;
    private List<Integer> sentencas = new ArrayList<Integer>();
    private List<Integer> posicoes = new ArrayList<Integer>();
    private int quantidadeSentencas=0;
    private double valor;

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQuantidadeSentencas() {
        return quantidadeSentencas;
    }

    public void setQuantidadeSentencas(int quantidadeSentencas) {
        this.quantidadeSentencas = quantidadeSentencas;
    }

    public void adcQtdSentencas(int num) {
        boolean possui = false;
        for(Integer i:sentencas) {
            if(i==num) {
                possui=true;
                break;
            }
        }
        if(!possui) this.quantidadeSentencas++;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public List<Integer> getPosicoes() {
        return posicoes;
    }

    public void setPosicoes(List<Integer> posicoes) {
        this.posicoes = posicoes;
    }

    public List<Integer> getSentencas() {
        return sentencas;
    }

    public void setSentencas(List<Integer> sentencas) {
        this.sentencas = sentencas;
    }

    public boolean isValida() {
        return valida;
    }

    public void setValida(boolean valida) {
        this.valida = valida;
    }

    public void setValida(List<String> lista) {
        valida = true;
        for(String str:lista) {
            if(this.palavra.equals(str)) {
                valida = false;
                break;
            }
        }
    }
    public int frequencia() {
        return this.sentencas.size();
    }
    public void atualizaValorSentencas(List<Sentenca> sents) {
        for(Integer i:this.sentencas) {
            Sentenca s = sents.get(i);
            s.addValor();
        }
    }
    public void addSentenca(int val) {
        this.sentencas.add(val);
    }
    public int qtdNaSentenca(int sent) {
        int v=0;
        for(Integer i: sentencas)
            v = v + i == sent?1:0;
        return v;
    }
}
