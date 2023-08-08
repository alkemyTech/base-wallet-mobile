package com.alkemy.alkewallet.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alkemy.alkewallet.R
import com.alkemy.alkewallet.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonRequestMoney.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_requestMoneyFragment)
        }

        binding.buttonSendMoney.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_sendMoneyFragment)
        }
    }
}