package zju.mobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.zerokol.views.JoystickView;

public class RoamActivity extends AppCompatActivity {
    private JoystickView leftStick;
    private JoystickView rightStick;
    private TextView directionTextView;
    private int leftAction=0;
    private int rightAction=0;
    private String output="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roam);

        directionTextView = (TextView)findViewById(R.id.testText);

        leftStick = (JoystickView) findViewById(R.id.leftStick);
        rightStick = (JoystickView) findViewById(R.id.rightStick);
        leftStick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {
            @Override
            public void onValueChanged(int angle, int power, int direction) {
                leftAction = direction;
                output = leftAction + ";" + rightAction;
                directionTextView.setText(output);
            }
        },JoystickView.DEFAULT_LOOP_INTERVAL);

        rightStick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {
            @Override
            public void onValueChanged(int angle, int power, int direction) {
                rightAction = direction;
                output = leftAction + ";" + rightAction;
                directionTextView.setText(output);
            }
        },JoystickView.DEFAULT_LOOP_INTERVAL);
    }
}
