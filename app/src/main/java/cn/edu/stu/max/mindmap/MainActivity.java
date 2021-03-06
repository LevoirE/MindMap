package cn.edu.stu.max.mindmap;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WorkActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting:
                Intent intent_setting = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent_setting);
                return true;
            case R.id.menu_about:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.message_about)
                        .setNegativeButton(R.string.btn_close, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        }).create().show();
            return true;
            case R.id.menu_backet:
                Intent intent_backet = new Intent(MainActivity.this, BacketActivity.class);
                startActivity(intent_backet);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    boolean isDoubleBack = false;

    @Override
    public void onBackPressed() {
        if (isDoubleBack) {
            // super.onBackPressed(); 	不要调用父类的方法
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
        this.isDoubleBack = true;
        Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 2s = 2000ms
                isDoubleBack = false;
            }
        }, 2000);
    }
}
