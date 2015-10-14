package sawyer.alex.evocreation;


import android.widget.TextView;

/**
 * Created by Alexander on 9/24/2015.
 */
public class Time {

    int iterations;
    int month;
    long year;
    String displayText;
    TextView timeView;

    Time() {
        iterations = 0;
        month = 0;
        year = 0;
    }

    public void update(TextView view, int setarraySize, int setbirths, int setdeaths) {
        timeView = view;
        int arraySize = setarraySize;
        int births = setbirths;
        int deaths = setdeaths;
        iterations = iterations + 1;
//        1918
        if (iterations == 300) {
            iterations = 0;
            month = month + 1;

            if (month > 11) {
                month = 0;
                year = year + 1;
            }
        }
        displayText = year + " years and " + month + " months since the beginning of life.    Population-" + arraySize + "  Births-" + births + "  Deaths-" + deaths;

        timeView.post(new Runnable() {
            public void run() {
                timeView.setText(displayText);
            }
        });
    }
}
