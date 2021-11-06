/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package identificacao;

import java.util.ArrayList;
import modelos.Palavra;

/**
 *
 * @author Marcelo
 */
public class NovoLunh {
    public void elementos(ArrayList<Palavra> lista, int total) {
        // int meio=(lista.size()-1)/2;
        int meio = total/lista.size();
        int p;
      //  System.out.println("-------------- "+meio+ " --- "+total+" --- "+lista.size());
        lista = modelos.Biblioteca.ClassificaPorFrequencia(lista);
        for(p=0;meio<lista.get(p).frequencia();++p)  ;
        meio = p;
        for(int j=meio;j>0 && j>meio-25;--j) lista.get(j).setValida(true);
        for(int j=meio+1;j<lista.size() && j<meio+25;++j) lista.get(j).setValida(true);
    }
}
