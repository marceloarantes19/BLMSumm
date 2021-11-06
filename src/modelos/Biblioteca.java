/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Marcelo
 */
public class Biblioteca {
    static public ArrayList<Palavra> ClassificaPorFrequencia(ArrayList<Palavra>trab) {
        Collections.sort(trab, new Comparator(){
            public int compare(Object o1, Object o2) {
                Palavra c1 = (Palavra) o1;
                Palavra c2 = (Palavra) o2;
                return c1.getSentencas().size() < c2.getSentencas().size() ? +1 :
                        (c1.getSentencas().size() > c2.getSentencas().size() ? -1 : 0);
             }
        });
        return trab;
    }
    static public ArrayList<Palavra> ClassificaPorValor(ArrayList<Palavra>trab) {
        Collections.sort(trab, new Comparator(){
            public int compare(Object o1, Object o2) {
                Palavra c1 = (Palavra) o1;
                Palavra c2 = (Palavra) o2;
                return c1.getValor() < c2.getValor() ? +1 :
                      (c1.getValor() > c2.getValor() ? -1 : 0);
             }
        });
        return trab;
    }

    public static int[][] geraGrafo(Texto t, int op) {
        int trab[][] = new int[t.getSentencas().size()][t.getSentencas().size()];
        for(int i=0;i<t.getSentencas().size();++i)
           for(int j=0;j<t.getSentencas().size();++j) trab[i][j] = 0;
        for(Sentenca s:t.getSentencas()) {
            for(Palavra p:s.getPalavras()) {
                if(p.isValida()) {
                    for(int i:p.getSentencas()) {
                        if(op==1 && i!=s.getNum()) {
                            trab[s.getNum()][i]=1;
                            trab[i][s.getNum()]=1;
                        } else if(op==2 && i!=s.getNum()) {
                            trab[s.getNum()][i]++;
                            trab[i][s.getNum()]++;
                        } else if(op==3 && i!=s.getNum()) {
                            if(s.getNum()>i) { trab[s.getNum()][i]++; }
                            else { trab[i][s.getNum()]++; }
                        }

                    }
                }
            }
        }
        return trab;
    }

    public static int calculaGrau(int grafo[][], int i, int tam) {
        int x=0;
        for(int j=0;j<tam; ++j) {
           x = x + grafo[i][j];
        }
        return x;
    }

    public static int qtdPalavras(ArrayList<Sentenca> sa) {
        int tot=0;
        for(Sentenca s:sa) tot+=s.getPalavras().size();
        return tot;
    }

    public static boolean selecionada(Sentenca sa, ArrayList<Sentenca> st, ArrayList<Boolean> esc) {
        return esc.get(st.indexOf(sa));
    }

    public static float calculaValor(ArrayList<Sentenca> sa) {
        float tot=0;
        for(Sentenca s:sa) tot+=s.getValor();
        return tot;
    }


}
