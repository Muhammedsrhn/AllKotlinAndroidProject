package com.example.kotlinnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_blank3.*
import kotlinx.android.synthetic.main.fragment_blank4.*
import org.jetbrains.annotations.Nullable
import java.lang.Exception


class BlankFragment2 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
             val username = BlankFragment2Args.fromBundle(it)
            textView.text = username!!.username
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button1.setOnClickListener {
            // direction class is created but you dont this class it's a working good
            val action = BlankFragment2Directions.actionBlankFragment2ToBlankFragment3()
            Navigation.findNavController(it).navigate(action)

        }
    }
}