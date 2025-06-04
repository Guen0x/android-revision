package parcelable_imc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Cette classe permet à une FicheIMC d'être transportée en tant qu'extra
 * d'une intention

 * @author B. LEMAIRE
 *
 * Ne pas modifier cette classe
 *
 * Exemple d'utilisation :
 *
 * Activité source :
 * FicheIMC ficheIMC = new FicheIMC(.......)
 * Intent intent = new Intent(MainActivity.this, ActivityB.class);
 * intent.putExtra("fiche_imc", ficheIMC);
 *
 *
 * Activité B
 * Intent intent = this.getIntent();
 *
 *	FicheIMC ficheIMC = intent.getParcelableExtra("fiche_imc");
 */

public class FicheIMC implements Parcelable {
	private String nom;
	private String prenom;
	private String dateNaissance;
	private float taille;
	private float poids;
	
	private float imc;


	public FicheIMC(){};
	
	 //Second constructeur qui sera appelé lors de la "Deparcelablisation"
	public FicheIMC(Parcel in)
    {
        this.getFromParcel(in);
    }
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public FicheIMC createFromParcel(Parcel in)
        {
            return new FicheIMC(in);
        }
 
        @Override
        public Object[] newArray(int size) {
            return null;
        }
    };
	
    public int describeContents() {
        return 0;
    }
    
    

    //On ecrit dans le parcel les données de notre objet
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.nom);
        dest.writeString(this.prenom);
        dest.writeString(this.dateNaissance);
        dest.writeFloat(this.taille);
        dest.writeFloat(this.poids);
        dest.writeFloat(this.imc);
    }
 
    //On va ici hydrater notre objet à partir du Parcel
    public void getFromParcel(Parcel in)
    {
        this.nom = new String(in.readString());
        this.prenom = new String(in.readString());
        this.dateNaissance = new String(in.readString());
        this.taille = in.readFloat();
        this.poids = in.readFloat();
        this.imc = in.readFloat();
    }

    
	
	public FicheIMC(String nom, String prenom,
			String dateNaissance, float taille, float poids ) {

		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.taille = taille;
		this.poids = poids;
	}


	public String getNom() {
		return nom;
	}

	
	public String getDateNaissance() {
		return dateNaissance;
	}


	public float getTaille() {
		return taille;
	}


	public float getPoids() {
		return poids;
	}


	public float getIMC() {
		return imc;
	}

	public void setIMC(float imc) {
		this.imc = imc;
	}
	
	public String toString(){
    	String s = 	"FicheIMC: \nnom :" +this.nom
				+ "\nprenom :" +  this.prenom
				+ "\nprenom :" +  this.dateNaissance
		+ "\ntaille :" +  this.taille
		+ "\npoids :" +  this.poids;
    	return s;
	}

	public String getPrenom() {
		return prenom;
	}
}

