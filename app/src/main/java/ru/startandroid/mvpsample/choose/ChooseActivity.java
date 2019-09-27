package ru.startandroid.mvpsample.choose;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import ru.startandroid.mvpsample.R;
import ru.startandroid.mvpsample.activity.SingleActivity;
import ru.startandroid.mvpsample.mvp.UsersActivity;

public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        findViewById(R.id.activity).setOnClickListener(view -> startActivity(new Intent(ChooseActivity.this, SingleActivity.class)));

        findViewById(R.id.mvp).setOnClickListener(view -> startActivity(new Intent(ChooseActivity.this, UsersActivity.class)));
    }
}
