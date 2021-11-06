/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visoes;

import java.util.Comparator;

/**
 *
 * @author marcelo
 */
public class TC {
    private String palavra;
    private int quantidade;
    private boolean sw;
    public TC(String pala, int quan, boolean s) {
        palavra = pala;
        quantidade = quan;
        sw = s;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isSw() {
        return sw;
    }

    public void setSw(boolean sw) {
        this.sw = sw;
    }
    
}

