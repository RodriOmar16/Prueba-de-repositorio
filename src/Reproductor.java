import javazoom.jl.decoder.JavaLayerException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.blinkenlights.jid3.ID3Exception;
import java.io.*;

public class Reproductor {
	
	private ArrayList <Cancion> Playlist;
	private ArrayList <Cancion> Genero;
	
	public Reproductor() throws FileNotFoundException, JavaLayerException, ID3Exception{
		Playlist = new ArrayList<Cancion>();
		Genero  = new ArrayList<Cancion>();
		System.out.println("	Cargar la lista de canciones:"); 	cargar_lis();
		
		int opcion,op;
		do{
			opcion=menu_principal();
			switch(opcion){
			case 1: op=Menu_selec();	Escuchar(op-1);		break;
			case 2: cargarGenero();	break;
			case 3: reproduceSecue(Playlist); break;
			case 4: Reproduce_aleatorio();	break;
			case 5: op=Menu_selec(); mostrar(op-1); break;
			}
			if(opcion!=0) System.out.printf("	Presionar enter para continuar.	");
		}while(opcion!=0);

	}

	public static void reproduceSecue(ArrayList<Cancion> l) throws FileNotFoundException, JavaLayerException {
		if(l.size()!=0){
			System.out.println("	Reproduciendo la lista.");
			for(Cancion obj: l)	obj.reproducir_una();
		}else System.out.println("	La lista se encuentra vacia.");
	}
	public void mostrar(int i) {	Playlist.get(i).mostrar_cancion();	}
	public void Reproduce_aleatorio() throws FileNotFoundException, JavaLayerException {
		
		int vind[] = new int[150]; int cantidad=Playlist.size(),i=0,j;
		
		ArrayList <Cancion> Aleatoria=new ArrayList <Cancion>();
		while(i<cantidad){
			vind[i]=(int)(Math.random() * (cantidad));
			j=0;
			while(j<i){
				if(vind[i]==vind[j]){
					i--;
				}j++;
			}i++;
		} 
		for(i=0;i<cantidad;i++)	Aleatoria.add(Playlist.get(vind[i]));	
		reproduceSecue(Aleatoria);
	}
	private short Busqueda(String gene){
		int i=0,n=Playlist.size();
		while(i<n && !Playlist.get(i).getGenero().equals(gene))	i++;
		if(i<n)	return 1;
		else 	return 0;
	}
	public void cargarGenero() throws FileNotFoundException, JavaLayerException{
		Scanner aiuda= new Scanner(System.in);	String gene=" ";
		if(aiuda!=null){	System.out.printf("	Presionar enter para continuar.	");	aiuda.nextLine();} 
		System.out.printf("	Ingresar el genero: ");		gene=aiuda.nextLine();
		if(Busqueda(gene)!=0){
			for(int i=0;i<Playlist.size();i++){
				if(Playlist.get(i).getGenero().equals(gene))	Genero.add(Playlist.get(i));
			}reproduceSecue(Genero);
		}else System.out.println("	No existen canciones con ese genero.");
	}
	public void Escuchar(int i) throws FileNotFoundException, JavaLayerException{
		Playlist.get(i).reproducir_una();
	}
	public int Menu_selec(){
		int opc,i; Scanner tecl=new Scanner(System.in);
		do{
			for(i=0;i<Playlist.size();i++)	System.out.println("	"+(i+1)+" - "+Playlist.get(i).getNombre());
			System.out.printf("	Ingresar el nro. de una cancion: "); opc=tecl.nextInt();
		}while(opc<1 || opc>Playlist.size());
		if(tecl != null){tecl.nextLine();	System.out.printf("	Presionar enter para continuar.	");}
		//tecl.close();
		return opc;
	}
	public int menu_principal(){
		int op;	Scanner tecla=new Scanner(System.in);
		do{
			if(tecla!=null)	tecla.nextLine();
			System.out.println("	1 - Seleccionar una cancion de la lista.");
			System.out.println("	2 - Reproducir canciones por genero.");
			System.out.println("	3 - Reproducir de manera secuencial.");
			System.out.println("	4 - Reproducir de manera aleatoria.");
			System.out.println("	5 - Mostrar los datos una cancion.");
			System.out.println("	0 - Salir.");
			System.out.printf("	Ingresar una opcion: ");	op=tecla.nextInt();
		}while(op<0 || op>5);
		if(tecla!=null)	tecla.nextLine();
		//tecla.close();
		return op;
	}
	public void cargar_pocas()throws ID3Exception {
		Scanner teclado = new Scanner(System.in);
		String b;
		do{
			System.out.println("	Selecionar una cancion para la lista.");
			
			//FileInputStream direccion;
			JFileChooser fileChooser = new JFileChooser();
			if(fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION){//si seleciona entra
				File file = fileChooser.getSelectedFile(); //recoge la informacion del archivo seleccionado y se la da a file
				Playlist.add(new Cancion(file.getName(),file.getAbsolutePath()));
				Playlist.get((Playlist.size())-1).Leer_Meta();
				System.out.println("	Cancion agregada a la lista de forma exitosa.");
			}else JOptionPane.showMessageDialog(null,"Ningun archivo mp3 fue seleccionado.");
			System.out.printf("	Desea ingresar otra cancion: "); b=teclado.nextLine();
		}while(b.equals("no")==false && b.equals("No")==false && b.equals("NO")==false);
	}
	public void cargar_muchas(){
		Scanner teclado = new Scanner(System.in);
		System.out.printf("	Ingresar la direccion de la carpeta (Las barras deben ser / ): ");	String Direccion = teclado.nextLine();
		File file= new File(Direccion);		String Vec[]=new String[100];
		Vec=file.list();
		for(int i=0;i<Vec.length;i++)	Playlist.add(new Cancion(Vec[i],Direccion+"/"+Vec[i]));
		//if(teclado!=null)	teclado.nextLine();	System.out.printf("	Presionar enter para continuar.	");
	}
	public void cargar_lis() throws ID3Exception{
		Scanner teclado = new Scanner(System.in);	int op;
		do{
			System.out.println("	1 - Cargar una carpeta completa.");
			System.out.println("	2 - Cargar las canciones de la lista una por una.");
			System.out.printf("	Por favor, ingresar una cancion: ");	op=teclado.nextInt();
		}while(op<1 || op>2);
		switch(op){
			case 1: cargar_muchas();	break;
			case 2: cargar_pocas();		break;
		}
		if(teclado!=null)	teclado.nextLine();	System.out.printf("	Presionar enter para continuar.	");
	}
}