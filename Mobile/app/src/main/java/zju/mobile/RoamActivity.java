package zju.mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zerokol.views.JoystickView;

public class RoamActivity extends AppCompatActivity {
    private JoystickView leftStick;
    private JoystickView rightStick;
    private Button btn;
    private int leftAction=0;
    private int rightAction=0;
    private MySender mySender = MySender.getInstance();
    private String output="";            //the String to be sent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roam);

        leftStick = (JoystickView) findViewById(R.id.leftStick);
        rightStick = (JoystickView) findViewById(R.id.rightStick);
        leftStick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {
            @Override
            public void onValueChanged(int angle, int power, int direction) {
                leftAction = direction;
                output = "R;"+leftAction + ";" + rightAction;
                mySender.sendData(output);
            }
        },JoystickView.DEFAULT_LOOP_INTERVAL);

        rightStick.setOnJoystickMoveListener(new JoystickView.OnJoystickMoveListener() {
            @Override
            public void onValueChanged(int angle, int power, int direction) {
                rightAction = direction;
                output = "R;"+leftAction + ";" + rightAction;
                mySender.sendData(output);
//                directionTextView.setText(output);
            }
        },JoystickView.DEFAULT_LOOP_INTERVAL);

        btn = (Button)findViewById(R.id.toSelectAty);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoamActivity.this, SelectActivity.class);
                startActivity(intent);
            }
        });
    }
}
