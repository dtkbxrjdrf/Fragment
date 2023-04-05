package com.example.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements BlankFragment.OnFragmentInteractionListener {

    private Button mButton;
    private Button oButton;
    private boolean isFragmentDisplayed = false;
    static final String STATE_FRAGMENT = "state_of_fragment";
    private int mRadioButtonChoice = 2; // The default (no choice).

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = findViewById(R.id.open_button);
        oButton = findViewById(R.id.next_button);
        if (savedInstanceState != null) {
            isFragmentDisplayed =
                    savedInstanceState.getBoolean(STATE_FRAGMENT);
            if (isFragmentDisplayed) {
                // If the fragment is displayed, change button to "close".
                mButton.setText(R.string.close);
            }
        }
        // Set the click listener for the button.
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFragmentDisplayed) {
                    displayFragment();
                } else {
                    closeFragment();
                }
            }
        });
        oButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
    }

    public void displayFragment() {
        BlankFragment fragment = BlankFragment.newInstance(mRadioButtonChoice);
        // Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        // Add the SimpleFragment.
        fragmentTransaction.add(R.id.fragment_container,
                fragment).addToBackStack(null).commit();
        // Update the Button text.
        mButton.setText(R.string.close);
        // Set boolean flag to indicate fragment is open.
        isFragmentDisplayed = true;
    }

    public void closeFragment() {
        // Get the FragmentManager.
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Check to see if the fragment is already showing.
        BlankFragment simpleFragment = (BlankFragment) fragmentManager
                .findFragmentById(R.id.fragment_container);
        if (simpleFragment != null) {
            // Create and commit the transaction to remove the fragment.
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        // Update the Button text.
        mButton.setText(R.string.open);
        // Set boolean flag to indicate fragment is closed.
        isFragmentDisplayed = false;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state of the fragment (true=open, false=closed).
        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRadioButtonChoice(int choice) {
        String message = "";
        switch (choice) {
            case BlankFragment.YES:
                message = "Так";
                break;
            case BlankFragment.NO:
                message = "Ні";
                break;
            default:
                break;
        }
        if (!message.equals("")) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
}