//package com.example.counttoearn.Activity;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.counttoearn.Model.SingupModal;
//import com.example.counttoearn.MyPrefrence;
//import com.example.counttoearn.R;
//import com.example.counttoearn.Service.RestClient;
//import com.example.counttoearn.Util.NoInternetActivity;
//import com.example.counttoearn.Util.Util;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.gms.tasks.TaskExecutors;
//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthProvider;
//
//import java.util.concurrent.TimeUnit;
//
//import in.aabhasjindal.otptextview.OtpTextView;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class OtpActivity extends AppCompatActivity {
//
//    OtpTextView et_otp;
//    TextView time_out, resend_otp;
//    int click_counter;
//    Button btn_go;
//    private String mVerificationId;
//    String otp_click;
//    private FirebaseAuth mAuth;
//    CountDownTimer countDownTimer;
//    String code;
//    String sname, country_code, smobile, referral_code, android_id;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_otp);
//
//        try {
//            et_otp = findViewById(R.id.et_otp);
//            time_out = findViewById(R.id.time_out);
//            resend_otp = findViewById(R.id.resend_otp);
//            btn_go = findViewById(R.id.btn_go);
//
//            Bundle bundle = getIntent().getExtras();
//            sname = bundle.getString("sname");
//            country_code = bundle.getString("country_code");
//            smobile = bundle.getString("smobile");
//            referral_code = bundle.getString("referral_code");
//            android_id = bundle.getString("android_id");
//
//
//            MyPrefrence.setotpsendclick("0");
////        Timer
//            time_out.setVisibility(View.VISIBLE);
//            new CountDownTimer(60000, 1000) {
//
//                public void onTick(long millisUntilFinished) {
//
//                    time_out.setText("00:" + millisUntilFinished / 1000);
//
//                }
//
//
//                public void onFinish() {
//    //                time_out.setText("Time Finish!");
//
//                    time_out.setVisibility(View.GONE);
//                    resend_otp.setVisibility(View.VISIBLE);
//
//                }
//            }.start();
//
//            resend_otp.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    /* OTP TIMER*/
//                    new CountDownTimer(60000, 1000) {
//
//                        public void onTick(long millisUntilFinished) {
//
//                            time_out.setText("00:" + millisUntilFinished / 1000);
//
//                            if (millisUntilFinished < 10000) {
//
//                                time_out.setTextColor(Color.RED);
//                            } else {
//
//                                time_out.setTextColor(Color.BLACK);
//                            }
//
//                        }
//
//
//                        public void onFinish() {
//    //                time_out.setText("Time Finish!");
//
//                            time_out.setVisibility(View.GONE);
//                            resend_otp.setVisibility(View.VISIBLE);
//
//                        }
//                    }.start();
//
//                    sendverificationcode(MyPrefrence.getmobile());
//                    time_out.setVisibility(View.VISIBLE);
//                    resend_otp.setVisibility(View.GONE);
//
//                    otp_click = MyPrefrence.getotpsendclick();
//                    click_counter = Integer.parseInt(otp_click) + 1;
//
//                    MyPrefrence.setotpsendclick("" + click_counter);
//
//                }
//            });
//
//            if (click_counter == 2) {
//
//                Toast toast = Toast.makeText(this, "Please try again after sometime..", Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
//
//            }
//
//
//            btn_go.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    code = et_otp.getOTP().toString().trim();
//
//                    if (code.isEmpty() || code.length() < 6) {
//
//    //                    et_otp.setError("Enter OTP");
//                        et_otp.showError();
//                        Toast.makeText(OtpActivity.this, "Enter OTP", Toast.LENGTH_SHORT).show();
//                        et_otp.requestFocus();
//                        return;
//                    }
//                    verifycode(code);
//                }
//            });
//
//
//            sendverificationcode(MyPrefrence.getmobile());
//            mAuth = FirebaseAuth.getInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//
//    public void back(View view) {
//        onBackPressed();
//    }
//
//
//    /*OTP SEND METHOD*/
//    private void sendverificationcode(String getuphone) {
//
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//
//
//                "+" + MyPrefrence.getcountrycode() + MyPrefrence.getmobile(),        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
//                mCallbacks);        // OnVerificationStateChangedCallbacks
//
//    }
//
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        @Override
//        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//            String code = phoneAuthCredential.getSmsCode();
//            if (code != null) {
////                et_otp.setText(code);
//                et_otp.setOTP(code);
//                verifycode(code);
//            }
//        }
//
//        @Override
//        public void onVerificationFailed(FirebaseException e) {
////            et_otp.setError("" + e.getMessage());
//            Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
////            Log.e("otp", "" + e.getMessage());
//        }
//
//        @Override
//        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//            mVerificationId = s;
//
//        }
//    };
//
//    private void verifycode(String code) {
//        try {
//            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
//            signInWithPhoneAuthCredential(credential);
//
//        } catch (Exception e) {
////            et_otp.setError("Verification Code is wrong");
//            Toast toast = Toast.makeText(this, "Verification Code is wrong", Toast.LENGTH_SHORT);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
//            et_otp.showError();
//
//        }
//    }
//
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(OtpActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//
//                        if (task.isSuccessful()) {
//
//                            singup(OtpActivity.this, sname, country_code, smobile, referral_code, android_id);
//
//
//
//                        } else {
//                            et_otp.showError();
////                            et_otp.setError("" + task.getException().getMessage());
////                            Toast.makeText(O.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });
//    }
//
//
//    Dialog dialog;
//
//    private void singup(Context context, String sname, String country_code, String smobile, String referral_code, String android_id) {
//
//        if (!Util.isInternetConnected(this)) {
//
//            Intent intent = new Intent(OtpActivity.this, NoInternetActivity.class);
//            startActivity(intent);
//
//            return;
//        }
//        dialog = Util.startLoader(context);
//
//        new RestClient(context).getInstance().get().getsingup(sname, country_code, smobile, referral_code, android_id).enqueue(new Callback<SingupModal>() {
//            @Override
//            public void onResponse(Call<SingupModal> call, Response<SingupModal> response) {
//
//                Util.stopLoader(dialog);
//
//                if (response.body() != null) {
//
//
//                    MyPrefrence.setcountrycode(country_code);
//                    MyPrefrence.setmobile(smobile);
//                    MyPrefrence.setname(sname);
//
//
//
//                    MyPrefrence.setuserid(response.body().user_id);
//
//                    Intent intent = new Intent(OtpActivity.this, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                            startActivity(intent);
//                    startActivityForResult(intent, 426);
//
//                    MyPrefrence.setulogin(true);
//
//
//                } else {
//
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<SingupModal> call, Throwable t) {
//                Util.stopLoader(dialog);
//
//            }
//        });
//
//    }
//
//
//}
