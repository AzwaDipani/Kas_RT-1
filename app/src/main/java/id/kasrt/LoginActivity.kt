package id.kasrt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import id.kasrt.databinding.ActivityLoginBinding
import androidx.activity.OnBackPressedCallback

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.forgotPassword.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.tvToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmailLogin.text.toString()
            val password = binding.edtPasswordLogin.text.toString()

            //Validasi email
            if (email.isEmpty()){
                binding.edtEmailLogin.error = "Email Harus Diisi"
                binding.edtEmailLogin.requestFocus()
                return@setOnClickListener
            }

            //Validasi email tidak sesuai
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtPasswordLogin.error = "Email Tidak Valid"
                binding.edtPasswordLogin.requestFocus()
                return@setOnClickListener
            }

            //Validasi password
            if (password.isEmpty()){
                binding.edtPasswordLogin.error = "Password Harus Diisi"
                binding.edtPasswordLogin.requestFocus()
                return@setOnClickListener
            }

            LoginFirebase(email,password)
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
            }})
    }

    private fun LoginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Selamat datang $email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MenuActivity2::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }


}