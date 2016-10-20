package nl.yrck.comicsprout.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class IssueListFragment extends BaseListFragment {

    private String type = "issue";
    private ListFragmentInteraction onFragmentInteractionListener;


    public IssueListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            onFragmentInteractionListener = (ListFragmentInteraction) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }

        onFragmentInteractionListener.setActionBarTitle("Issues");

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        String myUserId = getUid();
        return databaseReference.child(type).child(myUserId)
                .orderByChild("name");
    }

    @Override
    public String getType() {
        return type;
    }

}
