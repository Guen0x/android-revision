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

public class MainActivity extends Activity {

	public static final String FICHE_IMC = "fiche_imc";
	private static final int REQ_IMC = 1;

	private EditText ed_nom, ed_prenom, ed_poids, ed_taille;
	private DatePicker date_picker;
	private RadioGroup group;
	private TextView tv_imc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		deserialiserRessource();
		initConnections();
	}

	private void deserialiserRessource() {
		ed_nom = findViewById(R.id.nom);
		ed_prenom = findViewById(R.id.prenom);
		ed_poids = findViewById(R.id.poids);
		ed_taille = findViewById(R.id.taille);
		date_picker = findViewById(R.id.date_picker);
		group = findViewById(R.id.group);
		tv_imc = findViewById(R.id.tv_imc);
	}

	private void initConnections() {
		findViewById(R.id.calcul).setOnClickListener(new View.OnClickListener() {
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

				} catch (IncorrectDataException e) {
					Toast.makeText(MainActivity.this, R.string.input_problem, Toast.LENGTH_SHORT).show();
				}
			}
		});

		// Bonus : bouton de remise à zéro
		findViewById(R.id.reset).setOnClickListener(v -> {
			ed_nom.setText("");
			ed_prenom.setText("");
			ed_poids.setText("");
			ed_taille.setText("");
			tv_imc.setText("");
		});
	}

	private float getAndConvertPoids() throws IncorrectDataException {
		String sPoids = ed_poids.getText().toString().trim();
		return convertStringToFloat(sPoids);
	}

	private float getAndConvertTailleInCm() throws IncorrectDataException {
		String sTaille = ed_taille.getText().toString().trim();
		float taille = convertStringToFloat(sTaille);
		if (!checkIfTailleInCm()) {
			taille *= 100; // convertit en cm SI c'était en mètres
		}
		return taille;
	}

	private boolean checkIfTailleInCm() {
		return group.getCheckedRadioButtonId() == R.id.radio2;
	}

	public float convertStringToFloat(String stringToConvert) throws IncorrectDataException {
		try {
			return Float.parseFloat(stringToConvert);
		} catch (NumberFormatException e) {
			throw new IncorrectDataException("Conversion échouée");
		}
	}

	public String getDateFromDatePicker() {
		int d = date_picker.getDayOfMonth();
		int m = date_picker.getMonth() + 1;
		int y = date_picker.getYear();
		return d + "/" + m + "/" + y;
	}

	private void startActivityCalculIMC(FicheIMC ficheIMC) {
		Intent intent = new Intent(MainActivity.this, CalculIMC.class);
		intent.putExtra(FICHE_IMC, ficheIMC);
		startActivityForResult(intent, REQ_IMC);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// Pour affichage éventuel du retour si nécessaire
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}
}
