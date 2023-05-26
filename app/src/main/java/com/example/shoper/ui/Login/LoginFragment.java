package com.example.shoper.ui.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoper.databinding.FragmentLoginBinding;
import com.example.shoper.ui.Home.HomeActivity;
import com.example.shoper.ui.ProgressDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginFragment extends Fragment {

    private static final String ARG_NAME = "tabName";
    private static final String TAG = "tag";
    private String tabName = "";

    private ProgressDialog dialog;
    private FragmentLoginBinding binding;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getData() != null) {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        try {
                            Log.w("TAG", "on result");
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                            signInWithCredential(credential);
                        } catch (ApiException e) {
                            // Google Sign In failed, update UI appropriately
                            Log.w("TAG", "Google sign in failed", e);
                        }
                    }

                }
            });


    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabName = getArguments().getString(ARG_NAME);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getActivity().getLayoutInflater());
        dialog = new ProgressDialog(getActivity(), "Loading");
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(com.firebase.ui.auth.R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);


        binding.loginFragmentBtn.setText(tabName);
        if (tabName.equals("Login")) {
            binding.textInputLayout0.setVisibility(View.GONE);
        } else {
            binding.textInputLayout0.setVisibility(View.VISIBLE);
        }

        binding.loginFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.showProgress();
                boolean temp = true;
                String email, password, name;
                email = binding.loginFragmentEtEmail.getText().toString();
                password = binding.loginFragmentEtPassword.getText().toString();
                name = binding.loginFragmentEtName.getText().toString();
                if (email.isEmpty()) {
                    temp = false;
                    binding.textInputLayout1.setErrorEnabled(true);
                    binding.textInputLayout1.setError("Email is Required");
                } else
                    binding.textInputLayout1.setErrorEnabled(false);
                if (!tabName.equals("Login"))
                    if (name.isEmpty()) {
                        temp = false;
                        binding.textInputLayout0.setErrorEnabled(true);
                        binding.textInputLayout0.setError("Name is Required");
                    } else
                        binding.textInputLayout0.setErrorEnabled(false);
                if (password.isEmpty()) {
                    temp = false;
                    binding.textInputLayout1.setErrorEnabled(true);
                    binding.textInputLayout2.setError("Password is Required");
                } else if (password.length() < 6) {
                    temp = false;
                    binding.textInputLayout1.setErrorEnabled(true);
                    binding.textInputLayout2.setError("Password must be at least 6 characters");
                } else
                    binding.textInputLayout2.setErrorEnabled(false);
                if (temp) {
                    Log.w(TAG, "get data done");
                    if (tabName.equals("Login")) {
                        signIn(email, password);
                    } else {
                        createUser(email, password, name);
                    }
                } else {
                    dialog.hideProgress();
                }
            }
        });


        binding.loginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        return binding.getRoot();
    }


    private void createUser(String email, String password, String name) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.w(TAG, "create Successful");
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name).build();
                            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    setData(user);
                                    updateUI(user);
                                }
                            });


                        } else {
                            Log.w(TAG, "create is not Successful");
                            updateUI(null);
                        }
                    }
                });


    }

    private void signIn(String email, String password) {
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        signInWithCredential(credential);
    }

    private void signInWithCredential(AuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.w(TAG, "Login Successful");
                            FirebaseUser user = mAuth.getCurrentUser();
                            setData(user);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "Login is not Successful");
                            updateUI(null);
                        }
                    }
                });
    }

    private void signIn() {
        dialog.showProgress();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        resultLauncher.launch(signInIntent);

    }

    private void updateUI(FirebaseUser user) {
        dialog.hideProgress();
        if (user != null) {
            Toast.makeText(getActivity().getBaseContext(), "user : " + user.getUid(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);

        } else
            Log.w(TAG, "user : null");
    }

    private void setData(FirebaseUser user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(user.getUid());

        myRef.child("name").setValue(user.getDisplayName());
        myRef.child("email").setValue(user.getEmail());
    }


}