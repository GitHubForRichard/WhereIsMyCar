package academy.learnprogramming.whereismycar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText etemail;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnCancel;
    private int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etemail=(EditText)findViewById(R.id.etemail);
        etPassword=(EditText)findViewById(R.id.etPassword);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnCancel=(Button)findViewById(R.id.btnCancel);
        counter=5;
        btnLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //System.out.println(etemail.getText().toString());
                Log.d("test", etemail.getText().toString());
                validate(etemail.getText().toString(),etPassword.getText().toString());
            }
        });
    }
    private void validate(String email,String pwd){
        //when email && pwd match
        if(email.equals("admin") && pwd.equals("123")) {
            //let user enter next activity from this activity
            Intent intent = new Intent(MainActivity.this,SearchActivity.class );
            startActivity(intent);
        }else{
            //disable login button if user enter wrong email/pwd 5 times
            counter--;
            if(counter==0){
                btnLogin.setEnabled(false);
            }
        }
    }
}
