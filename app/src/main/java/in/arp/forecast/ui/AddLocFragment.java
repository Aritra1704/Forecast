package in.arp.forecast.ui;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.arp.forecast.R;
import in.arp.forecast.viewmodel.SharedVM;

import com.google.android.gms.plus.PlusOneButton;

public class AddLocFragment extends AppCompatDialogFragment {
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    private final String PLUS_ONE_URL = "http://developer.android.com";
    @BindView(R.id.edtloc)
    private EditText edtloc;
    @BindView(R.id.btnSave)
    private Button btnSave;
    private SharedVM model;
    private String selLocation;
    private OnFragmentInteractionListener mListener;

    public AddLocFragment() {
    }

    public static AddLocFragment newInstance() {
        AddLocFragment fragment = new AddLocFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_loc, null);
        ButterKnife.bind(this, view);

        model = ViewModelProviders.of(getActivity()).get(SharedVM.class);
        model.getSelected().observe(this, item -> {
            selLocation = item;
            edtloc.setText(selLocation);
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selLocation = model.getSelected().getValue();

                selLocation = edtloc.getText().toString();

                model.select(selLocation);
                dismiss();
            }
        });

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();

        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(STYLE_NO_TITLE);

        setCancelable(false);

        return dialog;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
