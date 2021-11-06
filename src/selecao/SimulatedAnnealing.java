/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package selecao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import modelos.Sentenca;
import modelos.Texto;

/**
 *
 * @author Marcelo
 */
public class SimulatedAnnealing {
    public ArrayList<Sentenca> gera(Texto t, Random nRandom) {
        BigInteger x;
        ArrayList<Sentenca> s = new ArrayList<Sentenca>();
        ArrayList<Sentenca> sumario = new ArrayList<Sentenca>();
        s = (ArrayList<Sentenca>) t.getSentencas().clone();
        x = gera(s, t.getQtdMaxPalavras(),10000, nRandom);
        for(int i=0;i<t.getSentencas().size();++i) {
            if(x.testBit(i)) sumario.add(t.getSentencas().get(i));
        }
        return sumario;
    }

    public BigInteger gera(ArrayList<Sentenca> sentencas, int qtdPalavras, int tamAgenda, Random nRandom) {
        ArrayList<Float> t;
        BigInteger corrente, proximo, melhor;
        float E, Ep, deltaE,T, Em;
        float k = (float) 1.3806503;
        int nBits = sentencas.size();
        t = geraAgenda(tamAgenda);
        corrente = BigInteger.ONE;
        melhor = corrente;
         Em = energia(melhor, sentencas);
         E = Em;
        for(;;) {
            T=t.remove(0);
            if(T==0) break;
            proximo = geraProximoA(nBits, sentencas, qtdPalavras, nRandom);
            if(proximo.toString().equals("-1")) continue;
            Ep = energia(proximo, sentencas);
            deltaE = Ep - E;
            if(deltaE>0) {
                corrente = proximo;
                E = Ep;
                if(E>Em) {
                    melhor = corrente;
                    Em = E;
                }
            }
            else
               if ( Math.pow(Math.E, deltaE/(k*T))>Math.random()) corrente = proximo;
         //   else if()
        }
        return melhor;
    }
    private BigInteger geraProximo(int nBits, ArrayList<Sentenca> sentencas, int  qtdPalavras, Random nRandom) {
        BigInteger ret;
        int qtd;
        do {
            ret = new BigInteger(nBits, nRandom);
            qtd=0;
            for(int i=0;i<sentencas.size();++i) {
                Sentenca s = sentencas.get(i);
                if(ret.testBit(i)) {
                    qtd+=s.getPalavras().size();
                }
           }
        } while(qtd>qtdPalavras);
        return ret;
    }
    private BigInteger geraProximoA(int nBits, ArrayList<Sentenca> sentencas, int  qtdPalavras, Random nRandom) {
        BigInteger ret;
        int qtd;
        ret = new BigInteger(nBits, nRandom);
        qtd=0;
        for(int i=0;i<sentencas.size();++i) {
            Sentenca s = sentencas.get(i);
            if(ret.testBit(i)) {
                qtd+=s.getPalavras().size();
            }
        }
        if(qtd>qtdPalavras) ret = new BigInteger("-1");
        return ret;
    }
    private float energia(BigInteger x, ArrayList<Sentenca> sentencas) {
        float valor = 0;
        for(int i=0;i<sentencas.size();++i) {
            Sentenca s = sentencas.get(i);
            if(x.testBit(i)) {
                valor+=s.getValor();
            }
        }
        return valor;
    }

    private ArrayList<Float> geraAgenda(int tamAgenda) {
        ArrayList<Float> ret = new ArrayList<Float>();
        for(float i=0;i<373;i=i+(float)373/tamAgenda) ret.add(0, i);
        return ret;
    }
}
