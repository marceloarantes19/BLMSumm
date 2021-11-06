/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelos;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Marcelo
 */
public class Cromossomo {
    private BigInteger cromossomo;
    private float valor;
    public BigInteger getCromossomo() {
        return cromossomo;
    }
    public void setCromossomo(BigInteger cromossomo) {
        this.cromossomo = cromossomo;
    }
    public float getValor() {
        return valor;
    }
    public void setValor(float valor) {
        this.valor = valor;
    }
    public int calculaValor(List <Sentenca> sentencas) {
        int totalPalavras =0;
        valor = 0;
        for(int i=0;i<sentencas.size();++i) {
            Sentenca s = sentencas.get(i);
            if(this.cromossomo.testBit(i+1)) {
                totalPalavras+=s.getPalavras().size();
                valor+=s.getValor();
            }
        }
        return totalPalavras;
    }
    
    public int getQtdPalavras(List <Sentenca> sentencas) {
        int totalPalavras =0;
        for(int i=0;i<sentencas.size();++i) {
            Sentenca s = sentencas.get(i);
            if(this.cromossomo.testBit(i+1)) {
               totalPalavras+=s.getPalavras().size();
            }
        }
        return totalPalavras;
    }

    public void geraAleatorio(int n, Random nRandom) {
        cromossomo = new BigInteger(n, nRandom);
    }
    public boolean contido(ArrayList<Cromossomo> populacao) {
        boolean ret=false;
        for(Cromossomo c:populacao) {
            if(c.cromossomo.equals(this.cromossomo)) {
                ret = true;
                break;
            }
        }
        return ret;
    }
}