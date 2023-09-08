package com.example.doc.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.doc.R
import com.example.doc.databinding.ActivityMainBinding
import com.example.doc.databinding.FragmentSignupBinding
import com.example.doc.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SignupFragment : Fragment() {
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
    lateinit var binding: FragmentSignupBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        main_binding = ActivityMainBinding.inflate(inflater, container, false)
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        val shared = requireContext().getSharedPreferences("shared", AppCompatActivity.MODE_PRIVATE)
        val edit = shared.edit()
        val gson = Gson()
        val convert = object : TypeToken<List<User>>() {}.type

        binding.back.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView,LogFragment())
        }

        binding.signUp.setOnClickListener {
            var users = shared.getString("users", "")
            if (users == "") {
                userList = mutableListOf()
            } else {
                userList = gson.fromJson(users, convert)
            }

            var user = User(
                binding.username.text.toString(),
                binding.password.text.toString(),
                binding.email.text.toString(),
            )
            if (validate()){
                userList.add(user)

                val str = gson.toJson(userList)
                edit.putString("users",str).apply()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, MainFragment())
                    .commit()
                shared.edit().putString("active_user", gson.toJson(user)).apply()
            }

        }
        return binding.root
    }

    private fun validate(): Boolean{
        if (binding.username.text.toString().isEmpty() || binding.password.text.toString().isEmpty() || binding.email.text.toString().isEmpty()
        ) {
            Toast.makeText(requireContext(), "Fill the gaps", Toast.LENGTH_SHORT).show()
            return false
        }
        for (i in userList.indices) {
            if (binding.username.text.toString() == userList[i].username) {
                Toast.makeText(requireContext(), "User with this username already registered", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}