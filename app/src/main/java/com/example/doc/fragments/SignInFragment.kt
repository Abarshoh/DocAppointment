package com.example.doc.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.replace
import com.example.doc.R
import com.example.doc.databinding.ActivityMainBinding
import com.example.doc.databinding.FragmentSignInBinding
import com.example.doc.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SignInFragment : Fragment() {
    private var userList = mutableListOf<User>()
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    lateinit var main_binding: ActivityMainBinding
    lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        main_binding = ActivityMainBinding.inflate(inflater, container, false)

        val shared = requireContext().getSharedPreferences("shared", AppCompatActivity.MODE_PRIVATE)
        val gson = Gson()
        val convert = object : TypeToken<List<User>>() {}.type
        val users = shared.getString("users", "")
        binding.back.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView,LogFragment())
        }
        binding.signIn.setOnClickListener {

            if (users!= ""){
                userList = gson.fromJson(users,convert)
            }
            for (user in userList){
                if ((binding.emaill.text.toString() == user.email) && binding.passwordl.text.toString() == user.password){
                    Toast.makeText(requireContext(), "Successfully logged in", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, MainFragment())
                        .commit()

                    shared.edit().putBoolean("isLoggedOut", false).apply()
                    shared.edit().putString("active_user", gson.toJson(user)).apply()

                    return@setOnClickListener
                }
            }
            Toast.makeText(
                requireContext(),
                "Username or password is incorrect",
                Toast.LENGTH_SHORT
            ).show()
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignInFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignInFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}