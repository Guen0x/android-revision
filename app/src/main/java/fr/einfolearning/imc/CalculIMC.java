package fr.einfolearning.imc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import parcelable_imc.FicheIMC;

public class CalculIMC extends Activity {

	private FicheIMC ficheIMC = null;
	private TextView tv_nom, tv_imc;
	private Button bt_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calcul_imc);

		deserialiserRessource();
		initConnections();

		ficheIMC = getIntent().getParcelableExtra(MainActivity.FICHE_IMC);
		if (ficheIMC != null) {
			printIMCDetail(ficheIMC);
		} else {
			Toast.makeText(this, "Erreur : fiche IMC absente", Toast.LENGTH_SHORT).show();
		}
	}

	private void deserialiserRessource() {
		tv_nom = findViewById(R.id.tv_nom);
		tv_imc = findViewById(R.id.tv_imc);
		bt_back = findViewById(R.id.bt_back);
	}


	private void initConnections() {
		bt_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(Activity.RESULT_OK);
				finish();
			}
		});
	}

	private void printIMCDetail(FicheIMC ficheIMC) {
		String nomComplet = ficheIMC.getNom() + " " + ficheIMC.getPrenom();
		float imc = calculateIMC(ficheIMC.getTaille(), ficheIMC.getPoids());

		tv_nom.setText("Nom : " + nomComplet);
		tv_imc.setText("IMC : " + String.format("%.2f", imc));
	}

	private float calculateIMC(float tailleCm, float poids) {
		float tailleM = tailleCm / 100.0f; // conversion en mètres
		return poids / (tailleM * tailleM);
	}

}
