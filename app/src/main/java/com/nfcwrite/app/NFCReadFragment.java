package com.nfcwrite.app;

import android.app.DialogFragment;
import android.content.Context;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;

public class NFCReadFragment extends DialogFragment {

    public static final String TAG = NFCReadFragment.class.getSimpleName();

    public static NFCReadFragment newInstance() {

        return new NFCReadFragment();
    }

    private TextView mTvMessage;
    private Listener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_read,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        Toast.makeText(getActivity().getApplicationContext(), "initViews" , Toast.LENGTH_LONG).show();
        mTvMessage = (TextView) view.findViewById(R.id.tv_message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (MainActivity)context;

        Toast.makeText(getActivity().getApplicationContext(), "onAttach" , Toast.LENGTH_LONG).show();

        mListener.onDialogDisplayed();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener.onDialogDismissed();
    }

    public void onNfcDetected(Ndef ndef){
        Toast.makeText(getActivity().getApplicationContext(), "onNfcDetected" , Toast.LENGTH_LONG).show();
        readFromNFC(ndef);
    }

    private void readFromNFC(Ndef ndef) {
        Toast.makeText(getActivity().getApplicationContext(), "readFromNFC" , Toast.LENGTH_LONG).show();
        try {
            ndef.connect();
            NdefMessage ndefMessage = ndef.getNdefMessage();
            String message = new String(ndefMessage.getRecords()[0].getPayload());
            Log.d(TAG, "readFromNFC: "+message);
            Toast.makeText(getActivity().getApplicationContext(), message , Toast.LENGTH_LONG).show();

            mTvMessage.setText(message);
            ndef.close();

        } catch (IOException | FormatException e) {
            e.printStackTrace();

        }
    }
}
