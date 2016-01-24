package zju.mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class SelectActivity extends AppCompatActivity{
    private Button btn;
    private Button upBtn;
    private Button downBtn;
    private Button rightBtn;
    private Button leftBtn;
    private Button forwardBtn;
    private Button backwardBtn;
    private MySender mySender = MySender.getInstance();
    private int tmpSelector=0;
    private String output="";
    private Switch selectChecked;
    private boolean isMoving=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        selectChecked = (Switch)findViewById(R.id.switch1);
        selectChecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isMoving = isChecked;
            }
        });

        btn = (Button)findViewById(R.id.toRoamAty);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectActivity.this, RoamActivity.class);
                startActivity(intent);
            }
        });

        upBtn = (Button)findViewById(R.id.up_arrow);
        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMoving) {
                    output = "M;" + 2;
                } else {                      //selecting mode
                    output = "S;" + 2;
                }
                mySender.sendData(output);
            }
        });
        leftBtn = (Button)findViewById(R.id.left_arrow);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMoving) {
                    output = "M;" + 3;
                } else {                      //selecting mode
                    output = "S;" + 3;
                }
                mySender.sendData(output);
            }
        });
        downBtn = (Button)findViewById(R.id.down_arrow);
        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMoving) {
                    output = "M;" + 4;
                } else {                      //selecting mode
                    output = "S;" + 4;
                }
                mySender.sendData(output);
            }
        });
        rightBtn = (Button)findViewById(R.id.right_arrow);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMoving) {
                    output = "M;" + 1;
                } else {                      //selecting mode
                    output = "S;" + 1;
                }
                mySender.sendData(output);
            }
        });

        forwardBtn=(Button)findViewById(R.id.forward_arrow);
        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMoving) {
                    output = "M;" + 5;
                } else {                      //selecting mode
                    output = "S;" + 5;
                }
                mySender.sendData(output);
            }
        });

        backwardBtn=(Button)findViewById(R.id.backward_arrow);
        backwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMoving) {
                    output = "M;" + 6;
                } else {                      //selecting mode
                    output = "S;" + 6;
                }
                mySender.sendData(output);
            }
        });
    }

}
