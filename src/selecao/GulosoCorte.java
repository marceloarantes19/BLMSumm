/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package selecao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import modelos.Sentenca;

/**
 *
 * @author Marcelo
 */
public class GulosoCorte {
    public ArrayList<Sentenca> gera(ArrayList<Sentenca> sentencas, int qtdPalavras) {
        ArrayList<Sentenca> s = new ArrayList<Sentenca>();
        ArrayList<Sentenca> s2 = new ArrayList<Sentenca>();
        ArrayList<String> stb = new ArrayList<String>();
        int total=0;
        s = (ArrayList<Sentenca>)sentencas.clone();
        Classifica(s);
        do {
            if(s.get(total).getNumeroPalavras()>qtdPalavras) s.remove(total) ;
            else ++total;
        } while(total<s.size());
        total = 0;
        for(int i=0; i<s.size(); ++i) {
            int calculaX = total+s.get(i).getPalavras().size();
            if(calculaX<=qtdPalavras) {
                s2.add(s.get(i));
                total=calculaX;
            } else { break; }
        }
        return s2;
    }
    private ArrayList<Sentenca> Classifica(ArrayList<Sentenca>trab) {
        Collections.sort(trab, new Comparator(){
            public int compare(Object o1, Object o2) {
                Sentenca c1 = (Sentenca) o1;
                Sentenca c2 = (Sentenca) o2;
                return c1.getValor() < c2.getValor() ? +1 : (c1.getValor() > c2.getValor() ? -1 : 0);
             }
        });
        return trab;
    }
}