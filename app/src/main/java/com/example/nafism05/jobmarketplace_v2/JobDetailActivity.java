package com.example.nafism05.jobmarketplace_v2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JobDetailActivity extends AppCompatActivity {

    TextView title, language, detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        title = findViewById(R.id.title);
        language = findViewById(R.id.language);
        detail = findViewById(R.id.detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
//            id = bundle.getString("UpdateNoteId");

            title.setText(bundle.getString("title"));
            language.setText(bundle.getString("language"));
            detail.setText(bundle.getString("detail"));
        }
    }
}
