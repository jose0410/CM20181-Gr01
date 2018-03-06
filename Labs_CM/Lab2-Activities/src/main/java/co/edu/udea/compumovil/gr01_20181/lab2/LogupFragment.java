package co.edu.udea.compumovil.gr01_20181.lab2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogupFragment extends Fragment implements View.OnClickListener {

    private Button loginButton, logupButton;


    public LogupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_log_up, container, false);

        logupButton = (Button) view.findViewById(R.id.logupButton);
        logupButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
