import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.*;

import org.blinkenlights.jid3.ID3Exception;
import org.blinkenlights.jid3.MP3File;
import org.blinkenlights.jid3.MediaFile;
//import org.blinkenlights.jid3.v1.ID3V1Tag.Genre;
import org.blinkenlights.jid3.v1.ID3V1_0Tag;
import org.blinkenlights.jid3.v2.ID3V2_3_0Tag;

public class Cancion {
	//atributos
	private String nombre,genero,direccion;
	//constructor
	public Cancion(String nom,/*String gen,*/String dire){
		this.nombre=nom;
		this.genero="Desconocido";
		this.direccion=dire;
	}
	
	//getters
	public String getNombre(){	return this.nombre;	}
	public String getDireccion(){	return this.direccion;	}
	public String getGenero(){	return this.genero;	}
	
	//setters
	public void setNombre(String nom){	this.nombre=nom;	}
	public void setDirecion(String dire){	this.direccion=dire;	}
	public void setGenero(String gen){ this.genero=gen; }
	
	//reproduccion
	public void reproducir_una() throws JavaLayerException, FileNotFoundException{
		FileInputStream direccion;
		direccion = new FileInputStream(this.direccion);// con esto le da la direccion absoluta del archivo que se seleccion
		//direccion, es un fichero;
		BufferedInputStream bis = new BufferedInputStream(direccion); //dar velocidad
		Player player = new Player(bis);
		System.out.printf("	¡Reproduciendo!...");	System.out.println("	"+this.nombre);
		player.play();
		System.out.println("	Reproduccion terminada!");
	}
	
	//Metadatos
	public void Leer_Meta() throws ID3Exception{
		
		MediaFile mediaFile = new MP3File(new File(this.direccion));
		try{
			for(Object obj: mediaFile.getTags()){
				if(obj instanceof ID3V1_0Tag){
					this.genero=Genero_can(obj);
				}else{
					if(obj instanceof ID3V2_3_0Tag){
							this.genero=Genero_can2(obj);
						}
				}
			}
		}catch(ID3Exception e){
			e.printStackTrace();
		}
		
	}
	public String Genero_can(Object obj) {
		ID3V1_0Tag oID3V1_0Tag = (ID3V1_0Tag) obj;
		String genero=null;
		if(oID3V1_0Tag.getGenre().toString()!=null){
			if((oID3V1_0Tag.getGenre().toString().equals("Unknown")) || (oID3V1_0Tag.getGenre().toString().equals("")))
				genero="Desconocido";
			else	genero=oID3V1_0Tag.getGenre().toString();//no se que onda
		}else genero="Desconocido";
		return genero;
	}
	public String Genero_can2(Object obj) {
		ID3V2_3_0Tag oID3V2_3_0Tag = (ID3V2_3_0Tag) obj;
		String genero=null;
		if(oID3V2_3_0Tag.getGenre()!=null){
			if((oID3V2_3_0Tag.getGenre().equals("Unknown")) || (oID3V2_3_0Tag.getGenre().toString().equals("")))
				genero="Desconocido";
			else
				genero=oID3V2_3_0Tag.getGenre();
		}else genero="Desconocido";
		return genero;
	}
	public String titulo_can(Object obj) {
		ID3V1_0Tag oID3V1_0Tag = (ID3V1_0Tag) obj;
		String titulo=null;
		if(oID3V1_0Tag.getTitle()!=null){
			titulo=oID3V1_0Tag.getTitle();
			System.out.println(titulo);
		}else titulo="Desconocido";
		return titulo;
	}
	public String titulo_can2(Object obj) {
		ID3V2_3_0Tag oID3V2_3_0Tag = (ID3V2_3_0Tag) obj;
		String titulo=null;
		if(oID3V2_3_0Tag.getTitle()!=null){
			titulo=oID3V2_3_0Tag.getTitle();
			System.out.println(titulo);
		}else titulo="Desconocido";
		return titulo;
	}
	
	//Muestra de una cancion
	public void mostrar_cancion(){
		System.out.println("	Nombre: "+getNombre());
		System.out.println("	Direccion: "+getDireccion());
		System.out.println("	Genero: "+getGenero());
	}
}
