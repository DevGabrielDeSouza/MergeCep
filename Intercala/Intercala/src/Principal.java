import java.io.*;
import java.util.*;

public class Principal {
	
	public static String inputString() {
		Scanner input = new Scanner(System.in);
		
		String texto = input.nextLine();
		
		input.close();
		
		return texto;
	}
	
	public static void teste1(Endereco e) 
	  throws IOException {
		OutputStream saida = new FileOutputStream("teste1.dat");
		DataOutputStream dout = new DataOutputStream(saida);
		
		e.escreveEndereco(dout);
		
        dout.close();
        saida.close();
		
	}
	
	public static void escreveEndereco(int pos, RandomAccessFile file, DataOutput dout) 
	  throws IOException {
		Endereco e = new Endereco();

		file.seek((pos)*300);
		
		e.leEndereco(file);
		
		e.escreveEndereco(dout);
	}
	
	private static void escreveSubArquivos(RandomAccessFile fonte, Arquivo a1, Arquivo a2) throws IOException {
		
		long size = fonte.length()/300;
		
		System.out.println("Tamanho: " + size);
        
        Random aleatorio = new Random();
        
        Endereco e = new Endereco();
        
        //System.out.println(gerador.nextInt(2));
		
		for(long i = 0; i < size; i++) {
			
			fonte.seek((i) * 300);
			
			e.leEndereco(fonte);
			
			//System.out.println("Cep: " + e.getCep());
			
			if(aleatorio.nextInt(2) < 1) {
				a1.escreverEndereco(e);
			}else {
				a2.escreverEndereco(e);
			}
		}
		
		fonte.seek(0);
	}
	
	private static void mesclaOrdenado(RandomAccessFile a1, RandomAccessFile a2, Arquivo mescla)  throws IOException {
		
		Random gerador = new Random();
		
		long size = a1.length()/300 + a2.length()/300;

		Endereco e1 = new Endereco();
		Endereco e2 = new Endereco();

		long i1 = 0;
		long i2 = 0;

		a1.seek(i1 * 300);
		a1.seek(i2 * 300);

		e1.leEndereco(a1);
		e2.leEndereco(a2);
		
		for(long i = 0; i < size; i++) {
			
			long c1 = Long.parseLong(e1.getCep());
			long c2 = Long.parseLong(e2.getCep());
			
			if(c1 < c2) {
				mescla.escreverEndereco(e1);
				
				if(i < size - 1) {
					a1.seek((i1) * 300);
					e1.leEndereco(a1);
					i1++;
					System.out.println("1: " + i1 + " || size: " + size);
				}else {

					System.out.println("1: " + i1 + " || size: " + size);
				}
				
			}else {
				mescla.escreverEndereco(e2);

				if(i < size - 1) {
					a2.seek((i2) * 300);
					e2.leEndereco(a2);
					i2++;
					System.out.println("2: " + i2 + " || size: " + size);
				}else {

					System.out.println("2: " + i2 + " || size: " + size);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception 
	{
		//pesquisa e armazena o arquivo que será verificado
		//RandomAccessFile f = new RandomAccessFile("cep_ordenado.dat", "r");
		RandomAccessFile fonte = new RandomAccessFile("cep_ordenado.dat", "r");
		
		//cria uma nova estrutura de endereço
		Endereco e = new Endereco();
        
        Arquivo a1 = null; 
        
        Arquivo a2 = null; 
		 
        try
        {
            a1 = new Arquivo("arquivo1.dat"); 
            
            a2 = new Arquivo("arquivo2.dat"); 
            
            escreveSubArquivos(fonte, a1, a2);
            
    		//RandomAccessFile teste = new RandomAccessFile("aaa.dat", "r");
        }
        catch(IOException ex)
        {
            //ex.printStackTrace();
        	System.out.println("Não foi possível criar o arquivo.\nVerifique o diretório e os arquivos, em seguida, tente novamente.\n");
        }
        
        a1.encerrar();
        a2.encerrar();
		
		//fecha o arquivo
		fonte.close();
		
		//Acessando sub-arquivos ordenados anteriormente
		RandomAccessFile sub1 = new RandomAccessFile("arquivo1.dat", "r");
		RandomAccessFile sub2 = new RandomAccessFile("arquivo2.dat", "r");
		
		//reinicia estrutura de endereço
		e = new Endereco();
        
		//Arquivo ordenado final
        Arquivo ordenado = null;
		 
        try
        {
        	ordenado = new Arquivo("ordenado.dat"); 
            
            mesclaOrdenado(sub1, sub2, ordenado);
            
    		//RandomAccessFile teste = new RandomAccessFile("aaa.dat", "r");
        }
        catch(IOException ex)
        {
            //ex.printStackTrace();
        	System.out.println("Parte 2: Não foi possível criar o arquivo.\nVerifique o diretório e os arquivos, em seguida, tente novamente.\n");
        }
        
        
	}

}
