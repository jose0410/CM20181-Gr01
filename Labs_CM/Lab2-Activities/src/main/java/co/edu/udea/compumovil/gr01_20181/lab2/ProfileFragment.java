package co.edu.udea.compumovil.gr01_20181.lab2;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    String mail, user;
    private TextView userPTextView;
    private TextView emailPTextView;

    @SuppressLint("ValidFragment")
    public ProfileFragment(String mail, String user) {
        this.mail = mail;
        this.user = user;

    }

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        userPTextView = view.findViewById(R.id.userProfile);
        emailPTextView = view.findViewById(R.id.emailProfile);
        emailPTextView.setText(mail);
        userPTextView.setText(user);
        setHasOptionsMenu(true);

        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.getItem(1).setVisible(false);
        menu.getItem(0).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }


}
