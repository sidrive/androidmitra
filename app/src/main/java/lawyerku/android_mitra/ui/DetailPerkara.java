package lawyerku.android_mitra.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.ButterKnife;
import lawyerku.android_mitra.R;

public class DetailPerkara extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_perkara);
        ButterKnife.bind(this);


    }
}
