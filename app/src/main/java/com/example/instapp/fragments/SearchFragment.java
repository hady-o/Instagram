package com.example.instapp.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instapp.R;
import com.example.instapp.adapters.CaptionedImagesAdapter;
import com.example.instapp.adapters.SearchAdapter;
import com.example.instapp.classes.Post;
import com.example.instapp.classes.User;
import com.example.instapp.firebase.CurrentUserClass;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends Fragment {
    FirebaseFirestore dbe;
    SearchAdapter adapter;
    EditText search;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_search, container, false);

        List<User> users = new ArrayList<>();
        adapter =new SearchAdapter(users);
        dbe = FirebaseFirestore.getInstance();
        search = view.findViewById(R.id.editTextSearch);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                users.clear();
                dbe.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        users.clear();
                            for (QueryDocumentSnapshot document : value)
                            {
                                if(document.getString("name").toLowerCase(Locale.ROOT).contains(search.getText().toString().toLowerCase(Locale.ROOT)) || document.getString("email").toLowerCase(Locale.ROOT).equals(search.getText().toString().toLowerCase(Locale.ROOT)))
                                {
                                    String name = document.getString("name");
                                    String email = document.getString("email");
                                    String id = document.getString("id");
                                    String uri = document.getString("uri");
                                    User u =new User(id,name,email,uri);
                                    users.add(u);
                                    adapter.notifyDataSetChanged();
                                }
                                if(search.getText().toString().equals(""))
                                {
                                    users.clear();
                                    adapter.notifyDataSetChanged();
                                }
                                adapter.notifyDataSetChanged();
                            }
                            adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        RecyclerView res = view.findViewById(R.id.search_recycler);

        LinearLayoutManager mylayoutmanager = new LinearLayoutManager(view.getContext());
        res.setLayoutManager(mylayoutmanager);
        res.setAdapter(adapter);

        return view;
    }
}
