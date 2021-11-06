package selecao;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import modelos.Sentenca;
import modelos.Texto;
public class AleatorioM {
    public ArrayList<Sentenca> gera(Texto t, Random nRandom) {
        BigInteger sum = gera(t.getSentencas(), t.getQtdMaxPalavras(), nRandom);
        ArrayList<Sentenca> s = new ArrayList<Sentenca>();
        for(int i=1;i<=sum.bitCount();++i) {
            if(sum.testBit(i)) {
                s.add(t.getSentencas().get(i));
            }
        }
        return s;
    }
    public BigInteger gera(List<Sentenca> sentenca, int TotalPalavras, Random nRandom) {
        BigInteger ret=new BigInteger(sentenca.size(),nRandom);
        int i=0;
        while(!sumarioOk(ret, sentenca, TotalPalavras)) {
            ++i;
            ret = new BigInteger(sentenca.size(),nRandom);
            if(i>50000) {
                ret = BigInteger.ONE;
                break;
            }
        }
        return ret;
    }
    public boolean sumarioOk(BigInteger summ, List<Sentenca> sentencas, int TotalPalavras) {
        int qtd=0;
        for(int i=0;i<sentencas.size();++i) {
            Sentenca s = sentencas.get(i);
            if(summ.testBit(i)) {
                qtd+=s.getPalavras().size();
            }
        }
        return qtd<=TotalPalavras;
    }
}