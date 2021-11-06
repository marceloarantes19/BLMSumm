package selecao;

import modelos.Cromossomo;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import modelos.Sentenca;
import modelos.Texto;

/**
 *
 * @author Marcelo
 */
public class Genetico {
    private int tamanhoDaPopulacao=200;
    private ArrayList<Cromossomo> populacao = new ArrayList<Cromossomo>();
    private float fitness=0;
    private float fitnessPrx=0;
    private double percentualDeMutacao=3;

    public double getPercentualDeMutacao() {
        return percentualDeMutacao;
    }

    public void setPercentualDeMutacao(double percentualDeMutacao) {
        this.percentualDeMutacao = percentualDeMutacao;
    }

    public int getTamanhoDaPopulacao() {
        return tamanhoDaPopulacao;
    }

    public void setTamanhoDaPopulacao(int tamanhoDaPopulacao) {
        this.tamanhoDaPopulacao = tamanhoDaPopulacao;
    }
    
    public ArrayList<Sentenca> gera(Texto t, Random nRandom) {
        int geracao = 0;
        ArrayList<Sentenca> sentencas = new ArrayList<Sentenca>();
        sentencas = t.getSentencas();
        populacaoInicial(sentencas, t.getQtdMaxPalavras(), nRandom);
        tamanhoDaPopulacao = populacao.size();
        fitnessPrx = calculaFitness(populacao);
        while(fitnessPrx>fitness) {
            ++geracao;
            ArrayList<Cromossomo> proximo;
            fitness = fitnessPrx;
            proximo=cruzamento(populacao, sentencas.size()-2);
            proximo=mutacao(proximo, sentencas.size());
            proximo=preSelecao(proximo, sentencas,t.getQtdMaxPalavras());
            proximo.addAll(populacao);
            proximo=Selecao(proximo);
            fitnessPrx = calculaFitness(proximo);
            if(fitnessPrx>fitness) populacao = proximo;
        }
        return melhorSentenca(populacao, t.getSentencas());
    }
    private void populacaoInicial(ArrayList<Sentenca> sentencas, int totalPalavras, Random nRandom) {
        int t=0, j=0;
        for(j=0;j<sentencas.size();++j) {
            Cromossomo c = new Cromossomo();
            Sentenca s = sentencas.get(j);
            c.setCromossomo(BigInteger.ZERO);
            if(s.getPalavras().size()<=totalPalavras) {
                c.setCromossomo(c.getCromossomo().setBit(j+1));
                populacao.add(c);
            }
        }
        for(int i=1;i<=tamanhoDaPopulacao && t<100000;++i) {
            Cromossomo c = new Cromossomo();
            int nAtualPalavras=0;
            do {
                c.geraAleatorio(sentencas.size(), nRandom);
                if(c.contido(populacao)) continue;
                nAtualPalavras = c.calculaValor(sentencas);
                ++t;
            } while ((nAtualPalavras > totalPalavras) && t<100000);
            if(c.getQtdPalavras(sentencas)<totalPalavras) {
                populacao.add(c);
            }
        }
    }
    private ArrayList<Cromossomo> cruzamento(ArrayList<Cromossomo> pop, int nbits) {
        ArrayList<Cromossomo> trab = new ArrayList<Cromossomo>();
        Random ponto = new Random();
        int corte = ponto.nextInt(nbits);
        ArrayList<Float> pesos = new ArrayList<Float>();
        float acumulo = 0;
        for(Cromossomo c: pop) {
             float vAtu = c.getValor()/fitness;
             acumulo = acumulo+vAtu;
             pesos.add(acumulo);
     //        System.out.println(acumulo);
        }
  //      System.out.println("Sai!!!------------------------------------------------------- "+fitness);
        
        for(int i=0;i<populacao.size()/2;i+=2) {
            Cromossomo c1 = new Cromossomo();
            Cromossomo c2 = new Cromossomo();
            BigInteger n1;
            BigInteger n2;
            Random aleat = new Random();
            float aleatorio = aleat.nextFloat();
            int ind1=0, ind2=0;
            for(Float ff:pesos) {
                 if(aleatorio<ff) {
                     ind1=pesos.indexOf(ff);
                     break;
                 }
            }
            for(Float ff:pesos) {
                 if(aleatorio<ff) {
                     ind2=pesos.indexOf(ff);
                     break;
                 }
            }

            n1 = populacao.get(ind1).getCromossomo();
            n2 = populacao.get(ind2).getCromossomo();

            String s1="",  s2="", s3="", s4="", s5="", s6 = "", s7 = "", s8 = "";
            s1 = n1.toString(2);
            s2 = n2.toString(2);
            for(int j=0;s1.length()<nbits+2;++j) s1="0"+s1;
            for(int j=0;s2.length()<nbits+2;++j) s2="0"+s2;
            s3 = s1.substring(0,corte);
            s4 = s1.substring(corte,nbits+2);
            s5 = s2.substring(0,corte);
            s6= s2.substring(corte,nbits+2);
            s7 = s3.concat(s6);
            s8 = s5.concat(s4);
            n1 = new BigInteger(s7,2);
            n2 = new BigInteger(s8,2);
            c1.setCromossomo(n1);
            c2.setCromossomo(n2);
            trab.add(c1);
            trab.add(c2);
        }
        return trab;
    }
    
