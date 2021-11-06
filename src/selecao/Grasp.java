/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package selecao;

import java.util.ArrayList;
import java.util.Random;
import modelos.Biblioteca;
import modelos.Sentenca;
import modelos.Texto;

/**
 *
 * @author Marcelo
 */
public class Grasp {
    public ArrayList<Sentenca> gera(Texto t, Random nRandom) {
        ArrayList <Sentenca> s = new ArrayList<Sentenca>();
        ArrayList <Sentenca> sa = new ArrayList<Sentenca>();
        ArrayList <Sentenca> st = new ArrayList<Sentenca>();
        ArrayList<Boolean> escolhida = new ArrayList<Boolean>();
        int [][] grafo = Biblioteca.geraGrafo(t,1);
        boolean continua=true;
        for(Sentenca sent: t.getSentencas()) {
            st.add(sent);
            escolhida.add(false);
        }
        float vm = 0, va=0, i=0;
        do {
              ++i;
              if(va>vm) {
                  s = sa;
                  vm = va;
              }
              sa=construtivo(0.4f, t.getQtdMaxPalavras(), s, st, escolhida, continua, nRandom);
              if (continua) {
                    sa =SubidaDeEncosta.gera(grafo, t.getQtdMaxPalavras(), sa, st, escolhida);
                    va = Biblioteca.calculaValor(sa);
              }
        } while((va>vm || i<2000) && continua);
        if(va>vm) {
           s = sa;
           vm = va;
       }
       return s;
    }
    private ArrayList<Sentenca> construtivo(float alfa, int pRest, ArrayList<Sentenca> ss, ArrayList<Sentenca> st,
                                                                                                ArrayList<Boolean> esc, boolean continua, Random rnd) {
        ArrayList<Sentenca> s = new ArrayList<Sentenca>();
        ArrayList<Sentenca> RCL = new ArrayList<Sentenca>();
        float qtd = alfa*st.size();
        int i=0;
        for(i=0;i<(int)qtd;++i) {
             float mv = 0;
             Sentenca ms = new Sentenca();
             for(Sentenca sa:st) {
                 if(sa.getValor()>mv && !RCL.contains(sa) && (sa.getPalavras().size() + Biblioteca.qtdPalavras(ss))<=pRest && !Biblioteca.selecionada(sa, st, esc)) {
                     ms = sa;
                     mv = sa.getValor();
                 }
             }
             if(mv>0) {
                 RCL.add(ms);
             } else {
                 break;
             }
        }
        if(!RCL.isEmpty()) {
            for(i=0;i<esc.size();++i) {
                if(esc.get(i))
                    s.add(st.get(i));
            }
            int x = rnd.nextInt(RCL.size());
            s.add(RCL.get(x));
            esc.set(st.indexOf(RCL.get(x)), true);
        } else {
            continua = false;
        }
        return s;
    }
}