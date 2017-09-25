package com.example.rafae.booktracker.original;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rafae.booktracker.MainMVP;
import com.example.rafae.booktracker.presenters.MainPresenter;
import com.example.rafae.booktracker.R;
import com.example.rafae.booktracker.StateMaintainer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements MainMVP.RequiredViewOps {

    /**
     * Tag for logging.
     */
    protected final String TAG = getClass().getSimpleName();

    // Responsible to maintain the Objects state
    // during changing configuration
    private final StateMaintainer mStateMaintainer = new StateMaintainer(this.getFragmentManager(), TAG);

    // Presenter operations
    private MainMVP.PresenterOps mPresenter;

    // views
    @BindView(R.id.noteText)
    EditText noteText;
    @BindView(R.id.clickyButton)
    Button addNoteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startMVPOps();
        setContentView(R.layout.activity_main3);
        ButterKnife.bind(this);
    }

    /**
     * Handle clicking on button to submit new note.
     *
     * @param v
     */
    @OnClick(R.id.clickyButton)
    public void addNoteButtonClicked(View v) {
        // get text
        String note = noteText.getText().toString();
        if (!note.isEmpty()) {
            mPresenter.newNote(note);
        }

    }


    /**
     * Initialize and restart the Presenter.
     * This method should be called after { Activity#onCreate(Bundle)}
     */
    public void startMVPOps() {
        try {
            if (mStateMaintainer.firstTimeIn()) {
                Log.d(TAG, "onCreate() called for the first time");
                initialize(this);
            } else {
                Log.d(TAG, "onCreate() called more than once");
                reinitialize(this);
            }
        } catch (Exception e) {
            Log.d(TAG, "onCreate() " + e);
            throw new RuntimeException(e);
        }
    }


    /**
     * Initialize relevant MVP Objects.
     * Creates a Presenter instance, saves the presenter in {@link StateMaintainer}
     */
    private void initialize(MainMVP.RequiredViewOps view) throws InstantiationException, IllegalAccessException {
        mPresenter = new MainPresenter(view);
        mStateMaintainer.put(MainMVP.PresenterOps.class.getSimpleName(), mPresenter);
    }

    /**
     * Recovers Presenter and informs Presenter that occurred a config change.
     * If Presenter has been lost, recreates a instance
     */
    private void reinitialize(MainMVP.RequiredViewOps view)
            throws InstantiationException, IllegalAccessException {
        mPresenter = mStateMaintainer.get(MainMVP.PresenterOps.class.getSimpleName());

        if (mPresenter == null) {
            Log.w(TAG, "recreating Presenter");
            initialize(view);
        } else {
            mPresenter.onConfigurationChanged(view);
        }
    }


    // Show AlertDialog
    @Override
    public void showAlert(String msg) {
        // show alert Box
    }

    // Show Toast
    @Override
    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}