/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package selecao;

import java.util.ArrayList;
import modelos.Biblioteca;
import modelos.Sentenca;
import modelos.Texto;

/**
 *
 * @author Marcelo
 */
public class SubidaDeEncosta {
    public ArrayList<Sentenca> gera(Texto t) {
        ArrayList<Sentenca> s = new ArrayList<Sentenca>();
        ArrayList<Sentenca> st = new ArrayList<Sentenca>();
        st = (ArrayList<Sentenca>)t.getSentencas().clone();
        int [][] grafo = new int[t.getSentencas().size()][t.getSentencas().size()];
        return s;
    }
    public static ArrayList<Sentenca> gera(int [][] grafo, int maxP, ArrayList<Sentenca> s,
                                                                       ArrayList <Sentenca> st, ArrayList<Boolean> esc)
    {
         do {
             ArrayList<Sentenca> prox = new ArrayList<Sentenca>();
             int indMv = -1;
             float vMv = 0;
             for(int i=0;i<esc.size();++i) {
                 if(esc.get(i)) {
                    for(int j=0;j<grafo[i].length;++j) {
                        if(grafo[i][j]>0 && !esc.get(j) && (st.get(j).getPalavras().size() +Biblioteca.qtdPalavras(s))<=maxP) {
                            if(st.get(j).getValor()>vMv) {
                                indMv = j;
                                vMv = st.get(j).getValor();
                            }
                        }
                    }
                 }
             }
             if(indMv >=0) {
                 s.add(st.get(indMv));
                 esc.set(indMv, Boolean.TRUE);
             } else {
                break;
             }
         } while(true);
         return s;
    }

}
