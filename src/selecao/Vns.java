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
class Vns {
    public ArrayList<Sentenca> gera(Texto t, Sentenca s){
        ArrayList<Sentenca> ns = new ArrayList<Sentenca>();
        return ns;
    }

   public static ArrayList<Sentenca> gera(int [][] grafo, int maxP, ArrayList<Sentenca> s,
                                                                       ArrayList <Sentenca> st, ArrayList<Boolean> esc)
   {
        ArrayList<Sentenca> sl = new ArrayList<Sentenca>();
        ArrayList<Sentenca> sdl = new ArrayList<Sentenca>();
        Random rnd = new Random();
        int N = st.indexOf(s.get(rnd.nextInt(s.size())));
        float cs=0, csl=0, csdl=0;
        for(Sentenca ss:s) sl.add(ss);
        cs = Biblioteca.calculaValor(s);
        csl = cs;
        int i=0;
        while (i < s.size()*10/100) {
              int x = rnd.nextInt(grafo[N].length);
              while(grafo[N][x]==0) {
                  x = rnd.nextInt(grafo[N].length);
              }
        }
        return s;
   }
}