    private ArrayList<Cromossomo> mutacao(ArrayList<Cromossomo> trab, int nbit) {
        Random rdn = new Random();
        int percentual = (int) (trab.size() * percentualDeMutacao / 100); 
        for(int i=0;i<trab.size();++i) {
            if(percentual<=rdn.nextDouble()*100 ) {
                int cromoAMutar = rdn.nextInt(trab.size());
                int muta = rdn.nextInt(nbit);
                BigInteger cromo = trab.get(cromoAMutar).getCromossomo();
                if(cromo.testBit(muta)) cromo.clearBit(muta); else cromo.setBit(muta);
                trab.get(cromoAMutar).setCromossomo(cromo);
            }
        }
        return trab;
    }
    private ArrayList<Cromossomo> preSelecao(ArrayList<Cromossomo> trab,
                                                                                                  ArrayList<Sentenca> sentencas, int totalPalavras) {
        ArrayList<Cromossomo> trabPreSelecao = new ArrayList<Cromossomo>();
        for(Cromossomo c:trab) {
            int total = c.calculaValor(sentencas);
            if(total<=totalPalavras) {
                trabPreSelecao.add(c);
            }
        }
        return trabPreSelecao;
    }
    private ArrayList<Cromossomo> Selecao(ArrayList<Cromossomo> trab) {
        ArrayList<Cromossomo> selecao = new ArrayList<Cromossomo>();
        selecao =  Classifica(trab);
        while(selecao.size()>tamanhoDaPopulacao) selecao.remove(tamanhoDaPopulacao);
        return selecao;
    }
    private ArrayList<Cromossomo> Classifica(ArrayList<Cromossomo>trab) {
        Collections.sort(trab, new Comparator(){
            public int compare(Object o1, Object o2) {
                Cromossomo c1 = (Cromossomo) o1;
                Cromossomo c2 = (Cromossomo) o2;
                return c1.getValor() < c2.getValor() ? +1 : (c1.getValor() > c2.getValor() ? -1 : 0);
             }
        });
        return trab;
    }

    private float calculaFitness(ArrayList<Cromossomo> populacao) {
        float fit =0;
        for(Cromossomo c : populacao) fit+=c.getValor();
        return fit;
    }

    private ArrayList<Sentenca> melhorSentenca(ArrayList<Cromossomo> populacao, ArrayList<Sentenca> sentencas) {
        ArrayList ret = new ArrayList<Sentenca>();
        int tpala = 0;
        Cromossomo x;
        if(populacao.size()>0) {
            x = populacao.get(0);
            for(int i=1;i<x.getCromossomo().bitLength();++i) {
                if(x.getCromossomo().testBit(i)) {
                    ret.add(sentencas.get(i-1));
                    tpala+=sentencas.get(i-1).getPalavras().size();
                }
            }
        }
        return ret;
    }
}