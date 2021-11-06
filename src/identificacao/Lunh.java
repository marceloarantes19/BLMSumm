/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package identificacao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import modelos.Palavra;

/**
 *
 * @author Marcelo
 */
public class Lunh {
    public void percentual(int total, ArrayList<Palavra> lista, float perc) {
         List <Palavra> trb=new ArrayList<Palavra>();
    }
    public void elementos(ArrayList<Palavra> lista, int sup, int inf) {
         int total = lista.size();
         int inicio = total*sup/100;
         int qtd = total * inf/100;
         lista = Classifica(lista);
         for(int j=inicio;j<inicio+qtd;++j) lista.get(j).setValida(true);
    }

    private ArrayList<Palavra> Classifica(ArrayList<Palavra>trab) {
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
}
