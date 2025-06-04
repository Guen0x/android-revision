package fr.einfolearning.imc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import fr.einfolearning.imc.exceptions.IncorrectDataException;
import parcelable_imc.FicheIMC;

/**
 * Saisie des informations de l'uitlisateur
 * Affichage de l'historique des informations et des IMCs calculés
 *
 * @author B. LEMAIRE
 * @version 2023
 */
public class MainActivity extends Activity {

	public static final String FICHE_IMC = "fiche_imc"; /* clef pour l'envoie de la fiche IMC en
	                                                       tant qu'extra d'un intent */

	private static final int REQ_IMC = 1;				/* Code de requête pour le résultat de
														   retour d'une activité */

	// Champs de saisis des informations
	private	EditText ed_nom = null;
	private	EditText ed_prenom = null;
	private	EditText ed_poids = null;
	private	EditText ed_taille = null;

	// Permet de saisir la date de naissance
	private	DatePicker date_picker = null;

	// Groupe radio pour saisir l'unité de la taille (mètre ou centimètre)
	private	RadioGroup group = null;


	// Zone d'affichage de l'IMC
	private TextView tv_imc;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Désérialisation des ressources
		this.deserialiserRessource();


		// Mise en place des écouteurs
		this.initConnections();
	}

	private void deserialiserRessource() {

		this.ed_nom = (EditText) findViewById(R.id.nom);
		this.ed_poids = (EditText) findViewById(R.id.poids);
		this.group = (RadioGroup) findViewById(R.id.group);
		this.date_picker = (DatePicker) this.findViewById(R.id.date_picker);
		this.ed_prenom = findViewById(R.id.prenom);
		this.ed_taille = findViewById(R.id.taille);
		this.tv_imc = findViewById(R.id.tv_imc);


		// A compléter FAIT

	}

	private void initConnections() {

		// Ecouteur pour le bouton calculer
		this.findViewById(R.id.calcul).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						try {
							String nom = ed_nom.getText().toString().trim();
							String prenom = ed_prenom.getText().toString().trim();
							String date = getDateFromDatePicker();
							float poids = getAndConvertPoids();
							float taille = getAndConvertTailleInCm();

							FicheIMC fiche = new FicheIMC(nom, prenom, date, poids, taille);

							startActivityCalculIMC(fiche);
							// A compléter

						}
						catch(IncorrectDataException e){
							Toast.makeText(MainActivity.this, R.string.input_problem, Toast.LENGTH_SHORT).show();
						}
					}
				});

		// Listener du bouton de remise à zéro

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {


		// A compléter
	   super.onActivityResult(requestCode, resultCode, data);
	}






	// Converti le poids de la TextView en float
	private float getAndConvertPoids() throws IncorrectDataException {
		String sPoids = ed_poids.getText().toString().trim();
		return convertStringToFloat(sPoids);
	}


	// Converti la taille de la TextView  en centimètres
	// Tiens compte du choix des boutons radios.
	private float getAndConvertTailleInCm() throws IncorrectDataException {
		String sTaille = ed_taille.getText().toString().trim();
		float taille = convertStringToFloat(sTaille);
		if (!checkIfTailleInCm()) {
			taille *= 100.0f;
		}
		return taille;
	}

	/**
	 * Vérifie si le boutons radio cm est activé
	 * @return (boolean) true : cm choisi, false sinon
	 */
	private boolean checkIfTailleInCm() {
		return group.getCheckedRadioButtonId() == R.id.radio2;
	}

	// Converti une chaine représentant un float en un nombre float
	public float convertStringToFloat(String stringToConvert) throws IncorrectDataException {
		try {
			return Float.parseFloat(stringToConvert);
		} catch (NumberFormatException e) {
			throw new IncorrectDataException("Erreur de conversion float");
		}
	}




	/** Retourn la date de naissance saisie dans le datepicker
	 * sous la forme d'une chaine au formatdd/mm/yy
	 * @return (String)
	 */
public String getDateFromDatePicker() {

	int d = MainActivity.this.date_picker.getDayOfMonth();
	int m = MainActivity.this.date_picker.getMonth() + 1;
	int y = MainActivity.this.date_picker.getYear();

	return d + "/" + m + "/" + y;
}


    // Lance l'activité CalculIMC avec une ficheIMC dans les extras
	private void startActivityCalculIMC(FicheIMC ficheIMC) {
		Intent intent = new Intent(MainActivity.this, CalculIMC.class);
		intent.putExtra(FICHE_IMC, ficheIMC);
		startActivityForResult(intent, REQ_IMC);
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onRestoreInstanceState(outState);
}


	@Override
	protected void onRestoreInstanceState(Bundle saveInstanceState){
		super.onRestoreInstanceState(saveInstanceState);

	}
}