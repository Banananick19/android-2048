package banana.game;


import android.graphics.drawable.Drawable;
import android.os.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.*;
import androidx.core.content.ContextCompat;
import androidx.percentlayout.widget.PercentFrameLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.SplittableRandom;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    int fieldSize = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void switchLeft(View view) {
        if (fieldSize == 3) {
            return;
        }
        fieldSize--;
        TextView textView = findViewById(R.id.boardSize);
        textView.setText(fieldSize + "x" + fieldSize);
    }
    public void switchRight(View view) {
        fieldSize++;
        TextView textView = findViewById(R.id.boardSize);
        textView.setText(fieldSize + "x" + fieldSize);
    }
    public void goPlay(View view) {
        Intent intent = new Intent(this, BoardActivity.class);
        intent.putExtra("size", String.valueOf(fieldSize));
        startActivity(intent);
    }

}