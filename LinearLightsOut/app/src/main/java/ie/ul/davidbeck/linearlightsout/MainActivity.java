package ie.ul.davidbeck.linearlightsout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private LightsOutGame mGame;
    private TextView mGameStateTextView;
    private Button[] mButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGame = new LightsOutGame();
        mGameStateTextView = findViewById(R.id.game_state_text_view);
        mButtons = new Button[mGame.getNumButtons()];
        mButtons[0] = findViewById(R.id.button0);
        mButtons[1] = findViewById(R.id.button1);
        mButtons[2] = findViewById(R.id.button2);
        mButtons[3] = findViewById(R.id.button3);
        mButtons[4] = findViewById(R.id.button4);
        mButtons[5] = findViewById(R.id.button5);
        mButtons[6] = findViewById(R.id.button6);
        if (!(savedInstanceState == null)) {
            int [] buttonsState = new int[mGame.getNumButtons()];
            for (int i = 0; i < mGame.getNumButtons(); i++){
                buttonsState[i] = Integer.parseInt(savedInstanceState.getString("buttonsState").substring(i, i + 1));
            }
            mGame.setAllValues(buttonsState);
            mGame.setNumPresses(savedInstanceState.getInt("turns"));
        }
        updateView();
    }

    public void pressedButton(View view) {

        String tagAsStr = view.getTag().toString();
        int tagAsInt = Integer.parseInt(tagAsStr);

        //Toast.makeText(this, "You pressed index : " + tagAsInt, Toast.LENGTH_SHORT).show();
        mGame.pressedButtonAtIndex(tagAsInt);
        updateView();
    }

    private void updateView() {
        if (mGame.checkForWin()) {
            mGameStateTextView.setText(getString(R.string.win_message));
            for (int i = 0; i < mGame.getNumButtons(); i++) {
                mButtons[i].setText("" + mGame.getValueAtIndex(i));
                mButtons[i].setEnabled(false);
            }
        } else if (mGame.getNumPresses() == 0) {
            mGameStateTextView.setText(R.string.new_game);
            for (int i = 0; i < mGame.getNumButtons(); i++) {
                mButtons[i].setText("" + mGame.getValueAtIndex(i));
                mButtons[i].setEnabled(true);
            }
        } else {
            mGameStateTextView.setText(getString(R.string.game_message, mGame.getNumPresses()));
            for (int i = 0; i < mGame.getNumButtons(); i++) {
                mButtons[i].setText("" + mGame.getValueAtIndex(i));
            }
        }
    }

    public void pressedNewGame(View view) {
        //Toast.makeText(this, "New Game", Toast.LENGTH_SHORT).show());
        mGame = new LightsOutGame();
        updateView();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save off data using outState.putXX(key, value)
        // Hint: you will use the appropriate methods to store int[] and ints,
        // maybe a String.
        outState.putString("buttonsState", mGame.toString());
        outState.putInt("turns" ,mGame.getNumPresses());
    }
}
