package sawyer.alex.evocreation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class Welcome extends Activity {

    @Override
     protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome);
}

    public void openGame(View view) {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }

}
