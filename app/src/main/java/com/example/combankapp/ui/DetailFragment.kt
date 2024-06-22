package com.example.combankapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.combankapp.databinding.FragmentDetailBinding
import com.example.combankapp.models.Transaction
import javax.annotation.Nullable


class DetailFragment : Fragment() {
    private var binding: FragmentDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.getRoot()
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val transaction: Transaction? =
                requireArguments().getParcelable("transaction")
            if (transaction != null) {
                binding?.tvDescription?.text = transaction.description
                binding?.tvAmount?.text = transaction.amount
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}