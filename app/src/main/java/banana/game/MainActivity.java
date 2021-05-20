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
    private int[][] board;
    private TextView[][] graphicsBoard;
    private int count = 0;
    private TextView countTextView;


    static int rows = -1;
    static int columns = -1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridLayout field = (GridLayout) findViewById(R.id.field);
        countTextView = findViewById(R.id.count);
        rows = 4;
        columns = 4;
        fillBoard(4);
        createBoard(field);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void createBoard(final GridLayout field) {
        field.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams fieldParams = field.getLayoutParams(); // берем параметры поля (лаяута)
                int size = Math.min(field.getWidth(), field.getHeight()); // определяем меньшую сторону
                fieldParams.width = size; // делаем стороны одинаковыми, квадрат
                fieldParams.height = size;
                field.setLayoutParams(fieldParams); // применяем новые параметры
                field.setColumnCount(columns); // задаем полю количество столбцов
                field.setRowCount(rows); // и строк
                // цикл в котором создаются кнопки
                for (int r = 0; r < rows; r++) {
                    for (int c = 0; c < columns; c++) {
                        TextView textView = new TextView(field.getContext());
                        graphicsBoard[r][c] = textView;
                        textView.setText(String.valueOf(board[r][c]));
                        textView.setTextSize(22);
                        textView.setGravity(Gravity.CENTER);
                        textView.setBackground(ContextCompat.getDrawable(field.getContext(), R.drawable.back));
                        GridLayout.LayoutParams lp = new GridLayout.LayoutParams(); // создаем параметры лаяута для кнопки
                        lp.width = 0; // так надо потому что ниже мы указываем вес кнопок = 1, они будут сами высчитывать размеры
                        lp.height = 0;
                        lp.columnSpec = GridLayout.spec(c, 1f); // вес и позиция кнопки по горизонтали
                        lp.rowSpec = GridLayout.spec(r, 1f); // и по вертикали
                        field.addView(textView, lp); // добавляем кнопку на поле
                    }
                }
                field.invalidate();
            }
        });
    }

    protected void fillBoard(int n) {
        board = new int[n][n];
        graphicsBoard = new TextView[n][n];
        for (int i = 0; i<n; i++) {
            for (int j = 0; j<n; j++) {
                board[i][j] = 0;
            }
        }
        int randomX = (int) (Math.random() * n);
        int randomY = (int) (Math.random() * n);
        board[randomX][randomY] = 2;
    }

    protected void updateBoard() {
        GridLayout layout = findViewById(R.id.field);
        for (int i = 0; i<columns; i++) {
            for (int j = 0; j<rows; j++) {
                TextView textView = graphicsBoard[i][j];
                textView.setText(String.valueOf(board[i][j]));
            }
        }
        layout.invalidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void fillRandomField() {
        ArrayList<Integer> emptyFieldsX = new ArrayList<Integer>();
        ArrayList<Integer> emptyFieldsY = new ArrayList<Integer>();
        for (int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    emptyFieldsX.add(j);
                    emptyFieldsY.add(i);
                }
            }
        }
        int index = (int) (Math.random() * emptyFieldsX.size());
        SplittableRandom random = new SplittableRandom();
        boolean setFour = random.nextInt(10) == 0;
        if (setFour) {
            board[emptyFieldsY.get(index)][emptyFieldsX.get(index)] = 4;
        } else {
            board[emptyFieldsY.get(index)][emptyFieldsX.get(index)] = 2;
        }
    }


        public void moveUp (View view){
            for (int i = 0; i < columns; i++) {//это столбцы
                for (int j = 0; j < rows-1; j++) {//это строки
                    for (int l = 0; l < rows-1-j; l++) {//это просто счетчик
                        if (board[j][i]==0) {
                            for (int k = j; k < rows-1; k++) {
                                board[k][i] = board[k+1][i];
                            }
                            board[rows-1][i]=0;
                        }
                    }
                }
                for (int j = 0; j < rows-1; j++) {//это строки
                    if (board[j][i]==board[j+1][i]) {
                        count+=board[j][i]+board[j+1][i];
                        board[j][i]+=board[j+1][i];
                        for (int k = j+1; k < rows-1; k++) {
                            board[k][i] = board[k+1][i];
                        }
                        board[rows-1][i]=0;
                    }
                }
            }
            countTextView.setText(String.valueOf(count));
            fillRandomField();
            updateBoard();
        }

        public void moveDown(View view){
            for (int i = 0; i < columns; i++) {//это столбцы
                for (int j = rows-1; j > 0; j--) {//это строки
                    for (int l = 0; l < j; l++) {//это просто счетчик
                        if (board[j][i]==0) {
                            for (int k = j; k > 0; k--) {
                                board[k][i] = board[k-1][i];
                            }
                            board[0][i]=0;
                        }
                    }
                }
                for (int j = rows-1; j > 0; j--) {//это строки
                    if (board[j][i]==board[j-1][i]) {
                        count+=board[j][i]+board[j-1][i];
                        board[j][i]+=board[j-1][i];
                        for (int k = j-1; k > 0; k--) {
                            board[k][i] = board[k-1][i];
                        }
                        board[0][i]=0;
                    }
                }
            }
            countTextView.setText(String.valueOf(count));
            fillRandomField();
            updateBoard();
        }

        public void moveRight(View view){
            for (int i = 0; i < rows; i++) {//это строки
                for (int j = columns-1; j > 0; j--) {//это столбцы
                    for (int l = 0; l < j; l++) {//это просто счетчик
                        if (board[i][j]==0) {
                            for (int k = j; k > 0; k--) {
                                board[i][k] = board[i][k-1];
                            }
                            board[i][0]=0;
                        }
                    }
                }
                for (int j = columns-1; j > 0; j--) {//это столбцы
                    if (board[i][j]==board[i][j-1]) {
                        count+= board[i][j]+board[i][j-1];
                        board[i][j]+=board[i][j-1];
                        for (int k = j-1; k > 0; k--) {
                            board[i][k] = board[i][k-1];
                        }
                        board[i][0]=0;
                    }
                }
            }
            countTextView.setText(String.valueOf(count));
            fillRandomField();
            updateBoard();
        }

        public void moveLeft(View view){
            for (int i = 0; i < rows; i++) {//это строки
                for (int j = 0; j < columns-1; j++) {//это столбцы
                    for (int l = 0; l < columns-1-j; l++) {//это просто счетчик
                        if (board[i][j]==0) {
                            for (int k = j; k < columns-1; k++) {
                                board[i][k] = board[i][k+1];
                            }
                            board[i][columns-1]=0;
                        }
                    }
                }
                for (int j = 0; j < columns-1; j++) {//это столбцы
                    if (board[i][j]==board[i][j+1]) {
                        count+=board[i][j]+board[i][j+1];
                        board[i][j]+=board[i][j+1];
                        for (int k = j+1; k < columns-1; k++) {
                            board[i][k] = board[i][k+1];
                        }
                        board[i][columns-1]=0;
                    }
                }
            }
            countTextView.setText(String.valueOf(count));
            fillRandomField();
            updateBoard();
        }
}